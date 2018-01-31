package com.hsjry.lang.mybatis.mapper.common.base.insert;

import org.apache.ibatis.annotations.InsertProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.base.BaseInsertWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: InsertSelectiveWithBlobsMapper.java, v 0.1 2018/1/27 下午5:12 jinyn22648 Exp $$
 */
public interface InsertSelectiveWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 插入带大字段的实体（非null插入）
     * @param record 实体
     * @return 涉及数据量
     */
    @InsertProvider(type = BaseInsertWithBlobsProvider.class, method = "dynamicSQL")
    int insertSelective(T record);

}
