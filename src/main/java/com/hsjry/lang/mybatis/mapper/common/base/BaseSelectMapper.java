package com.hsjry.lang.mybatis.mapper.common.base;

import tk.mybatis.mapper.common.base.select.ExistsWithPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.select.SelectAllMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseSelectMapper.java, v 0.1 2018/1/29 下午6:53 jinyn22648 Exp $$
 */
public interface BaseSelectMapper<T>
        extends SelectOneMapper<T>, SelectAllMapper<T>, SelectMapper<T>, ExistsWithPrimaryKeyMapper<T> {

}
