package com.hsjry.lang.mybatis.mapper.common;

import tk.mybatis.mapper.common.RowBoundsMapper;

import com.hsjry.lang.mybatis.mapper.common.base.BaseMapper;
import com.hsjry.lang.mybatis.mapper.common.example.ExampleMapper;

/**
 * Description: CommonMapper.java
 *
 * Created on 2018年01月05日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface CommonMapper<T> extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T> {

}
