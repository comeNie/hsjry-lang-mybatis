package com.hsjry.lang.mybatis.mapper.common.base;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseWithBlobsMapper.java, v 0.1 2018/1/27 下午6:10 jinyn22648 Exp $$
 */
public interface BaseWithBlobsMapper<T extends ModelWithBlobs>
        extends BaseDeleteWithBlobsMapper<T>, BaseInsertWithBlobsMapper<T>, BaseSelectWithBlobsMapper<T>,
        BaseUpdateWithBlobsMapper<T> {

}
