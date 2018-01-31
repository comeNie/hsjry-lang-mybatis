package com.hsjry.lang.mybatis.mapper.common.example;

import tk.mybatis.mapper.common.example.DeleteByExampleMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.SelectOneByExampleMapper;
import tk.mybatis.mapper.common.example.UpdateByExampleMapper;

import com.hsjry.lang.mybatis.mapper.common.example.count.CountByExample;

/**
 *
 * @author jinyn22648
 * @version $$Id: ExampleMapper.java, v 0.1 2018/1/27 下午6:16 jinyn22648 Exp $$
 */
public interface ExampleMapper<T>
        extends SelectByExampleMapper<T>, SelectOneByExampleMapper<T>, CountByExample, DeleteByExampleMapper<T>,
        UpdateByExampleMapper<T> {

}
