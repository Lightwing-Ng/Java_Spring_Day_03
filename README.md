## 第1章 `Spring_day03` 笔记

### 1.1 上次课内容回顾

#### Spring 的 IoC 的注解开发

##### 注解的入门

###### 引入 `AOP` 的包

###### 引入 `context` 约束

`<context:component-scan />`

###### 使用注解开发

- `@Component`		| 定义 `Bean`
  - `@Controller`	| `Web` 层
  	 `@Service`		| `Service` 层
  	 `@Repository`	| `DAO` 层

- 属性注入：
  - 普通属性	：`@Value`
  	 对象属性	：`@Resource`
  	 `@Autowired`	：按类型注入属性，按名称@Qulifier

##### `XML` 方式和注解方式比较

- `XML` 方式	| 适用性更广，结构更加清晰。
	 注解方式	| 适用类是自己定义，开发更方便。

##### `XML` 和注解的整合开发

- `XML` 定义类

- 注解属性注入


#### `Spring` 的 `AOP` 的基于 `AspectJ` 的 `XML` 的开发

##### `AOP` 的概述

​	AOP：面向切面编程，是 OOP 的扩展和延伸，是用来解决OOP遇到问题。

##### `Spring` 的 `AOP`

###### 底层的实现

JDK的动态代理

Cglib的动态代理

###### AOP 的相关术语

连接点：可以被拦截的点。

切入点：真正被拦截的点。

通知：增强方法

引介：类的增强

目标：被增强的对象

织入：将增强应用到目标的过程。

代理：织入增强后产生的对象

切面：切入点和通知的组合

###### `AOP` 的入门开发

- 引入 jar 包

- 编写目标类并配置

- 编写切面类并配置

- 进行 aop 的配置


```xml
<aop:config>
<aop:pointcut expression="execution(表达式)" id="pc1"/>
  <aop:aspect >
  	<aop:before method="" pointcut-ref="pc1"/>
  </aop:aspect>
</aop:config>
```

###### 通知类型

- 前置通知

- 后置通知

- 环绕通知

- 异常抛出通知

- 最终通知


###### 切入点表达式写法

### 1.2 `Spring` 的 `AOP` 的基于 `AspectJ` 注解开发

#### 1.2.1 `Spring` 的基于 `ApsectJ` 的注解的 `AOP` 开发

##### 1.2.1.1 创建项目，引入 `jar` 包

```powershell
.
├── lib
│   ├── com.springsource.org.aopalliance-1.0.0.jar # AoP
│   ├── com.springsource.org.apache.commons.logging-1.1.1.jar
│   ├── com.springsource.org.apache.log4j-1.2.15.jar
│   ├── com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar # AoP
│   ├── spring-aop-4.2.4.RELEASE.jar # AoP
│   ├── spring-aspects-4.2.4.RELEASE.jar # AoP
│   ├── spring-beans-4.2.4.RELEASE.jar
│   ├── spring-context-4.2.4.RELEASE.jar
│   ├── spring-core-4.2.4.RELEASE.jar
│   ├── spring-expression-4.2.4.RELEASE.jar
│   └── spring-test-4.2.4.RELEASE.jar
└── web.xml
```

##### 1.2.1.2 引入配置文件

##### 1.2.1.3 编写目标类并配置

```xml
<bean id="orderDao" class="com.lightwing.spring.demo1.OrderDao"/>
```

##### 1.2.1.4 编写切面类并配置

```java
public class MyAspectAnno {
    public void before() {
        System.out.println("前置增强===========");
    }
}
```

```xml
<bean id="myAspect" class="com.lightwing.spring.demo1.MyAspectAnno"/>
```

##### 1.2.1.5 使用注解的 `AOP` 对象目标类进行增强

###### 在配置文件中打开注解的 `AOP` 开发

```xml
<aop:aspectj-autoproxy/>
```

###### 在切面类上使用注解

```java
/**
 * 切面类：注解的切面类
 *
 * @author Lightwing Ng
 */
@Aspect
public class MyAspectAnno {
    @Before(value = "MyAspectAnno.pointcut2()")
    public void before() {
        System.out.println("前置增强===========");
    }
}
```

##### 1.2.1.6 编写测试类

```java
package com.lightwing.spring.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring的AOP的注解开发
 *
 * @author Lightwing Ng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDemo1 {
    @Resource(name = "orderDao")
    private OrderDao orderDao;

    @Test
    public void demo1() {
        orderDao.save();
        orderDao.update();
        orderDao.delete();
        orderDao.find();
    }
}
```

