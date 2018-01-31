package com.hsjry.lang.mybatis.mapper.provider.base;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import org.apache.ibatis.mapping.MappedStatement;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseSelectWithBlobsProvider.java, v 0.1 2018/1/29 上午9:48 jinyn22648 Exp $$
 */
public class BaseSelectWithBlobsProvider extends MapperTemplate {

    public BaseSelectWithBlobsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据主键查询带大字段的实体
     * @param ms 表信息
     * @return sql
     */
    public String selectByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    /**
     * 查询带大字段的所有数据
     * @param ms 表信息
     * @return sql
     */
    public String selectAllWithBlobs(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        return sql.toString();
    }
}
