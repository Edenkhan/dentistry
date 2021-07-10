使用 MySQL 创建一个名为 `dentistry` 的数据库，并在该数据库执行 `docs/schemas.sql` 文件创建数据表。

打开命令提示符，进入项目根目录运行下面命令安装全部模块的依赖：

```
mvn install
```

运行后，进入根目录下的 `console` 目录，运行下面命令启动后端管理服务（默认端口为 `8080`）：

```
mvn spring-boot:run
```

在运行后端管理服务后访问路径 `http://localhost:8080/setup`，访问后会创建一个管理员，用户名为 `dentistry`，密码为：`admin`。