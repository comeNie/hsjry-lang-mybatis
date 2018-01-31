package com.hsjry.lang.mybatis.mapper.common.example.update;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.example.ExampleUpdateWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: UpdateByExampleWithBlobsMapper.java, v 0.1 2018/1/27 下午5:58 jinyn22648 Exp $$
 */
public interface UpdateByExampleWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 根据example更新为带大数据字段的实体（全量更新）
     * @param record 带大数据字段的实体
     * @param example 条件example
     * @return 涉及数据量
     */
    @UpdateProvider(type = ExampleUpdateWithBlobsProvider.class, method = "dynamicSQL")
    int updateByExampleWithBLOBs(T record, Object example);

}
