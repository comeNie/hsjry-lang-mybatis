package com.hsjry.lang.mybatis.mapper.common.base;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseWithoutBlobsMapper.java, v 0.1 2018/1/30 下午6:29 jinyn22648 Exp $$
 */
public interface BaseWithoutBlobsMapper<T>
        extends BaseSelectWithoutBlobsMapper<T>, BaseInsertWithoutBlobsMapper<T>, BaseUpdateWithoutBlobsMapper<T>,
        BaseDeleteWithoutBlobsMapper<T> {

}
