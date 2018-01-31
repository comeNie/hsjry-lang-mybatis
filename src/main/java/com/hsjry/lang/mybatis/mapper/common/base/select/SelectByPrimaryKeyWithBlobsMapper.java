package com.hsjry.lang.mybatis.mapper.common.base.select;

import org.apache.ibatis.annotations.SelectProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.base.BaseSelectWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: SelectByPrimaryKeyWithBlobsMapper.java, v 0.1 2018/1/27 下午5:00 jinyn22648 Exp $$
 */
public interface SelectByPrimaryKeyWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 根据主键查询带大字段的实体
     * @param key 主键
     * @return 带大字段的实体
     */
    @SelectProvider(type = BaseSelectWithBlobsProvider.class, method = "dynamicSQL")
    T selectByPrimaryKey(Object key);
}
