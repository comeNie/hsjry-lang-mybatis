package com.hsjry.lang.mybatis.mapper.provider.example;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import org.apache.ibatis.mapping.MappedStatement;

/**
 *
 * @author jinyn22648
 * @version $$Id: ExampleCountWithBlobsProvider.java, v 0.1 2018/1/29 上午9:54 jinyn22648 Exp $$
 */
public class ExampleCountWithBlobsProvider extends MapperTemplate {

    public ExampleCountWithBlobsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据条件查询数量
     * @param ms 表信息
     * @return sql
     */
    public String countByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleForUpdate());
        return sql.toString();
    }

}