#### 1.2.2 `Spring` 的注解的 `AOP` 的通知类型

##### 1.2.2.1 `@Before | `前置通知

```java
@Before(value = "MyAspectAnno.pointcut2()")
public void before() {
    System.out.println("前置增强===========");
}
```

##### 1.2.2.2 `@AfterReturning | ` 后置通知

```java
// 后置通知:
@AfterReturning(value = "MyAspectAnno.pointcut4()", returning = "result")
public void afterReturning(Object result) {
    System.out.println("后置增强===========" + result);
}
```

##### 1.2.2.3 `@Around | ` 环绕通知

```java
// 环绕通知:
@Around(value = "MyAspectAnno.pointcut3()")
public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("环绕前增强==========");
    Object obj = joinPoint.proceed();
    System.out.println("环绕后增强==========");
    return obj;
}
```

##### 1.2.2.4 `@AfterThrowing | ` 异常抛出通知

```java
// 异常抛出通知:
@AfterThrowing(value = "MyAspectAnno.pointcut1()", throwing = "e")
public void afterThrowing(Throwable e) {
    System.out.println("异常抛出增强=========" + e.getMessage());
}
```

##### 1.2.2.5 `@After | `最终通知

```java
// 最终通知
@After(value = "MyAspectAnno.pointcut1()")
public void after() {
    System.out.println("最终增强============");
}
```

#### 1.2.3 `Spring` 的注解的 `AOP` 的切入点的配置

##### 1.2.3.1 `Spring` 的 `AOP` 的注解切入点的配置

```java
// 切入点注解：
@Pointcut(value = "execution(* com.lightwing.spring.demo1.OrderDao.find(..))")
private void pointcut1() {
}

@Pointcut(value = "execution(* com.lightwing.spring.demo1.OrderDao.save(..))")
private void pointcut2() {
}

@Pointcut(value = "execution(* com.lightwing.spring.demo1.OrderDao.update(..))")
private void pointcut3() {
}

@Pointcut(value = "execution(* com.lightwing.spring.demo1.OrderDao.delete(..))")
private void pointcut4() {
}
```

### 1.3 `Spring` 的 `JDBC` 的模板的使用

#### 1.3.1 `Spring` 的 `JDBC` 的模板

​	Spring 是 EE 开发的一站式的框架，有 EE 开发的每层的解决方案。Spring 对持久层也提供了解决方案：ORM 模块和 JDBC 的模板。
	Spring 提供了很多的模板用于简化开发：

| 持久化技术  | 模板类                                                 |
| ----------- | ------------------------------------------------------ |
| `JDBC`      | `org.springframework.jdbc.core.JdbcTemplate`           |
| `Hibernate` | `org.springframework.orm.hibernateX.HibernateTemplate` |
| `MyBatis`   | 直接使用 `JDBC` 的模板类和支持类                       |
| `JPA`       | `org.springframework.orm.jpa.JpaTemplate`              |
| `JDO`       | `org.springframework.orm.jdo.JdoTemplate`              |

##### 1.3.1.1 `JDBC` 模板使用的入门

###### 创建项目，引入 `jar` 包

- 引入基本开发包（6）
- 数据库驱动（1）
- `Spring` 的 `JDBC` 模板的 `jar` 包（）

```powershell
.
├── lib
│   ├── com.springsource.com.mchange.v2.c3p0-0.9.1.2.jar
│   ├── com.springsource.org.aopalliance-1.0.0.jar
│   ├── com.springsource.org.apache.commons.dbcp-1.2.2.osgi.jar
│   ├── com.springsource.org.apache.commons.logging-1.1.1.jar
│   ├── com.springsource.org.apache.commons.pool-1.5.3.jar
│   ├── com.springsource.org.apache.log4j-1.2.15.jar
│   ├── com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
│   ├── mysql-connector-java-5.1.7-bin.jar
│   ├── spring-aop-4.2.4.RELEASE.jar
│   ├── spring-aspects-4.2.4.RELEASE.jar
│   ├── spring-beans-4.2.4.RELEASE.jar
│   ├── spring-context-4.2.4.RELEASE.jar
│   ├── spring-core-4.2.4.RELEASE.jar
│   ├── spring-expression-4.2.4.RELEASE.jar
│   ├── spring-jdbc-4.2.4.RELEASE.jar
│   ├── spring-test-4.2.4.RELEASE.jar
│   └── spring-tx-4.2.4.RELEASE.jar
└── web.xml
```

