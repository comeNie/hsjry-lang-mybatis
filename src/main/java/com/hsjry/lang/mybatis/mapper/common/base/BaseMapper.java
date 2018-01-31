package com.hsjry.lang.mybatis.mapper.common.base;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseMapper.java, v 0.1 2018/1/29 下午6:53 jinyn22648 Exp $$
 */
public interface BaseMapper<T>
        extends BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T> {

}
