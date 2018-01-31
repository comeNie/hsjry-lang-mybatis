package com.hsjry.lang.mybatis.generator.plugins;

import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.hsjry.lang.mybatis.generator.HsjryCommentGenerator;
import com.hsjry.lang.mybatis.mapper.ModelWithBlobs;
import com.hsjry.lang.mybatis.mapper.common.CommonWithBlobsMapper;
import com.hsjry.lang.mybatis.mapper.common.CommonWithoutBlobsMapper;

/**
 * Description:
 *
 * Created on 2017年3月4日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class MapperPlugin extends PluginAdapter {

    /**
     * mappers
     */
    private Set<String> mappers = Sets.newHashSet();

    /**
     * caseSensitive
     */
    private boolean caseSensitive = false;

    /**
     * 开始的分隔符，例如mysql为`，sqlserver为[
     */
    private String beginningDelimiter = "";

    /**
     * 结束的分隔符，例如mysql为`，sqlserver为]
     */
    private String endingDelimiter = "";

    private static final String TRUE = "TRUE";

    /**
     * 数据库模式
     */
    private String schema;

    /**
     * 注释生成器
     */
    private CommentGeneratorConfiguration commentCfg;

    /**
     * author
     */
    private String author;

    /**
     * currentDateStr
     */
    private String currentDateStr;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        //设置默认的注释生成器
        commentCfg = new CommentGeneratorConfiguration();
        commentCfg.setConfigurationType(HsjryCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(commentCfg);
        for (Object key : context.getProperties()
                .keySet()) {
            commentCfg.addProperty((String) key, context.getProperty((String) key));
        }
        //支持oracle获取注释#114
        context.getJdbcConnectionConfiguration()
                .addProperty("remarksReporting", TRUE.toLowerCase());
    }

    @Override
    public void setProperties(Properties properties) {
        for (Object key : context.getProperties()
                .keySet()) {
            properties.put(key, context.getProperty((String) key));
        }
        super.setProperties(properties);
        String mappers = this.properties.getProperty("mappers");
        if (StringUtility.stringHasValue(mappers)) {
            this.mappers.addAll(Splitter.on(',')
                    .splitToList(mappers));
        } else {
            throw new MapperException("Mapper插件缺少必要的mappers属性!");
        }
        String caseSensitive = this.properties.getProperty("caseSensitive");
        if (StringUtility.stringHasValue(caseSensitive)) {
            this.caseSensitive = TRUE.equalsIgnoreCase(caseSensitive);
        }
        String beginningDelimiter = this.properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }
        String endingDelimiter = this.properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }
        String schema = this.properties.getProperty("schema");
        if (StringUtility.stringHasValue(schema)) {
            this.schema = schema;
        }
        String authorString = this.properties.getProperty("author");
        if (StringUtility.stringHasValue(authorString)) {
            author = authorString;
        }
        currentDateStr = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
    }

    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtility.stringHasValue(schema)) {
            nameBuilder.append(schema);
            nameBuilder.append(".");
        }
        nameBuilder.append(beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(endingDelimiter);
        return nameBuilder.toString();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 生成的Mapper接口
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        //获取实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        //import接口
        for (String mapper : mappers) {
            if (CommonWithBlobsMapper.class.getCanonicalName().equals(mapper) || CommonWithoutBlobsMapper.class.getCanonicalName().equals(mapper)) {
                continue;
            }
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }
        //import实体类
        interfaze.addImportedType(entityType);

        // 根据是否存在Blob字段，实现不同的接口
        if (Iterators.any(mappers.iterator(), new Predicate<String>() {
            @Override
            public boolean apply(String mapper) {
                return CommonWithBlobsMapper.class.getCanonicalName().equals(mapper);
            }
        }) && CollectionUtils.isNotEmpty(introspectedTable.getBLOBColumns())) {
            interfaze.addImportedType(new FullyQualifiedJavaType(CommonWithBlobsMapper.class.getCanonicalName()));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(
                    CommonWithBlobsMapper.class.getCanonicalName() + "<" + entityType.getShortName() + "WithBLOBs>"));
            interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "WithBLOBs"));
        } else {
            interfaze.addImportedType(new FullyQualifiedJavaType(CommonWithoutBlobsMapper.class.getCanonicalName()));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(
                    CommonWithoutBlobsMapper.class.getCanonicalName() + "<" + entityType.getShortName() + ">"));
        }

        // 加注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * Description: " + introspectedTable.getFullyQualifiedTable() + " Mapper 接口");
        interfaze.addJavaDocLine(" *");
        interfaze.addJavaDocLine(" * Created on " + currentDateStr);
        interfaze.addJavaDocLine(" * @author " + author);
        interfaze.addJavaDocLine(" * @version 1.0");
        interfaze.addJavaDocLine(" * @since v1.0");
        interfaze.addJavaDocLine(" */");
        return true;
    }

    /**
     * 处理实体类的包和@Table注解
     */
    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //  导入Table注解
        importClassTableAnnotation(topLevelClass, introspectedTable);
        // 引入JPA注解
        importFieldAnnotation(topLevelClass, Column.class);
        // 判断是否有@GeneratedValue注解的字段
        importFieldAnnotation(topLevelClass, GeneratedValue.class, GenerationType.class);
        // 导入swagger配置
        //        importFieldAnnotation(topLevelClass, ApiModelProperty.class);
        // 导入主键生成策略
        importFieldAnnotation(topLevelClass, Id.class);
        // 判断field是否是Transient，如果是则导入对应包
        importFieldAnnotation(topLevelClass, Transient.class);
        // 导入列类型
        importFieldAnnotation(topLevelClass, ColumnType.class, JdbcType.class);

        // 实体类注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * Description: " + introspectedTable.getFullyQualifiedTable() + " 实体");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * Created on " + currentDateStr);
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @version 1.0");
        topLevelClass.addJavaDocLine(" * @since v1.0");
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 导入Table注解
     * @param topLevelClass topLevelClass
     * @param introspectedTable introspectedTable
     */
    private void importClassTableAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //引入JPA注解
        topLevelClass.addImportedType("javax.persistence.Table");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        //如果包含空格，或者需要分隔符，需要完善
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = context.getBeginningDelimiter() + tableName + context.getEndingDelimiter();
        }
        //是否忽略大小写，对于区分大小写的数据库，会有用
        if (caseSensitive && !topLevelClass.getType()
                .getShortName()
                .equals(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (!topLevelClass.getType()
                .getShortName()
                .equalsIgnoreCase(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (StringUtility.stringHasValue(schema) || StringUtility.stringHasValue(beginningDelimiter) ||
                StringUtility.stringHasValue(endingDelimiter)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        }
    }

    /**
     * field上的注解，需要在class上进行导入
     * @param topLevelClass topLevelClass
     * @param mainAnnotation 字段上的注解
     * @param extAnnotations 该注解所需额外的导入类
     */
    private void importFieldAnnotation(TopLevelClass topLevelClass, final Class<?> mainAnnotation,
            Class<?>... extAnnotations) {
        boolean hasAnnotationOnField = Iterators.any(topLevelClass.getFields()
                .iterator(), new Predicate<Field>() {
            @Override
            public boolean apply(Field field) {
                return field != null && CollectionUtils.isNotEmpty(field.getAnnotations()) && Iterators.any(
                        field.getAnnotations()
                                .iterator(), new Predicate<String>() {
                            @Override
                            public boolean apply(String annotation) {
                                return StringUtils.isNotBlank(annotation) && annotation.startsWith(
                                        "@" + mainAnnotation.getSimpleName());
                            }
                        });
            }
        });
        if (hasAnnotationOnField) {
            topLevelClass.addImportedType(mainAnnotation.getCanonicalName());
            if (ArrayUtils.isNotEmpty(extAnnotations)) {
                for (Class<?> extAnnotation : extAnnotations) {
                    topLevelClass.addImportedType(extAnnotation.getCanonicalName());
                }
            }
        }
    }

    /**
     * 导入ApiModel配置
     * @param topLevelClass topLevelClass
     * @param introspectedTable introspectedTable
     */
    private void importClassApiModelAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addAnnotation("@ApiModel(\"" + introspectedTable.getFullyQualifiedTable() + "实体\")");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
    }

    /**
     * 生成基础实体类
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成实体类注解KEY对象
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成带BLOB字段的对象
     * XxxWithBlobs
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        FullyQualifiedJavaType modelWithBlobsClass = new FullyQualifiedJavaType(ModelWithBlobs.class.getCanonicalName());
        topLevelClass.addSuperInterface(modelWithBlobsClass);
        topLevelClass.addImportedType(modelWithBlobsClass);
        return true;
    }

    /*下面所有return false的方法都不生成。这些都是基础的CRUD方法，使用通用Mapper实现*/

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    // 以下方法，关闭所有跟Example相关的count、update、delete、select方法，这些方法在通用Mapper中已经存在

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

}