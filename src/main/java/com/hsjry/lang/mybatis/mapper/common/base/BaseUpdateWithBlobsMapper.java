package com.hsjry.lang.mybatis.mapper.common.base;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.common.base.update.UpdateByPrimaryKeySelectiveWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.base.update.UpdateByPrimaryKeyWithBlobsMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseUpdateWithBlobsMapper.java, v 0.1 2018/1/27 下午6:10 jinyn22648 Exp $$
 */
public interface BaseUpdateWithBlobsMapper<T extends ModelWithBlobs>
        extends UpdateByPrimaryKeySelectiveWithBlobsMapper<T>, UpdateByPrimaryKeyWithBlobsMapper<T> {

}
