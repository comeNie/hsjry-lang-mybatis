package com.hsjry.lang.mybatis.mapper.common;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.common.base.BaseWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.example.ExampleWithBlobsMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: CommonWithBlobsMapper.java, v 0.1 2018/1/27 下午4:42 jinyn22648 Exp $$
 */
public interface CommonWithBlobsMapper<T extends ModelWithBlobs>
        extends BaseWithBlobsMapper<T>, ExampleWithBlobsMapper<T> {

}
