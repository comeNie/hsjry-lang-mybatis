package com.hsjry.lang.mybatis.mapper.common.base;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.common.base.insert.InsertSelectiveWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.base.insert.InsertWithBlobsMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseInsertWithBlobsMapper.java, v 0.1 2018/1/27 下午6:09 jinyn22648 Exp $$
 */
public interface BaseInsertWithBlobsMapper<T extends ModelWithBlobs>
        extends InsertSelectiveWithBlobsMapper<T>, InsertWithBlobsMapper<T> {

}
