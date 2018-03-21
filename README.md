#### hsjry mybatis插件

#### Mybatis 通用Mapper介绍
业务痛点：
1. 想使用批量insert怎么办，手写sql？
2. 是否想更方便的开启Mybatis二级缓存？
介绍：
通用Mapper可以自定义新增许多比较通用的接口，例如批量插入，selectOne(根据Example仅查出至多1条记录)，乐观锁等。使用通用Mapper后，所不需要生成大量重复的接口，针对于有代码洁癖的人是一项利好。
#### Mybatis generator介绍
业务痛点：
1. 代码中生成的model、xml、mapper不可读注释
2. 表中改字段，需要本地生成转到工程里，不麻烦吗？
介绍：Mybatis generator是Mybatis附属项目，可以自定义生成的model、xml、mapper等代码，优化其中的注释等等，减去大量不可读的注释，简直代码洁癖者的强大利器。同时自定义一些配置，可以对代码进行格式化，相当于生成后的代码直接满足checkstyle这类检查工具的要求。

#### 使用指南

##### 1. 引入该工程
放入`hsjry-lang-parent`下，并在`hsjry-lang-parent`的`pom.xml`文件中增加`module`
```
<module>hsjry-lang-mybatis</module>
```

##### 2. `hsjry-lang-parent`使用`maven`进行编译打包并进行发布
`mvn clean install -Dmaven.test.skip=true`

##### 3. 各模块`dal`的`pom.xml`配置
增加依赖
```
<dependency>
  <groupId>hsjry.lang</groupId>
  <artifactId>hsjry-lang-mybatis</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
增加`maven plugin`插件
```
<build>
  <pluginManagement>
    <plugins>
      <!-- mybatis自动生成代码的插件 -->
      <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.6</version>
        <configuration>
          <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
          <overwrite>true</overwrite>
          <verbose>true</verbose>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql_version}</version>
          </dependency>
          <dependency>
            <groupId>hsjry.lang</groupId>
            <artifactId>hsjry-lang-mybatis</artifactId>
            <version>1.0-SNAPSHOT</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </pluginManagement>
</build>
```

##### 4. `spring-datasource.xml`改造
原`org.mybatis.spring.mapper.MapperScannerConfigurer`的`Bean`修改为如下内容
```
<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
  <property name="basePackage" value="com.hsjry.market.dal.dao.mapper.**" />
  <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
  <property name="properties">
    <value>
      mappers=com.hsjry.lang.mybatis.mapper.common.CommonMapper,com.hsjry.lang.mybatis.mapper.common.CommonWithBlobsMapper,com.hsjry.lang.mybatis.mapper.common.CommonWithoutBlobsMapper
      columnPredicate=com.hsjry.lang.mybatis.mapper.predicate.Predicate4Column
    </value>
  </property>
</bean>
```

    注意：
    MapperScannerConfigurer的类路径是从org改为tk的

##### 5. 引入`generatorConfig.xml`文件
以下内容放在`dal`下的`src/main/resources`，命名为`generatorConfig.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
  <context id="Mysql" targetRuntime="MyBatis3">

    <property name="author" value="author.name"/>
    <property name="javaFormatter" value="com.hsjry.lang.mybatis.generator.SortImportsAndFieldsJavaFormatter"/>

    <plugin type="com.hsjry.lang.mybatis.generator.plugins.MapperPlugin">
      <property name="mappers" value="com.hsjry.lang.mybatis.mapper.common.CommonMapper,com.hsjry.lang.mybatis.mapper.common.CommonWithBlobsMapper,com.hsjry.lang.mybatis.mapper.common.CommonWithoutBlobsMapper"/>
    </plugin>

    <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
    <plugin type="org.mybatis.generator.plugins.ToStringPlugin">
      <property name="useToStringFromRoot" value="true"/>
    </plugin>

    <jdbcConnection driverClass="jdbc.driver"
                    connectionURL="jdbc.url"
                    userId="jdbc.user" password="jdbc.password">
    </jdbcConnection>

    <javaModelGenerator targetPackage="model.package" targetProject="src/main/java"/>

    <sqlMapGenerator targetPackage="sql.package" targetProject="src/main/resources"/>

    <javaClientGenerator targetPackage="java.mapper.package" targetProject="src/main/java"
                         type="XMLMAPPER"/>

    <table tableName="table.name" domainObjectName="model.name"/>
  </context>
</generatorConfiguration>
```

其中可以修改的内容有
```
author.name 作者名
jdbc.driver jdbcDriver地址
jdbc.url jdbc链接url
jdbc.user jdbc用户名
jdbc.password jdbc密码
model.package 实体类放置位置
sql.package mapper.xml放置位置
java.mapper.package mapper接口放置位置
table.name 表名
model.name 生成实体类的名字
```

##### 6. 使用`Mybatis generator`自动生成类
在`dal`层执行`maven`命令`mybatis-generator:generate`来生成类和`xml`文件

##### 7. 使用通用`Mapper`
使用方式和之前一样，所对应的mapper方法，都在继承的接口中

#### 补充说明
所需新增和修改的文件，都已放在`docs`目录下
