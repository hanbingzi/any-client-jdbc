# 项目介绍
## 项目目录
```text
any-client-jdbc
├─src
|  ├─core
|  |  ├─app //项目核心
|  |  ├─common //项目通用
|  |  ├─base //jdbc底层操作
|  |  |   ├─
|  |  |   ├─sq-base
|  |  |   ├─
|  |  ├─plugin-api //与plugins通信model
|  |  └server //后台node启动入口
|  |     ├─Mysql8
|  |     ├─DB2
|  |     ├─KingBase
|  |     ├─Hive
|  |     ├─SparkSql
|  ├─plugins //1.主流驱动不兼容的驱动，例如mysql8的驱动不兼容mysql5，需要通过osgi引用 2.为了减少plugins的jar包大小，plugins减少引入

```
