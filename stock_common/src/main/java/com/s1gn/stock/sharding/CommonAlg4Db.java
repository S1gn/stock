package com.s1gn.stock.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName CommonAlg4Db
 * @Description 定义公共的数据库分库算法，覆盖个股、大盘、板块
 * @Author S1gn
 * @Date 2024/4/22 13:52
 * @Version 1.0
 */
public class CommonAlg4Db implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    /**
     * @Auther s1gn
     * @Description 精准分库查询算法，查询条件为=或者in时触发，按年分库
     * @Date 2024/4/22 13:53
     * @param collection
     * @param preciseShardingValue
     * @return {@link String }
     **/
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        // 获取逻辑表
        String logicTableName = preciseShardingValue.getLogicTableName();
        // 获取分片键
        String columnName = preciseShardingValue.getColumnName();
        // 获取分片键值
        Date value = preciseShardingValue.getValue();
        // 获取分片键值的年份，从集合中过滤出符合条件的库
        String year = new DateTime(value).getYear() + "";
        Optional<String> result= collection.stream().filter(e -> e.endsWith(year)).findFirst();
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }
    /**
     * @Auther s1gn
     * @Description 范围分库查询算法，查询条件为between时触发，按年分库
     * @Date 2024/4/22 14:02
     * @param collection
     * @param rangeShardingValue
     * @return {@link Collection< String> }
     **/
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        // 获取逻辑表
        String logicTableName = rangeShardingValue.getLogicTableName();
        // 获取分片键
        String columnName = rangeShardingValue.getColumnName();
        // 获取范围数据封装
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        // 获取下限
        if(valueRange.hasLowerBound()){
            Date lower = valueRange.lowerEndpoint();
            // 获取下限的年份
            String lowerYear = new DateTime(lower).getYear() + "";
            // 过滤出符合条件的库
            collection= collection.stream().filter(e ->
                    Integer.parseInt(e.substring(e.lastIndexOf("-") + 1)) >=
                            Integer.parseInt(lowerYear)).collect(Collectors.toList()
            );
        }
        // 获取上限
        if(valueRange.hasUpperBound()){
            Date upper = valueRange.upperEndpoint();
            // 获取上限的年份
            String upperYear = new DateTime(upper).getYear() + "";
            // 过滤出符合条件的库
            collection= collection.stream().filter(e ->
                    Integer.parseInt(e.substring(e.lastIndexOf("-") + 1)) <=
                            Integer.parseInt(upperYear)).collect(Collectors.toList()
            );
        }
        return collection;
    }
}
