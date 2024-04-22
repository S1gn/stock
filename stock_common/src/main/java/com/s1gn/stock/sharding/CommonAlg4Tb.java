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
 * @ClassName CommonAlg4Tb
 * @Description 定义公共的数据库分表算法，只有个股
 * @Author S1gn
 * @Date 2024/4/22 14:22
 * @Version 1.0
 */
public class CommonAlg4Tb implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    /**
     * @Auther s1gn
     * @Description 精准分表查询算法，查询条件为=或者in时触发，按月分表
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
        // 获取分片键值的年月份，从集合中过滤出符合条件的表
        String yearMonth = new DateTime(value).toString("yyyyMM");
        Optional<String> result= collection.stream().filter(e -> e.endsWith(yearMonth)).findFirst();
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }
    /**
     * @Auther s1gn
     * @Description 范围分表查询算法，查询条件为between时触发，按月分表
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
            Integer lowerYearMonth = Integer.parseInt(new DateTime(lower).toString("yyyyMM"));
            // 过滤出符合条件的库
            collection= collection.stream().filter(e ->
                    Integer.parseInt(e.substring(e.lastIndexOf("_") + 1)) >=
                            lowerYearMonth).collect(Collectors.toList()
            );
        }
        // 获取上限
        if(valueRange.hasUpperBound()){
            Date upper = valueRange.upperEndpoint();
            // 获取上限的年份
            Integer upperYearMonth = Integer.parseInt(new DateTime(upper).toString("yyyyMM"));
            // 过滤出符合条件的库
            collection= collection.stream().filter(e ->
                    Integer.parseInt(e.substring(e.lastIndexOf("_") + 1)) <=
                            upperYearMonth).collect(Collectors.toList()
            );
        }
        return collection;
    }
}
