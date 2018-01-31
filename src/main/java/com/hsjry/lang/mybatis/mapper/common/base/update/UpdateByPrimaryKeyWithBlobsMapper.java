package com.hsjry.lang.mybatis.mapper.common.base.update;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.base.BaseUpdateWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: UpdateByPrimaryKeyWithBlobsMapper.java, v 0.1 2018/1/29 上午10:32 jinyn22648 Exp $$
 */
public interface UpdateByPrimaryKeyWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 根据主键更新为带大数据字段的实体（全量更新）
     * @param record 带大数据字段的实体
     * @return 涉及数据量
     */
    @UpdateProvider(type = BaseUpdateWithBlobsProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeyWithBLOBs(T record);
}