##### 1.3.1.2 创建数据库和表

```sql
CREATE DATABASE spring4_day03;

USE spring4_day03;

CREATE TABLE account (
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(20),
	money double
);
```

##### 1.3.1.3 使用 `JDBC` 的模板：保存数据

```java
/**
 * JDBC 模板的使用
 *
 * @author Lightwing Ng
 */
public class JdbcDemo1 {
    // JDBC 模板的使用类似于 DButils
    @Test
    public void demo1() {
        // 创建连接池:
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring4_day03");
        dataSource.setUsername("root");
        dataSource.setPassword("canton0520");

        // 创建 JDBC 模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.update("INSERT INTO account VALUES (null, ?, ?)",
                "Alexander Robert",
                10000d
        );
    }
}
```

#### 1.3.2 将连接池和模板交给 `Spring` 管理

##### 1.3.2.1 引入 `Spring` 的配置文件

```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
	<property name="url" value="jdbc:mysql://localhost:3306/spring4_day03"/>
	<property name="username" value="root"/>
	<property name="password" value="canton0520"/>
</bean>

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	<property name="dataSource" ref="dataSource"/>
</bean>
```

##### 1.3.2.2 使用 `JDBC` 的模板

###### 引入 `spring_aop` 的 jar 包

```java
@Test
// 修改操作
public void demo2() {
    jdbcTemplate.update(
        "UPDATE account SET name = ?, money = ? WHERE id = ?",
        "Kristopher Hicks", 2000d, 6
    );
}
```

#### 1.3.3 使用开源的数据库连接池：

##### 1.3.3.1 `DBCP` 的使用

###### 引入 `jar` 包

###### 配置 `DBCP` 连接池

```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
	<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
	<property name="url" value="jdbc:mysql://localhost:3306/spring4_day03"/>
	<property name="username" value="root"/>
	<property name="password" value="canton0520"/>
</bean>
```

##### 1.3.3.2 `C3P0` 的使用

###### 引入 `c3p0` 连接池 `jar` 包

###### 配置 `c3p0` 连接池

```xml
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="${jdbc.driverClass}"/>
	<property name="jdbcUrl" value="${jdbc.url}"/>
	<property name="user" value="${jdbc.username}"/>
	<property name="password" value="${jdbc.password}"/>
</bean>
```

#### 1.3.4 抽取配置到属性文件

##### 1.3.4.1 定义一个属性文件

##### 1.3.4.2 在 `Spring` 的配置文件中引入属性文件

###### 第一种：

```xml
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="location" value="classpath:jdbc.properties"/>
</bean>
```

###### 第二种：

```xml
<context:property-placeholder location="classpath:jdbc.properties"/>
```

##### 1.3.4.3 引入属性文件的值

```xml
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="${jdbc.driverClass}"/>
	<property name="jdbcUrl" value="${jdbc.url}"/>
	<property name="user" value="${jdbc.username}"/>
	<property name="password" value="${jdbc.password}"/>
</bean>
```

##### 1.3.4.4 测试

```java
@Test
// 修改操作
public void demo2() {
    jdbcTemplate.update(
        "UPDATE account SET name = ?, money = ? WHERE id = ?",
        "Kristopher Hicks", 2000d, 6
    );
}
```

#### 1.3.5 使用 `JDBC` 的模板完成 `CRUD` 的操作

##### 1.3.5.1 保存操作

```java
@Test
// 保存操作
public void demo1() {
    jdbcTemplate.update(
        "INSERT INTO account VALUES(null, ?, ?)",
        "Dwayne Stokes",
        10000d
    );
}
```

##### 1.3.5.2 修改操作

```java
@Test
// 修改操作
public void demo2() {
    jdbcTemplate.update(
        "UPDATE account SET name = ?, money = ? WHERE id = ?",
        "Kristopher Hicks", 2000d, 6
    );
}
```

##### 1.3.5.3 删除操作

```java
@Test
// 删除操作
public void demo3() {
    jdbcTemplate.update(
        "DELETE FROM account WHERE id = ?",
        4606
    );
}
```

##### 1.3.5.4 查询操作

###### 查询某个属性

```java
@Test
// 查询操作：
public void demo4() {
    String name = jdbcTemplate.queryForObject(
                      "SELECT name FROM account WHERE id = ?",
                      String.class,
                      8838
                  );
    System.out.println(name);
}
```

