HyDRO 基于Spring+Mybatis+Velocity的数据库中间件
============

## 项目背景

> 
DataBase RPC ORM【简称HyDRO】，项目起源于2017年，在CCMP项目实施中有一项需求是要在业务工程统一调度统一存储工程，实现业务与数据分离。在实施过程中发现我们的所有SQL语句只能写在代码里，这让代码的可读性和维护性非常难，这让我们想到Mybatis，在对Mybatis的深入研究后发现Mybatis是依赖于datasource无法单独只使用SQL模板解析。现在我们急需一套解决方案。所以从中孕育出了HYDRO数据中间件，HYDRO数据中间件是基于Mybatis原理进行改造，启动时自动加载SQL模板文件，并对DAO接口进行绑定，在执行SQL语句时，可自定义到远程的数据服务工程中。


## 版本说明
*   HyDRO  v1.x

## 本版说明(v1.x)

- 支持常规的数据库操作，SELECT,INSERT,UPDATE,DELETE
- 支持批量SQL操作（INSERT,UPDATE,DELETE）
- 在SQL模板中，支持对Velocity语句解析
- 支持自定义SQL远程执行器
- 支持数据对象和数据库字段映射
- 暂不支持事务

## HyDRO 1.x 运行环境

*   JDK 8
*   Spring Boot 1.5.x
*   Tomcat 8.x + / Jetty 9.x +
*   Maven 3.3.x +

## HyDRO 1.x 目录结构

```java
Hydro
├── hydro-core (Hydro核心项目)
│       ├── annotation      注解
│       ├── exception       异常
│       ├── io              IO
│       ├── mapping         映射
│       ├── scan            扫描器
│       ├── session         会话
│       ├── velocity        模板引擎
│       └── xml             XML解析器
│
└── hydro-test (Hydro实例项目)
        │
        ├─ hydro-test-api           (RPC接口)
        │
        ├─ hydro-test-client        (RPC客户端)
        │     ├── java
        │     │     ├── bean                             Java Bean 对象
        │     │     │      ├── mapper                    POJO对象转换器
        │     │     │      ├── po                        数据对象
        │     │     │      └── vo                        视图对象
        │     │     ├── controller                       控制器
        │     │     ├── dao                              数据层接口
        │     │     ├── service                          服务层
        │     │     └── utils                            工具
        │     │            └── support                   第三方工具
        │     │                   ├── dubbo              RPC Dubbo
        │     │                   ├── hydro              * Hydro
        │     │                   │      └── extend      Hydro自定义扩展
        │     │                   ├── jackson            JACKSON
        │     │                   └── swagger            SWAGGER2
        │     └── resources
        │               ├── dtd                          XML自定义Hydro模板语句
        │               └── mapping                      SQL语句模板
        │
        └─ hydro-test-server		(RPC远程数据支撑) 
```

## HyDRO 1.x 类图
![](https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_class.jpg?x-oss-process=image/auto-orient,1/resize,p_30/quality,q_90)

下载地址：[https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_class.svg]()

## HyDRO 1.x 时序图

运行时序图：

![](https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_urml_run.jpg?x-oss-process=image/auto-orient,1/resize,p_30/quality,q_90)

下载地址：[https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_urml_run.svg]()

XML加载时序图：

![](https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_xml.jpg?x-oss-process=image/auto-orient,1/resize,p_30/quality,q_90)

下载地址：[https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_xml.svg]()

DAO加载时序图：

![](https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_dao.jpg?x-oss-process=image/auto-orient,1/resize,p_50/quality,q_90)

下载地址：[https://greesoft.oss-cn-shenzhen.aliyuncs.com/hydro/img/hydro_uml_dao.svg]()

License
-------
[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt)
