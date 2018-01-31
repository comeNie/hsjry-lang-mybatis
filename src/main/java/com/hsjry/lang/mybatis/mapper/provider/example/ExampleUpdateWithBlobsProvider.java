package com.hsjry.lang.mybatis.mapper.provider.example;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import org.apache.ibatis.mapping.MappedStatement;

import com.hsjry.lang.mybatis.mapper.helper.SqlHelper;

/**
 *
 * @author jinyn22648
 * @version $$Id: ExampleUpdateWithBlobsProvider.java, v 0.1 2018/1/29 上午9:56 jinyn22648 Exp $$
 */
public class ExampleUpdateWithBlobsProvider extends MapperTemplate {

    public ExampleUpdateWithBlobsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据example更新为带大数据字段的实体（非null更新）
     * @param ms 表信息
     * @return sql
     */
    public String updateByExampleSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass), "example"));
        sql.append(SqlHelper.updateSetColumns(entityClass, "record", true, isNotEmpty()));
        sql.append(SqlHelper.updateByExampleWhereClause());
        return sql.toString();
    }

    /**
     * 根据example更新为带大数据字段的实体（全量更新）
     * @param ms 表信息
     * @return sql
     */
    public String updateByExampleWithBLOBs(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass), "example"));
        sql.append(SqlHelper.updateSetColumns(entityClass, "record", false, false));
        sql.append(SqlHelper.updateByExampleWhereClause());
        return sql.toString();
    }
}