###### 查询返回对象或集合

```java
@Test
// 统计查询
public void demo5() {
    Long count = jdbcTemplate.queryForObject(
                     "SELECT COUNT(*) FROM account",
                     Long.class
                 );
    System.out.println(count);
}
```

###### 数据封装

```java
class MyRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setName(rs.getString("name"));
        account.setMoney(rs.getDouble("money"));
        return account;
    }
}
```

##### 1.4 Spring 的事务管理

#### 1.4.1 事务的回顾

##### 1.4.1.1 什么事务

事务：逻辑上的一组操作，组成这组操作的各个单元，要么全都成功，要么全都失败。

##### 1.4.1.2 事务的特性

- **原子性** | 事务不可分割

- **一致性** | 事务执行前后数据完整性保持一致

- **隔离性** | 一个事务的执行不应该受到其他事务的干扰

- **持久性** | 一旦事务结束，数据就持久化到数据库


##### 1.4.1.3 如果不考虑隔离性引发安全性问题
- 读问题
  - **脏读**	 | 一个事务读到另一个事务未提交的数据
  -  **不可重复读** | 一个事务读到另一个事务已经提交的 `update` 的数据，导致一个事务中多次查询结果不一致
  -  **虚读/幻读** | 一个事务读到另一个事务已经提交的 `insert` 的数据，导致一个事务中多次查询结果不一致。

- 写问题
  - **丢失更新**

##### 1.4.1.4 解决读问题

- 设置事务的隔离级别
  - `Read uncommitted`  | 未提交读，任何读问题解决不了。
  - `Read committed`	 | 已提交读，解决脏读，但是不可重复读和虚读有可能发生。
  - `Repeatable read`	 | 重复读，解决脏读和不可重复读，但是虚读有可能发生。
  - `Serializable`		 | 解决所有读问题。

#### 1.4.2 `Spring` 的事务管理的 `API`

##### 1.4.2.1 `PlatformTransactionManager`：平台事务管理器

- 平台事务管理器：接口，是 Spring 用于管理事务的真正的对象。
  - `DataSourceTransactionManager`	 | 底层使用 `JDBC` 管理事务
  - `HibernateTransactionManager`	 | 底层使用 `Hibernate` 管理事务

##### 1.4.2.2 `TransactionDefinition`: 事务定义信息

- 事务定义：用于定义事务的相关的信息，隔离级别、超时信息、传播行为、是否只读


##### 1.4.2.3 `TransactionStatus`：事务的状态

- 事务状态：用于记录在事务管理过程中，事务的状态的对象。


##### 1.4.2.4 事务管理的 API 的关系：

​	Spring 进行事务管理的时候，首先平台事务管理器根据事务定义信息进行事务的管理，在事务管理过程中，产生各种状态，将这些状态的信息记录到事务状态的对象中。

#### 1.4.3 `Spring` 的事务的传播行为

##### 1.4.3.1 `Spring` 的传播行为

###### Spring 中提供了七种事务的传播行为：

- 保证多个操作在同一个事务中
  - `PROPAGATION_REQUIRED`	 | 默认值，如果 A 中有事务，使用 A 中的事务，如果A没有，创建一个新的事务，将操作包含进来
  -  `PROPAGATION_SUPPORTS`	 | 支持事务，如果A中有事务，使用A 中的事务。如果 A 没有事务，不使用事务。
  -  `PROPAGATION_MANDATORY` | 如果 A 中有事务，使用 A 中的事务。如果 A 没有事务，抛出异常。

- 保证多个操作不在同一个事务中
  - `PROPAGATION_REQUIRES_NEW`	 | 如果A中有事务，将 A 的事务挂起（暂停），创建新事务，只包含自身操作。如果 A 中没有事务，创建一个新事务，包含自身操作。
  - `PROPAGATION_NOT_SUPPORTED` | 如果A中有事务，将 A 的事务挂起。不使用事务管理。
  -  `PROPAGATION_NEVER` | 如果 A 中有事务，报异常。

- 嵌套式事务
  - `PROPAGATION_NESTED` | 嵌套事务，如果 A 中有事务，按照 A 的事务执行，执行完成后，设置一个保存点，执行 B 中的操作，如果没有异常，执行通过，如果有异常，可以选择回滚到最初始位置，也可以回滚到保存点。

#### 1.4.4 `Spring` 的事务管理

