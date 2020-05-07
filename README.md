# Lovecanfly

命名来自于随手放的一首歌～

## 前言

这是自学编程以来我写的第一个项目，参考了很多项目和博客，也学到了很多。

现在也发出来给提供一个参考吧～

（感受：果然学编程还得靠多写hh

## 项目介绍

Lovecanfly 是一个前后端分离的多用户博客系统，采用 SpringBoot+Vue 开发。

演示地址：http://lovecanfly.zhouruikang.cn/

> 用户名：zrk（管理员）、xx（普通用户）、 nn（普通用户）
>
> 密码：123456

**注：上述项目演示只开放查询和部分post权限，其他权限都不具备。完整权限需自己本地或服务器部署。**

### 运行效果图

- 广场页

![](http://img.zhouruikang.cn/2020/0409/lovecanfly02.png)

- 登陆页

![](http://img.zhouruikang.cn/2020/0409/lovecanfly03.png)

- 管理页

![](http://img.zhouruikang.cn/2020/0409/lovecanfly01rrr.png )

### 部署

`lovecanfly`为Springboot后端，`vuelovecanfly`为Vue前端

#### 本地部署

- 前端代码已打包到后端里，如没有参考前端的需求，执行1-4步即可

1. clone 项目到本地，IDE工具（我用的IDEA）打开`lovecanfly`后端项目
2. 提前在本地 Mysql 中创建一个空的数据库 lovecanfly，在根目录下找到`lovecanfly.sql`，在该新建数据库中执行。同时修改项目resources 目录下的 application.properties 中关于数据的配置（如密码等）
3. Maven仓库顺利引入后，启动 LovecanflyApplication
4. 访问：http://localhost:8081

- 如需参考或修改前端

5. IDE工具（我用的WebStorm）打开 `vuelovecanfly` 前端项目，根目录终端命令行依次输入 `npm install`安装依赖、`npm run serve` 启动项目
6. 访问：http://localhost:8080

#### 远程服务端部署

带前端打包后的后端项目可直接jar包或docker远程部署，这里不过多赘述。

以下是记录我前后端分离nginx静态部署的全过程，提供一个参考～

1. [Vue+Springboot项目部署（一）环境准备](http://www.zhouruikang.cn/2020/0325/vue-springboot-deploy01)

2. [Vue+Springboot项目部署（二）启动](http://www.zhouruikang.cn/2020/0326/vue-springboot-deploy02)

### 技术选型

#### 后端

- SpringBoot  :  [容器+MVC框架](https://spring.io/projects/spring-boot)
- SpringSecurity : [认证和授权框架](https://spring.io/projects/spring-security)
- MyBatis : [ORM框架](http://www.mybatis.org/mybatis-3/zh/index.html)
- Easy Code : [基于IDEA开发的代码生成插件](https://gitee.com/makejava/EasyCode)
- PageHelper : [Mybatis通用分页插件](https://github.com/pagehelper/Mybatis-PageHelper)
- Hutool : [Java工具类库](https://github.com/looly/hutool)
- Docker : [应用容器引擎](https://www.docker.com/)

#### 前端

- Vue四件套
    - Vue-cli4 : [Vue.js 开发的标准工具](https://cli.vuejs.org/)
    - Vue-router : [路由框架](https://router.vuejs.org/)
    - Vuex : [全局状态管理框架](https://vuex.vuejs.org/)
    - Axios : [前端HTTP框架](https://github.com/axios/axios)
- Element : [UI库](https://element.eleme.io/)
- v-charts : [基于Echarts的图表框架](https://v-charts.js.org/)
- mavon-editor : [基于vue的markdown编辑器](https://github.com/hinesboy/mavonEditor)

## 主要参考

- [vhr](https://github.com/lenve/vhr)

- [vBlog](https://github.com/lenve/VBlog)

- [vue-manage-system](https://github.com/lin-xin/vue-manage-system)

谢谢在探索未知时给我帮助的项目和博客～我也希望日后自己也能在记录总结自身经验的同时，将这份共享精神传递下去。

Good Luck ~

