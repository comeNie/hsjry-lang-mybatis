package com.hsjry.lang.mybatis.mapper.provider.example;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import org.apache.ibatis.mapping.MappedStatement;

import com.hsjry.lang.mybatis.mapper.helper.SqlHelper;

/**
 *
 * @author jinyn22648
 * @version $$Id: ExampleSelectWithBlobsProvider.java, v 0.1 2018/1/29 上午9:56 jinyn22648 Exp $$
 */
public class ExampleSelectWithBlobsProvider extends MapperTemplate {

    public ExampleSelectWithBlobsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据Example查询带大字段的参数
     * @param ms 表信息
     * @return sql
     */
    public String selectByExampleWithBLOBs(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append("<if test=\"distinct\">distinct</if>");
        //支持查询指定列
        sql.append(SqlHelper.exampleSelectColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleOrderBy(entityClass));
        sql.append(SqlHelper.exampleForUpdate());
        return sql.toString();
    }
}
