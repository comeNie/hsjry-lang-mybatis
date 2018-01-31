package com.hsjry.lang.mybatis.mapper.common.base.select;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.provider.base.BaseSelectWithBlobsProvider;

/**
 *
 * @author jinyn22648
 * @version $$Id: SelectAllWithBlobsMapper.java, v 0.1 2018/1/30 下午6:45 jinyn22648 Exp $$
 */
public interface SelectAllWithBlobsMapper<T extends ModelWithBlobs> {

    /**
     * 查询带大字段的所有数据
     * @return 所有数据
     */
    @SelectProvider(type = BaseSelectWithBlobsProvider.class, method = "dynamicSQL")
    List<T> selectAllWithBlobs();
}
