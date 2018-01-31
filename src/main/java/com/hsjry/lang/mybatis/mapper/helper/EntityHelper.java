package com.hsjry.lang.mybatis.mapper.helper;

import tk.mybatis.mapper.entity.EntityColumn;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jinyn22648
 * @version $$Id: EntityHelper.java, v 0.1 2018/1/30 下午5:38 jinyn22648 Exp $$
 */
public class EntityHelper extends tk.mybatis.mapper.mapperhelper.EntityHelper {



    /**
     * 获取全部列
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }
}
