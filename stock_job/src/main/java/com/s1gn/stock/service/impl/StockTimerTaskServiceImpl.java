package com.s1gn.stock.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.s1gn.stock.constant.ParseType;
import com.s1gn.stock.mapper.*;
import com.s1gn.stock.pojo.entity.StockBlockRtInfo;
import com.s1gn.stock.pojo.entity.StockMarketIndexInfo;
import com.s1gn.stock.pojo.entity.StockOuterMarketIndexInfo;
import com.s1gn.stock.pojo.vo.StockInfoConfig;
import com.s1gn.stock.service.StockTimerTaskService;
import com.s1gn.stock.utils.DateTimeUtil;
import com.s1gn.stock.utils.IdWorker;
import com.s1gn.stock.utils.ParserStockInfoUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName StockTimerTaskServiceImpl
 * @Description
 * @Author S1gn
 * @Date 2024/4/3 14:58
 * @Version 1.0
 */
@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private HttpEntity<Object> httpEntity;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;
    @Override
    public void getInnerMarketInfo() {
        // 采集原始数据
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        // 填充headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Referer", "https://finance.sina.com.cn/stock/");
//        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        if (responseEntity.getStatusCodeValue()!=200){
            log.error("{}，采集数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
            return;
        }
        // 解析数据
        String jsData = responseEntity.getBody();
        log.info("{}采集到数据{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), jsData);
        String reg = "var hq_str_(.+)=\"(.+)\";";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(jsData);
        List<StockMarketIndexInfo> entities = new ArrayList<>();
        while(matcher.find())
        {
//            String stockCode = matcher.group(1);
            String stockInfo = matcher.group(2);
            String[] stockInfoArr = stockInfo.split(",");
            // 0:股票名称
            String marketName = stockInfoArr[0];
            // 1:开盘价
            BigDecimal openPoint = new BigDecimal(stockInfoArr[1]);
            // 2:前收盘价
            BigDecimal preClosePoint = new BigDecimal(stockInfoArr[2]);
            // 3:当前价格
            BigDecimal currentPoint = new BigDecimal(stockInfoArr[3]);
            // 4:最高价
            BigDecimal highPoint = new BigDecimal(stockInfoArr[4]);
            // 5:最低价
            BigDecimal lowPoint = new BigDecimal(stockInfoArr[5]);
            // 8：成交量
            Long tradeAmt = Long.valueOf(stockInfoArr[8]);
            // 9：成交额
            BigDecimal tradeVol = new BigDecimal(stockInfoArr[9]);
            // 30：日期 31：时间
            Date cutTime = DateTimeUtil.getDateTimeWithoutSecond(stockInfoArr[30] + " " + stockInfoArr[31]).toDate();
            // 封装实体对象
            StockMarketIndexInfo entity = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(stockInfoArr[0])
                    .marketName(marketName)
                    .preClosePoint(preClosePoint)
                    .openPoint(openPoint)
                    .curPoint(currentPoint)
                    .minPoint(lowPoint)
                    .maxPoint(highPoint)
                    .tradeAmount(tradeAmt)
                    .tradeVolume(tradeVol)
                    .curTime(cutTime)
                    .build();
            entities.add(entity);
        }
        log.info("解析到{}条数据", entities.size());
        // 持久化数据
        int count = stockMarketIndexInfoMapper.insertBatch(entities);
        if(count>0)
        {
            // 数据采集完成后，通知backend刷新缓存
            // 发送日期，backend收到日期并比对，判断是否需要刷新缓存
            rabbitTemplate.convertAndSend("stockExchange", "inner.market", new Date());

            log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
        }else{
            log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Override
    public void getInnerStockMarketInfo() {
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        allStockCodes = allStockCodes.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        // 将股票集合拆分成多个集合
        List<List<String>> partition = Lists.partition(allStockCodes, 15);
        partition.forEach(codeList->{

            // 单线程拼接采集
//            String url = stockInfoConfig.getMarketUrl() + String.join(",", codeList);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Referer", "https://finance.sina.com.cn/stock/");
//            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
//            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            /*ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            if (responseEntity.getStatusCodeValue()!=200){
                log.error("{}，采集股票数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
                return;
            }
            String jsData = responseEntity.getBody();
            List<StockInfoConfig> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
            int count = stockRtInfoMapper.insertBatch(list);
            if(count>0)
            {
                log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
            }else{
                log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            }*/

            // 多线程采集
            // 问题：每次请求，需要创建销毁线程，开销大；线程过多，会导致线程阻塞竞争资源，上下文切换开销大
            /*new Thread(()->{
                String url = stockInfoConfig.getMarketUrl() + String.join(",", codeList);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                if (responseEntity.getStatusCodeValue()!=200){
                    log.error("{}，采集股票数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
                    return;
                }
                String jsData = responseEntity.getBody();
                List<StockInfoConfig> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
                int count = stockRtInfoMapper.insertBatch(list);
                if(count>0)
                {
                    log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
                }else{
                    log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                }
            });*/

            // 线程池采集
            threadPoolTaskExecutor.execute(()->{
                String url = stockInfoConfig.getMarketUrl() + String.join(",", codeList);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                if (responseEntity.getStatusCodeValue()!=200){
                    log.error("{}，采集股票数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
                    return;
                }
                String jsData = responseEntity.getBody();
                List<StockInfoConfig> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
                int count = stockRtInfoMapper.insertBatch(list);
                if(count>0)
                {
                    log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
                }else{
                    log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                }
            });

        });
    }

    @Override
    public void getBlockInfo() {
        String url = stockInfoConfig.getBlockUrl();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        if (responseEntity.getStatusCodeValue()!=200){
            log.error("{}，采集板块数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
            return;
        }
        String jsData = responseEntity.getBody();
        // jsdata = aaaa={...}
        String reg = "(.+)=(.+)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(jsData);
        if(matcher.find())
        {
            String blockData = matcher.group(2);
            try {
                HashMap<String, String> blockMap = objectMapper.readValue(blockData, HashMap.class);
                List<StockBlockRtInfo> entities = new ArrayList<>();
                blockMap.values().forEach(block-> {
                    String[] blockInfo = block.split(",");
                    String label = blockInfo[0];
                    String blockName = blockInfo[1];
                    Long companyNum = Long.valueOf(blockInfo[2]);
                    BigDecimal avgPrice = new BigDecimal(blockInfo[3]);
                    BigDecimal updownRate = new BigDecimal(blockInfo[5]);
                    Long tradeAmount = Long.valueOf(blockInfo[6]);
                    BigDecimal tradeVolume = new BigDecimal(blockInfo[7]);
                    Date now = DateTimeUtil.getDateTimeWithoutSecond(DateTime.now()).toDate();
                    StockBlockRtInfo entity = StockBlockRtInfo.builder()
                            .id(idWorker.nextId())
                            .label(label)
                            .blockName(blockName)
                            .companyNum(companyNum.intValue())
                            .avgPrice(avgPrice)
                            .updownRate(updownRate)
                            .tradeAmount(tradeAmount)
                            .tradeVolume(tradeVolume)
                            .curTime(now)
                            .build();
                    entities.add(entity);
                });
                int count = stockBlockRtInfoMapper.insertBatch(entities);
                if(count>0)
                {
                    log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
                }else{
                    log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void getOuterMarketInfo() {
        // 构造url
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getOuter());
        // 填充headers
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        if (responseEntity.getStatusCodeValue()!=200){
            log.error("{}，采集数据失败，状态码{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), responseEntity.getStatusCodeValue());
            return;
        }
        // 解析数据
        String jsData = responseEntity.getBody();
        log.info("{}采集到数据{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), jsData);
        String reg = "var hq_str_(.+)=\"(.+)\";";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(jsData);
        List< StockOuterMarketIndexInfo> entities = new ArrayList<>();
        while(matcher.find())
        {
            String stockCode = matcher.group(1);
            String stockInfo = matcher.group(2);
            String[] stockInfoArr = stockInfo.split(",");
            // 0:股票名称
            String marketName = stockInfoArr[0];
            // 1:当前点数
            BigDecimal curPoint = new BigDecimal(stockInfoArr[1]);
            // 2:涨跌值
            BigDecimal updown = new BigDecimal(stockInfoArr[2]);
            // 3:涨幅
            BigDecimal rose = new BigDecimal(stockInfoArr[3]);
            // 当前时间
            Date curTime = DateTimeUtil.getCloseDate(DateTime.now()).toDate();
            // 封装实体对象
            StockOuterMarketIndexInfo entity = StockOuterMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(stockCode)
                    .marketName(marketName)
                    .curPoint(curPoint)
                    .updown(updown)
                    .rose(rose)
                    .curTime(curTime)
                    .build();
            entities.add(entity);
        }
        log.info("解析到{}条数据", entities.size());
        // 持久化数据
        int count = stockOuterMarketIndexInfoMapper.insertBatch(entities);
        if(count>0)
        {
            // 数据采集完成后，通知backend刷新缓存
            // 发送日期，backend收到日期并比对，判断是否需要刷新缓存
            rabbitTemplate.convertAndSend("stockExchange", "outer.market", new Date());

            log.info("当前时间{}成功插入{}条数据", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), count);
        }else{
            log.error("当前时间{}插入数据失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        }

    }

    @PostConstruct
    //@PreDestroy
    public void init(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        httpEntity = new HttpEntity<>(headers);
    }
}
