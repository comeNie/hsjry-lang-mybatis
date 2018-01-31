package com.hsjry.lang.mybatis.mapper.common.base;

import tk.mybatis.mapper.common.base.delete.DeleteByPrimaryKeyMapper;
import tk.mybatis.mapper.common.base.delete.DeleteMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseDeleteMapper.java, v 0.1 2018/1/29 下午6:52 jinyn22648 Exp $$
 */
public interface BaseDeleteMapper<T> extends DeleteMapper<T>, DeleteByPrimaryKeyMapper<T> {

}
