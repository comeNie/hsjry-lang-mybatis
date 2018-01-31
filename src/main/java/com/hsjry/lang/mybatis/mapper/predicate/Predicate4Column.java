package com.hsjry.lang.mybatis.mapper.predicate;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Predicate;

import org.apache.ibatis.type.JdbcType;

/**
 *
 * @author jinyn22648
 * @version $$Id: Predicate4Column.java, v 0.1 2018/1/29 下午2:52 jinyn22648 Exp $$
 */
public class Predicate4Column implements Predicate<EntityColumn> {

    @Override
    public boolean apply(EntityColumn input) {
        return input != null && !isBlobColumn(input.getJdbcType());
    }

    private boolean isBlobColumn(JdbcType jdbcType) {
        if (jdbcType == null) {
            return false;
        }
        if (JdbcType.BINARY.equals(jdbcType) || JdbcType.BLOB.equals(jdbcType) || JdbcType.CLOB.equals(jdbcType) ||
                JdbcType.LONGVARBINARY.equals(jdbcType) || JdbcType.LONGVARCHAR.equals(jdbcType) ||
                JdbcType.NCLOB.equals(jdbcType) || JdbcType.VARBINARY.equals(jdbcType)) {
            return true;
        }
        return false;
    }
}