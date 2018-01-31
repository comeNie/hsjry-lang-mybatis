package com.hsjry.lang.mybatis.mapper.common.example;

import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.common.example.select.SelectByExampleWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.example.update.UpdateByExampleSelectiveWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.example.update.UpdateByExampleWithBlobsMapper;

/**
 *
 * @author jinyn22648
 * @version $$Id: ExampleWithBlobsMapper.java, v 0.1 2018/1/27 下午4:51 jinyn22648 Exp $$
 */
public interface ExampleWithBlobsMapper<T extends ModelWithBlobs>
        extends SelectByExampleWithBlobsMapper<T>, UpdateByExampleSelectiveWithBlobsMapper<T>,
        UpdateByExampleWithBlobsMapper<T> {

}
