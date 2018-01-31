package com.hsjry.lang.mybatis.mapper.common.example.update;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.example.ExampleUpdateWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: UpdateByExampleSelectiveWithBlobsMapper.java, v 0.1 2018/1/27 下午5:15 jinyn22648 Exp $$
 */
public interface UpdateByExampleSelectiveWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 根据example更新为带大数据字段的实体（非null更新）
     * @param record 带大数据字段的实体
     * @param example 条件example
     * @return 涉及数据量
     */
    @UpdateProvider(type = ExampleUpdateWithBlobsProvider.class, method = "dynamicSQL")
    int updateByExampleSelective(T record, Object example);
}