##### 1.4.4.1 搭建 `Spring` 的事务管理的环境

###### 1. 创建 `Service` 的接口和实现类

```java
/**
 * 转账的业务层的实现类
 *
 * @author Lightwing Ng
 */
public class AccountServiceImpl implements AccountService {
    // 注入 DAO
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // 注入事务管理的模板
    private TransactionTemplate trsactionTemplate;

    public void setTrsactionTemplate(TransactionTemplate trsactionTemplate) {
        this.trsactionTemplate = trsactionTemplate;
    }

    @Override
    /**
     * from：转出账号
     * to：转入账号
     * money：转账金额
     */
    public void transfer(final String from, final String to, final Double money) {
        trsactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(from, money);
                accountDao.inMoney(to, money);
            }
        });
    }
}
```

###### 2. 创建 `DAO` 的接口和实现类

```java
/**
 * 转账的 DAO 的实现类
 *
 * @author Lightwing Ng
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void outMoney(String from, Double money) {
        this.getJdbcTemplate().update(
                "UPDATE account SET = money - ? WHERE name = ?",
                money,
                from
        );
    }

    @Override
    public void inMoney(String to, Double money) {
        this.getJdbcTemplate().update(
                "UPDATE account SET money = money + ? WHERE name = ?",
                money,
                to
        );
    }
}
```

###### 3. 配置 `Service` 和 `DAO`：交给 `Spring` 管理

```xml
<bean id="accountService" class="com.lightwing.tx.demo1.AccountServiceImpl">
	<property name="accountDao" ref="accountDao"/>
	<property name="trsactionTemplate" ref="transactionTemplate"/>
</bean>

<bean id="accountDao" class="com.lightwing.tx.demo1.AccountDaoImpl">
	<property name="dataSource" ref="dataSource"/>
</bean>
```

###### 4. 在 `DAO` 中编写扣钱和加钱方法：

- 配置连接池和 `JDBC` 的模板

```xml
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="${jdbc.driverClass}"/>
 	<property name="jdbcUrl" value="${jdbc.url}"/>
	<property name="user" value="${jdbc.username}"/>
	<property name="password" value="${jdbc.password}"/>
</bean>
```

- 在 `DAO` 注入 `JDBC` 的模板：

```xml
<bean id="accountDao" class="com.lightwing.tx.demo1.AccountDaoImpl">
	<property name="dataSource" ref="dataSource"/>
</bean>
```

###### 5. 测试

```java
/**
 * 测试转账的环境
 *
 * @author Lightwing Ng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx.xml")
public class SpringDemo1 {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void demo1() {
        accountService.transfer("Alexander Robert", "Jonathan Gibson", 1000d);
    }
}
```

#### 1.4.5 `Spring` 的事务管理：一类：编程式事务（需要手动编写代码）--了解

##### 1.4.5.1 第一步：配置平台事务管理器

```xml
<bean id="transactionManager"
       class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"/>
</bean>
```

##### 1.4.5.2 第二步：`Spring` 提供了事务管理的模板类

###### 配置事务的管理的模板类

##### 1.4.5.3 第三步：在业务层注入事务管理的模板

##### 1.4.5.4 编写事务管理的代码

```java
/**
 * 转账的业务层的实现类
 *
 * @author Lightwing Ng
 */
public class AccountServiceImpl implements AccountService {
    // 注入 DAO
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String from, String to, Double money) {
        accountDao.outMoney(from, money);
        int d = 1 / 0;
        accountDao.inMoney(to, money);
    }
}
```

##### 1.4.5.5 测试：

```java
/**
 * 测试转账的环境
 *
 * @author Lightwing Ng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx2.xml")
public class SpringDemo2 {
    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    public void demo1() {
        accountService.transfer("Nixon James", "Hayden Green", 1000d);
    }
}
```

#### 1.4.6 Spring 的事务管理：二类：声明式事务管理（通过配置实现）--- AoP

##### 1.4.6.1 `XML` 方式的声明式事务管理

###### 第一步：引入 `AoP` 的开发包

###### 第二步：恢复转账环境

###### 第三步：配置事务管理器

```xml
<bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"/>
</bean>
```

第四步：配置增强

第五步：AOP 的配置

测试

##### 1.4.6.2 注解方式的声明式事务管理

第一步：引入aop的开发包

第二步：恢复转账环境

第三步：配置事务管理器

第四步：开启注解事务

第五步：在业务层添加注解