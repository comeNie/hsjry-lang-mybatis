package com.hsjry.lang.mybatis.mapper.provider.base;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import org.apache.ibatis.mapping.MappedStatement;

/**
 *
 * @author jinyn22648
 * @version $$Id: BaseUpdateWithBlobsProvider.java, v 0.1 2018/1/29 上午9:48 jinyn22648 Exp $$
 */
public class BaseUpdateWithBlobsProvider extends MapperTemplate {

    public BaseUpdateWithBlobsProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据主键更新为带大数据字段的实体（非null更新）
     * @param ms 表信息
     * @return sql
     */
    public String updateByPrimaryKeySelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    /**
     * 根据主键更新为带大数据字段的实体（全量更新）
     * @param ms 表信息
     * @return sql
     */
    public String updateByPrimaryKeyWithBLOBs(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }
}
