package com.hsjry.lang.mybatis.mapper.common.example.select;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.example.ExampleSelectWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: SelectByExampleWithBlobsMapper.java, v 0.1 2018/1/27 下午4:43 jinyn22648 Exp $$
 */
public interface SelectByExampleWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 根据Example查询带大字段的参数
     * @param example 条件
     * @return 数据
     */
    @SelectProvider(type = ExampleSelectWithBlobsProvider.class, method = "dynamicSQL")
    List<T> selectByExampleWithBLOBs(Object example);
}
