# 项目介绍
## 项目目录
```text
any-client-jdbc
├─src
|  ├─core
|  |  ├─app //项目核心
|  |  ├─api //与plugins通信model
|  |  ├─common //与plugins通信model
|  |  └server //后台node启动入口
|  |     ├─Mysql8
|  |     ├─DB2
|  |     ├─KingBase
|  |     ├─Hive
|  |     ├─SparkSql
|  ├─plugins //主流驱动不兼容的驱动，例如mysql8的驱动不兼容mysql5，需要通过osgi引用

```
