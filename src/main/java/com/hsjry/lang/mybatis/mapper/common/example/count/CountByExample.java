package com.hsjry.lang.mybatis.mapper.common.example.count;

import org.apache.ibatis.annotations.SelectProvider;

import com.hsjry.lang.mybatis.mapper.provider.example.ExampleCountWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: CountByExample.java, v 0.1 2018/1/27 下午7:05 jinyn22648 Exp $$
 */
public interface CountByExample {

    /**
     * 根据条件查询数量
     * 跟{@link tk.mybatis.mapper.common.example.SelectCountByExampleMapper}一个含义，
     * 只是方法名匹配generator自动生成的方法名
     * @param example 条件
     * @return 满足条件的数量
     */
    @SelectProvider(type = ExampleCountWithBlobsProvider.class, method = "dynamicSQL")
    int countByExample(Object example);
}
