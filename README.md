# wx-shop



本仓库包含开发全部所需材料，使用这些材料即可快速构建一个全新的开发环境。

## 准备环境

### 容器技术

本项目的开发依赖容器技术，需要开发环境有Docker环境。如您尚未配置Docker环境，请先自行配置Docker环境。Mac OS X和Windows请安装Docker Desktop，Linux系统直接安装Docker CE.以下信息供参考：

- Mac OS X: https://www.docker.com/products/docker-desktop
- Windows: https://www.docker.com/products/docker-desktop
- Ubuntu: https://docs.docker.com/engine/install/ubuntu/
- CentOS: https://docs.docker.com/engine/install/centos/

### 开发平台

本项目使用Java平台开发，严重依赖Java生态环境。请先安装OpenJDK 8：

- OpenJDK 8 for Windows: https://www.oracle.com/java/technologies/downloads/#java8-windows
- OpenJDK 8 for Mac: https://www.oracle.com/java/technologies/downloads/#java8-mac
- OpenJDK 8 for Linux: https://www.oracle.com/java/technologies/downloads/#java8-linux

### 开发工具

为了更好的开发体验以及更高的开发效率，请使用IntelliJ IDEA作为默认开发工具：

- IntelliJ IDEA: https://www.jetbrains.com/idea/

### 依赖服务

本项目所有依赖服务均可通过自动化方式构建。要构建一个全新的环境，请在项目根目录执行如下命令：

```shell
./scripts/setup-dev
```
注意！ 在本地idea中启动。

## 命令参考

### 快速检查代码风格

```shell script
./scripts/check-static
```

### 提交前代码检查

```shell script
./scripts/check-all
```

### 运行主项目

```shell script
./scripts/run-server
```

