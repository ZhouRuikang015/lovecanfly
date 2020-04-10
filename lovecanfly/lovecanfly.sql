/*
 Navicat Premium Data Transfer

 Source Server         : MacMysql
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : lovecanfly

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 11/04/2020 01:33:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章表主键id',
  `title` varchar(64) NOT NULL COMMENT '文章标题',
  `content` mediumtext NOT NULL COMMENT '文章内容',
  `html` mediumtext NOT NULL COMMENT '文章markdown后html',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `category_id` int(11) NOT NULL COMMENT '文章所属类别的id',
  `article_tags` varchar(64) NOT NULL COMMENT '文章标签，多个以逗号分隔',
  `summary` varchar(200) NOT NULL COMMENT '文章封面资源地址',
  `views` bigint(20) NOT NULL DEFAULT '0' COMMENT '文章阅读量',
  `enable_comment` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0-不允许评论 1-允许评论',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0-删除 1-发布 2-草稿',
  PRIMARY KEY (`article_id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `article_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `article_fk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
BEGIN;
INSERT INTO `article` VALUES (26, 'Vue+Springboot项目部署（一）环境准备', '项目前后端分别开发测试对调完成后，一方面Vue前端项目编译打包，剩下一堆 js、css 以及 html文件等静态资源文件，另一方面Springboot后端项目构建jar包。\n\n采用 Nginx + Server 的方式部署～\n\n# 环境准备\n\n服务器选用的是阿里云ECS的Ubuntu 18.04.3，与CentOS 的操作基本类似，供参考\n\n常见的软件安装有以下四种方式：源码、二进制（rpm）、yum、脚本安装。\n\n- **二进制（rpm）安装** ：只需少量的命令，如 **apt-get install nginx**   但无法解决软件包的依赖关系。\n- **yum安装**：自动解决软件依赖性，虽操作更简单方便，但同上无法干预，不能按需安装。\n\n- **源码安装**：可以设定参数，按照需求，进行安装，且可选择安装的版本，灵活性较大。\n\n- **脚本安装**：即把复杂的软件包安装过程写成了程序脚本。讲解不考虑。\n\n\n\n## Nginx安装\n\n考虑到后续在nginx引入gzip模块，使用源码安装！\n\n1. 安装编译环境gcc g++的依赖库 (阿里云ECS自带，可省，不过试了也无妨～)\n\n```shell\nsudo apt-get install build-essential\nsudo apt-get install libtool\n```\n\n2. 安装nginx的依赖库 pcre zlib ssl\n\n```shell\nsudo apt-get install libpcre3 libpcre3-dev zlib1g-dev openssl\n```\n\n3. 安装nginx\n\n```shell\nwget http://nginx.org/download/nginx-1.17.0.tar.gz\ntar -zxvf nginx-1.17.0.tar.gz\n```\n\ncd到解压目录，开始编译安装。引入gzip模块，**nginx静态压缩**的条件。\n\n```shell\n./configure --with-http_gzip_static_module\nmake\nmake install\n```\n\n\n\n> 安装目录：/usr/local/nginx\n\n<img src=\" http://img.zhouruikang.cn/2020/0325/deploy1-1.png\" style=\"zoom:50%;\" />\n\n### 常用命令\n\n```shell\ncd sbin/\n./nginx #启动\n\nvim conf/nginx.conf #修改配置文件\n./nginx -s reload #重新加载配置文件启动\n```\n\n\n\n\n\n## Docker安装\n\n二进制（rpm）安装即可，具体可[参照官网](https://docs.docker.com/install/linux/docker-ce/ubuntu/)\n\n```shell\nsudo apt-get update\n\n# 允许apt通过HTTPS使用仓库\nsudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common\n\n# 添加Docker官方GPG key\ncurl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -\n\n# 设置Docker稳定版仓库\nsudo add-apt-repository \"deb [arch=arm64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable\"\n\n# 安装\nsudo apt-get install docker-ce docker-ce-cli containerd.io\n```\n\ndocker --version 查到版本信号即安装成功！\n\n### 常用命令\n\n```shell\n# 启动/停止/重启docker\nsudo service docker start/stop/restart\n\n# 列出镜像\ndocker images\n\n# 拉取镜像\ndocker image pull 容器名:Tag\n\n# 删除镜像\ndocker rmi 镜像名\n\n# 创建容器\ndocker run [选项参数] 镜像名 \n\n# 正在运行的容器\ndocker ps\n\n# 全部容器\ndocker ps -a\n\n# 停止/启动的容器\ndocker stop/start 容器名或容器id\n\n# 删除容器\ndocker rm 容器名或容器id\n```\n\n\n\n\n\n\n\n\n\n', '<p>项目前后端分别开发测试对调完成后，一方面Vue前端项目编译打包，剩下一堆 js、css 以及 html文件等静态资源文件，另一方面Springboot后端项目构建jar包。</p>\n<p>采用 Nginx + Server 的方式部署～</p>\n<h1><a id=\"_4\"></a>环境准备</h1>\n<p>服务器选用的是阿里云ECS的Ubuntu 18.04.3，与CentOS 的操作基本类似，供参考</p>\n<p>常见的软件安装有以下四种方式：源码、二进制（rpm）、yum、脚本安装。</p>\n<ul>\n<li>\n<p><strong>二进制（rpm）安装</strong> ：只需少量的命令，如 <strong>apt-get install nginx</strong>   但无法解决软件包的依赖关系。</p>\n</li>\n<li>\n<p><strong>yum安装</strong>：自动解决软件依赖性，虽操作更简单方便，但同上无法干预，不能按需安装。</p>\n</li>\n<li>\n<p><strong>源码安装</strong>：可以设定参数，按照需求，进行安装，且可选择安装的版本，灵活性较大。</p>\n</li>\n<li>\n<p><strong>脚本安装</strong>：即把复杂的软件包安装过程写成了程序脚本。讲解不考虑。</p>\n</li>\n</ul>\n<h2><a id=\"Nginx_19\"></a>Nginx安装</h2>\n<p>考虑到后续在nginx引入gzip模块，使用源码安装！</p>\n<ol>\n<li>安装编译环境gcc g++的依赖库 (阿里云ECS自带，可省，不过试了也无妨～)</li>\n</ol>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">sudo apt-get install build-essential\nsudo apt-get install libtool\n</code></div></pre>\n<ol start=\"2\">\n<li>安装nginx的依赖库 pcre zlib ssl</li>\n</ol>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">sudo apt-get install libpcre3 libpcre3-dev zlib1g-dev openssl\n</code></div></pre>\n<ol start=\"3\">\n<li>安装nginx</li>\n</ol>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">wget http://nginx.org/download/nginx-1.17.0.tar.gz\ntar -zxvf nginx-1.17.0.tar.gz\n</code></div></pre>\n<p>cd到解压目录，开始编译安装。引入gzip模块，<strong>nginx静态压缩</strong>的条件。</p>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">./configure --with-http_gzip_static_module\nmake\nmake install\n</code></div></pre>\n<blockquote>\n<p>安装目录：/usr/local/nginx</p>\n</blockquote>\n<img src=\" http://img.zhouruikang.cn/2020/0325/deploy1-1.png\" style=\"zoom:50%;\" />\n<h3><a id=\"_57\"></a>常用命令</h3>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">cd sbin/\n./nginx #启动\n\nvim conf/nginx.conf #修改配置文件\n./nginx -s reload #重新加载配置文件启动\n</code></div></pre>\n<h2><a id=\"Docker_71\"></a>Docker安装</h2>\n<p>二进制（rpm）安装即可，具体可<a href=\"https://docs.docker.com/install/linux/docker-ce/ubuntu/\" target=\"_blank\">参照官网</a></p>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">sudo apt-get update\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 允许apt通过HTTPS使用仓库</span>\nsudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 添加Docker官方GPG key</span>\ncurl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 设置Docker稳定版仓库</span>\nsudo add-apt-repository \"deb [arch=arm64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable\"\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 安装</span>\nsudo apt-get install docker-ce docker-ce-cli containerd.io\n</code></div></pre>\n<p>docker --version 查到版本信号即安装成功！</p>\n<h3><a id=\"_93\"></a>常用命令</h3>\n<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">#</span><span class=\"bash\"> 启动/停止/重启docker</span>\nsudo service docker start/stop/restart\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 列出镜像</span>\ndocker images\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 拉取镜像</span>\ndocker image pull 容器名:Tag\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 删除镜像</span>\ndocker rmi 镜像名\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 创建容器</span>\ndocker run [选项参数] 镜像名 \n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 正在运行的容器</span>\ndocker ps\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 全部容器</span>\ndocker ps -a\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 停止/启动的容器</span>\ndocker stop/start 容器名或容器id\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 删除容器</span>\ndocker rm 容器名或容器id\n</code></div></pre>\n', 13, 41, 'vue,springboot,deploy', '项目前后端分别开发测试对调完成后，一方面Vue前端项目编译打包，剩下一堆 js、css 以及 html文件等静态资源文件，另一方面Springboot后端项目构建jar包。\n\n采用 Nginx + Server 的方式部署～', 10, 1, '2020-04-09 20:30:03', '2020-04-09 20:31:42', 1);
INSERT INTO `article` VALUES (27, 'Vue+Springboot项目部署（二）启动', '以我的Vue+Springboot项目lovecanfly为例说明。\n\n\n\n## 1.后端\n\n后端采用docker部署。项目使用了mysql数据库，除了springboot项目本身用docker部署，为方便也使用docker的mysql容器。\n\n\n\n**容器通信**：多个容器之间需要通信，以往使用的--link方法在互连的容器数量较多时，复杂度会显著增加（官网已不建议）。这里推荐使用自定义bridge网络通信。\n\n\n\n>bridge是docker默认的网络驱动模型。\n>\n>默认的bridge上的容器只能通过子网IP互连，无法通过DNS解析名称或别名。但docker无法保证容器重启后的子网IP地址不变，所以更好的方式是通过别名进行互联。\n>\n>**自定义bridge**在网络中加入了DNS服务器，将**容器名**与IP地址进行匹配，解决了该问题，操作简便。\n\n\n\n### 1.1 创建自定义bridge网络\n\n```shell\ndocker network create lovecanfly-net\n```\n\n\n\n### 1.2 创建并运行mysql容器\n\n```shell\n# 拉取 mysql 5.7镜像\ndocker pull mysql:5.7\n\n# 创建mysql容器\ndocker run -d -p 3306:3306 --network lovecanfly-net --name zrkmysql -e MYSQL_ROOT_PASSWORD=XXXXXXX  mysql:5.7\n```\n\n- -d：容器后台运行\n\n- -p：端口映射，此处映射 主机3306端口 到 容器的3306端口\n\n- --network：指定bridge网络\n\n- –name：容器名，此处命名为zrkmysql。后续web则可通过容器名zrkmysql连接mysql\n\n- -e：配置信息，此处配置mysql的root用户的登陆密码\n\n\n\n### 1.3 创建并运行springboot容器\n\n- **在application.properties里更改数据库连接url**\n\n更改连接的ip名为之前mysql设置好的容器名。\n\n![]( http://img.zhouruikang.cn/2020/0326/deploy2-1.png)\n\n\n\n接下来，一种比较方便的做法是直接一键打包部署，可以参考松哥的这篇文章：[一键部署 Spring Boot 到远程 Docker 容器](https://mp.weixin.qq.com/s/vSCQLvQBYMYoPhdlO2v3XA)。这种方法是在IDEA安装docker插件，更改远程连接服务器上的docker配置，使IDEA可远程连接服务器上的docker，一键即可在本地打jar包同时构建镜像。\n\n\n\n但这种做法在更改服务器上docker配置将无密码状态暴露端口给外界，方便自己也方便了他人，有明显的安全风险。我第一次也是这么做的，结果第二天阿里云上的服务器就被攻击安装并运行了挖矿容器～所以不太推荐哈～\n\n\n\n- **打jar包**\n\n选择如图或在项目跟路径输入mvn package打jar包，成功后会在target目录生成jar包\n\n![]( http://img.zhouruikang.cn/2020/0326/deploy2-2.png)\n\n\n\n- **编写Dockerfile文件**\n\n新建一个`Dockerfile`文件，编辑内容如下：\n\n```dockerfile\nFROM java:8\nVOLUME /tmp\nADD target/lovecanfly-0.0.1-SNAPSHOT.jar app.jar\nRUN bash -c \'touch /app.jar\'\nENTRYPOINT [\"java\",\"-jar\",\"/app.jar\"]\n```\n\n简单说明：\n\n基础镜像使用java，VOLUME 指定了临时文件目录为/tmp，将jar包添加到容器中并更名为app.jar\n\n按命令行 `java -jar /app.jar` 运行jar包。\n\n\n\n- **制作镜像及运行容器**\n\n把 `lovecanfly-0.0.1-SNAPSHOT.jar app.jar` 和 `Dockerfile`上传至服务器**同一目录**。\n\n进入该目录，\n\n```shell\ndocker build -t lovecanfly .   # 注意最后的. 不可省, -t 为指定镜像名\n```\n\n生成并运行容器，\n\n```shell\ndocker run -d -p 8081:8081 --network lovecanfly-net --name lovecanfly lovecanfly\n```\n\n\n\n所有步骤完成，输入 `docker ps`\n\n![]( http://img.zhouruikang.cn/2020/0326/deploy3-1.png)\n\n容器成功运行即可～\n\n\n\n\n\n## 2.前端\n\n前端的操作简单的多，但考虑到减少项目运行时的首屏加载时间，提高用户体验。\n\n采用nginx静态压缩，提前把静态文件压缩成 .gz 格式。\n\n（具体的压缩方式可参考： [首页加载速度提高了5倍](https://mp.weixin.qq.com/s/rzlALs0kW2PDzQ8OSPQMlA)\n\n\n\n前端安装插件\n\n```vue\nnpm install compression-webpack-plugin -D\n```\n\n我使用的是vue-cli 4，在项目根路径下的`vue.config.js`\n\n```javascript\nconst CompressionPlugin = require(\"compression-webpack-plugin\");\n\nmodule.exports = {  \n    configureWebpack: config => {\n        if (process.env.NODE_ENV === \'production\') {\n            return {\n                plugins: [\n                    new CompressionPlugin({\n                        test: /\\.js$|\\.html$|\\.css/,\n                        threshold: 1024,\n                        deleteOriginalAssets: false\n                    })\n                ]\n            }\n        }\n    }\n}\n```\n\n然后 build，在项目dist文件夹中生成带.gz格式静态文件即可。\n\n\n\n## 3.Nginx配置\n\n之前在安装nginx时已引入gzip静态模块，`vim /usr/local/nginx/conf/nginx.conf` 配置nginx\n\n\n\n```\ngzip_static  on; # 开启静态gzip压缩\n\nupstream lovecanfly{ # 上游server名称，与 proxy_pass http://lovecanfly 对应\n    server 127.0.0.1:8081 weight=2;\n}\n\nserver {\n	listen       80; #访问端口\n	server_name  lovecanfly.zhouruikang.cn; # 访问域名\n	\n	# 访问项目url: lovecanfly.zhouruikang.cn/index.html,\n	# 配置 / 直接转发 /index.html，即访问lovecanfly.zhouruikang.cn即可\n	rewrite ^/$ /index.html redirect; \n	\n	\n  location ~ .*\\.(js|css|ico|png|jpg|eot|svg|ttf|woff|html|txt|pdf|) {\n  	root /usr/local/nginx/html/lovecanfly; #所有静态文件直接读取硬盘\n  	index index.html index.htm;\n  	expires 7d; #缓存7天\n  }\n	\n	\n	location /{\n		proxy_pass http://lovecanfly;  # 访问api转发上游server\n		tcp_nodelay     on;\n		proxy_set_header Host            $host;\n		proxy_set_header X-Real-IP       $remote_addr;\n		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n	}\n\n}\n```\n\n保存后，\n\n```shell\n/usr/local/nginx/sbin/nginx -s reload\n```\n\n\n\n## 4.启动项目\n\n之前前端dist目录下的静态文件，上传至nginx刚配置好的 `/usr/local/nginx/html/lovecanfly` 路径中。访问刚才设置好的域名`server_name  lovecanfly.zhouruikang.cn; `即可！\n\n前提是，要先将域名zhouruikang.cn与服务器IP对应哦～太简单就省啦。', '<p>以我的Vue+Springboot项目lovecanfly为例说明。</p>\n<h2><a id=\"1_4\"></a>1.后端</h2>\n<p>后端采用docker部署。项目使用了mysql数据库，除了springboot项目本身用docker部署，为方便也使用docker的mysql容器。</p>\n<p><strong>容器通信</strong>：多个容器之间需要通信，以往使用的–link方法在互连的容器数量较多时，复杂度会显著增加（官网已不建议）。这里推荐使用自定义bridge网络通信。</p>\n<blockquote>\n<p>bridge是docker默认的网络驱动模型。</p>\n<p>默认的bridge上的容器只能通过子网IP互连，无法通过DNS解析名称或别名。但docker无法保证容器重启后的子网IP地址不变，所以更好的方式是通过别名进行互联。</p>\n<p><strong>自定义bridge</strong>在网络中加入了DNS服务器，将<strong>容器名</strong>与IP地址进行匹配，解决了该问题，操作简便。</p>\n</blockquote>\n<h3><a id=\"11_bridge_22\"></a>1.1 创建自定义bridge网络</h3>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">docker network create lovecanfly-net\n</code></div></pre>\n<h3><a id=\"12_mysql_30\"></a>1.2 创建并运行mysql容器</h3>\n<pre><div class=\"hljs\"><code class=\"lang-shell\"><span class=\"hljs-meta\">#</span><span class=\"bash\"> 拉取 mysql 5.7镜像</span>\ndocker pull mysql:5.7\n<span class=\"hljs-meta\">\n#</span><span class=\"bash\"> 创建mysql容器</span>\ndocker run -d -p 3306:3306 --network lovecanfly-net --name zrkmysql -e MYSQL_ROOT_PASSWORD=XXXXXXX  mysql:5.7\n</code></div></pre>\n<ul>\n<li>\n<p>-d：容器后台运行</p>\n</li>\n<li>\n<p>-p：端口映射，此处映射 主机3306端口 到 容器的3306端口</p>\n</li>\n<li>\n<p>–network：指定bridge网络</p>\n</li>\n<li>\n<p>–name：容器名，此处命名为zrkmysql。后续web则可通过容器名zrkmysql连接mysql</p>\n</li>\n<li>\n<p>-e：配置信息，此处配置mysql的root用户的登陆密码</p>\n</li>\n</ul>\n<h3><a id=\"13_springboot_52\"></a>1.3 创建并运行springboot容器</h3>\n<ul>\n<li><strong>在application.properties里更改数据库连接url</strong></li>\n</ul>\n<p>更改连接的ip名为之前mysql设置好的容器名。</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/deploy2-1.png\" alt=\"\" /></p>\n<p>接下来，一种比较方便的做法是直接一键打包部署，可以参考松哥的这篇文章：<a href=\"https://mp.weixin.qq.com/s/vSCQLvQBYMYoPhdlO2v3XA\" target=\"_blank\">一键部署 Spring Boot 到远程 Docker 容器</a>。这种方法是在IDEA安装docker插件，更改远程连接服务器上的docker配置，使IDEA可远程连接服务器上的docker，一键即可在本地打jar包同时构建镜像。</p>\n<p>但这种做法在更改服务器上docker配置将无密码状态暴露端口给外界，方便自己也方便了他人，有明显的安全风险。我第一次也是这么做的，结果第二天阿里云上的服务器就被攻击安装并运行了挖矿容器～所以不太推荐哈～</p>\n<ul>\n<li><strong>打jar包</strong></li>\n</ul>\n<p>选择如图或在项目跟路径输入mvn package打jar包，成功后会在target目录生成jar包</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/deploy2-2.png\" alt=\"\" /></p>\n<ul>\n<li><strong>编写Dockerfile文件</strong></li>\n</ul>\n<p>新建一个<code>Dockerfile</code>文件，编辑内容如下：</p>\n<pre><div class=\"hljs\"><code class=\"lang-dockerfile\"><span class=\"hljs-keyword\">FROM</span> java:<span class=\"hljs-number\">8</span>\n<span class=\"hljs-keyword\">VOLUME</span><span class=\"bash\"> /tmp\n</span><span class=\"hljs-keyword\">ADD</span><span class=\"bash\"> target/lovecanfly-0.0.1-SNAPSHOT.jar app.jar\n</span><span class=\"hljs-keyword\">RUN</span><span class=\"bash\"> bash -c <span class=\"hljs-string\">\'touch /app.jar\'</span>\n</span><span class=\"hljs-keyword\">ENTRYPOINT</span><span class=\"bash\"> [<span class=\"hljs-string\">\"java\"</span>,<span class=\"hljs-string\">\"-jar\"</span>,<span class=\"hljs-string\">\"/app.jar\"</span>]\n</span></code></div></pre>\n<p>简单说明：</p>\n<p>基础镜像使用java，VOLUME 指定了临时文件目录为/tmp，将jar包添加到容器中并更名为app.jar</p>\n<p>按命令行 <code>java -jar /app.jar</code> 运行jar包。</p>\n<ul>\n<li><strong>制作镜像及运行容器</strong></li>\n</ul>\n<p>把 <code>lovecanfly-0.0.1-SNAPSHOT.jar app.jar</code> 和 <code>Dockerfile</code>上传至服务器<strong>同一目录</strong>。</p>\n<p>进入该目录，</p>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">docker build -t lovecanfly .   # 注意最后的. 不可省, -t 为指定镜像名\n</code></div></pre>\n<p>生成并运行容器，</p>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">docker run -d -p 8081:8081 --network lovecanfly-net --name lovecanfly lovecanfly\n</code></div></pre>\n<p>所有步骤完成，输入 <code>docker ps</code></p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/deploy3-1.png\" alt=\"\" /></p>\n<p>容器成功运行即可～</p>\n<h2><a id=\"2_126\"></a>2.前端</h2>\n<p>前端的操作简单的多，但考虑到减少项目运行时的首屏加载时间，提高用户体验。</p>\n<p>采用nginx静态压缩，提前把静态文件压缩成 .gz 格式。</p>\n<p>（具体的压缩方式可参考： <a href=\"https://mp.weixin.qq.com/s/rzlALs0kW2PDzQ8OSPQMlA\" target=\"_blank\">首页加载速度提高了5倍</a></p>\n<p>前端安装插件</p>\n<pre><code class=\"lang-vue\">npm install compression-webpack-plugin -D\n</code></pre>\n<p>我使用的是vue-cli 4，在项目根路径下的<code>vue.config.js</code></p>\n<pre><div class=\"hljs\"><code class=\"lang-javascript\"><span class=\"hljs-keyword\">const</span> CompressionPlugin = <span class=\"hljs-built_in\">require</span>(<span class=\"hljs-string\">\"compression-webpack-plugin\"</span>);\n\n<span class=\"hljs-built_in\">module</span>.exports = {  \n    <span class=\"hljs-attr\">configureWebpack</span>: <span class=\"hljs-function\"><span class=\"hljs-params\">config</span> =&gt;</span> {\n        <span class=\"hljs-keyword\">if</span> (process.env.NODE_ENV === <span class=\"hljs-string\">\'production\'</span>) {\n            <span class=\"hljs-keyword\">return</span> {\n                <span class=\"hljs-attr\">plugins</span>: [\n                    <span class=\"hljs-keyword\">new</span> CompressionPlugin({\n                        <span class=\"hljs-attr\">test</span>: <span class=\"hljs-regexp\">/\\.js$|\\.html$|\\.css/</span>,\n                        <span class=\"hljs-attr\">threshold</span>: <span class=\"hljs-number\">1024</span>,\n                        <span class=\"hljs-attr\">deleteOriginalAssets</span>: <span class=\"hljs-literal\">false</span>\n                    })\n                ]\n            }\n        }\n    }\n}\n</code></div></pre>\n<p>然后 build，在项目dist文件夹中生成带.gz格式静态文件即可。</p>\n<h2><a id=\"3Nginx_168\"></a>3.Nginx配置</h2>\n<p>之前在安装nginx时已引入gzip静态模块，<code>vim /usr/local/nginx/conf/nginx.conf</code> 配置nginx</p>\n<pre><code class=\"lang-\">gzip_static  on; # 开启静态gzip压缩\n\nupstream lovecanfly{ # 上游server名称，与 proxy_pass http://lovecanfly 对应\n    server 127.0.0.1:8081 weight=2;\n}\n\nserver {\n	listen       80; #访问端口\n	server_name  lovecanfly.zhouruikang.cn; # 访问域名\n	\n	# 访问项目url: lovecanfly.zhouruikang.cn/index.html,\n	# 配置 / 直接转发 /index.html，即访问lovecanfly.zhouruikang.cn即可\n	rewrite ^/$ /index.html redirect; \n	\n	\n  location ~ .*\\.(js|css|ico|png|jpg|eot|svg|ttf|woff|html|txt|pdf|) {\n  	root /usr/local/nginx/html/lovecanfly; #所有静态文件直接读取硬盘\n  	index index.html index.htm;\n  	expires 7d; #缓存7天\n  }\n	\n	\n	location /{\n		proxy_pass http://lovecanfly;  # 访问api转发上游server\n		tcp_nodelay     on;\n		proxy_set_header Host            $host;\n		proxy_set_header X-Real-IP       $remote_addr;\n		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n	}\n\n}\n</code></pre>\n<p>保存后，</p>\n<pre><div class=\"hljs\"><code class=\"lang-shell\">/usr/local/nginx/sbin/nginx -s reload\n</code></div></pre>\n<h2><a id=\"4_216\"></a>4.启动项目</h2>\n<p>之前前端dist目录下的静态文件，上传至nginx刚配置好的 <code>/usr/local/nginx/html/lovecanfly</code> 路径中。访问刚才设置好的域名<code>server_name lovecanfly.zhouruikang.cn;</code>即可！</p>\n<p>前提是，要先将域名zhouruikang.cn与服务器IP对应哦～太简单就省啦。</p>\n', 13, 41, 'vue,springboot,deploy', '以我的Vue+Springboot项目lovecanfly为例说明。', 10, 1, '2020-04-09 20:41:49', '2020-04-09 20:41:49', 1);
INSERT INTO `article` VALUES (28, 'java开发桌面应用（一）JavaFx初体验Jfoenix UI+Scene Builder', '之前哈工大的同学找到我，他的老师接的海绵城市系统评价相关的项目在最后的提交里需要有一个桌面应用。尽管对桌面开发没多了解，但想到咱java无所不能，加上顺手谷歌一下看到javafx能做。既然能做那就不是问题，帮他这个忙啦～\n\n\n</br>\n\n目前主流的桌面开发，跨平台用qt，不跨平台用winform或wpf。\n\njavafx虽然不太主流，但是也能做啦～\n\n常用的开发套路：javafx 框架 + Jfoenix UI控件 + Scene Builder可视化工具\n\n下面举个简单的小栗子来初步感受吧～ （ [源码记录](https://github.com/ZhouRuikang015/javafx-havefun/tree/master/javafx01-helloworld)\n\n\n\n**环境准备：**\n\n- [IntelliJ IDEA](https://www.jetbrains.com/idea/)\n- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/overview/index.html)\n- [Scene Builder 8.5.0](https://gluonhq.com/products/scene-builder/)\n\n\n\n# 1.新建一个javafx项目\n\n使用IDEA自带的新建项目菜单即可\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-1.png)\n\n目录结构如下，直接点击Main运行，效果如右图\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-2r.png)\n\n接下来可以大致感受javafx框架的想法，项目从Main.java进入，加载view层sample.fxml，窗口设置title和scene参数后展出。sample.fxml与controller层Controller.java进行行为绑定。与MVC类似，理解起来比较容易，接下来就开始玩吧～\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-3.png)\n\n\n\n# 2.添加Maven\n\n为方便引入三方库管理项目，引入Maven。\n\n- 首先将设置里Maven配置修改为自己提前安装好的maven地址。\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-4.png)\n\n\n\n- 引入maven\n\n右键项目名选择 `Add Framework Support...` ，选择Maven确认，并修改一下目录结构。\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-5rr.png)\n\n\n\n**注：**将sample.fxml移动到resource目录下需修改Main.java中的加载路径`FXMLLoader.load(getClass().getResource(\"/sample.fxml\"))`\n\n\n\n# 3.引入Jfoenix UI库\n\n[Jfoenix项目地址及Components演示](https://github.com/jfoenixadmin/JFoenix)\n\n- 项目引入：`pom.xml` 文件里添加\n\n```xml\n<dependencies>\n	<dependency>\n		<groupId>com.jfoenix</groupId>\n		<artifactId>jfoenix</artifactId>\n		<version>8.0.8</version>\n	</dependency>\n</dependencies>\n```\n\n\n\n- scene builder工具引入\n\n图中第二步从Maven仓库找到刚下载好的 `jfoenix-8.0.8.jar`，第三步全部或按需引入。\n\n完成后在Library的Custom栏即引入的Jfoenix组件。\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-6r.png)\n\n\n\n# 4.使用scene builder编写界面\n\n通过scene builder工具可视化编写`.fxml`视图文件。\n\n- 打开设置，建立IDEA与scene builder工具关联\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-7.png)\n\n这样选中`sample.fxml`文件右键点击 `Open In SceneBuilder` 即可直接打开scene builder可视化编辑该文件。\n\n\n\n- scene builder编辑演示\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-8.png)\n\n简单说明，\n\n1. 图中引入三个组件，AnchorPane做背景板，一个按钮JFXButton，一个文本栏JFXTextField\n\n2. 右侧code栏处，JFXButton的Id名设置为button，响应方法名为changeInput。JFXTextField的Id名为textField，无响应方法。\n\n3. 左侧Controller指定`sample.fxml`的控制器`Controller.java`，方便自动生成Controller代码\n\n\n\n- 使用自动生成的Controller代码\n\n打开scene builder工具左上角 `View / Show Simple Controller Skeleton`，复制粘贴至项目中`Controller.java`，然后再编辑省时省力～\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1-9.png)\n\n\n\n- 更新项目中sample.fxml\n\n工具里 点保存 ！！。\n\n\n\n- 预览效果\n\n在scene builder工具使用中不免想预览界面效果，左上角 `Preview / Show Preview in Window`\n\n\n\n# 5.初体验\n\n- 简单编写Controller.java完成组件的行为响应。\n\n```java\npackage sample;\n\nimport com.jfoenix.controls.JFXButton;\nimport com.jfoenix.controls.JFXTextField;\nimport javafx.event.ActionEvent;\nimport javafx.fxml.FXML;\n\npublic class Controller {\n\n    @FXML\n    private JFXButton button;\n\n    @FXML\n    private JFXTextField textField;\n\n    @FXML\n    void changeInput(ActionEvent event) {\n        String text = textField.getText();\n        if (text.equals(\"hello\")){\n            textField.setText(\"world!\");\n        }else{\n            textField.setText(\"\");\n        }\n    }\n\n}\n```\n\n\n\n- 运行效果\n\n![]( http://img.zhouruikang.cn/2020/0326/javafx1.gif)\n\n还挺好玩的～\n\n\n\n', '<p>之前哈工大的同学找到我，他的老师接的海绵城市系统评价相关的项目在最后的提交里需要有一个桌面应用。尽管对桌面开发没多了解，但想到咱java无所不能，加上顺手谷歌一下看到javafx能做。既然能做那就不是问题，帮他这个忙啦～</p>\n</br>\n<p>目前主流的桌面开发，跨平台用qt，不跨平台用winform或wpf。</p>\n<p>javafx虽然不太主流，但是也能做啦～</p>\n<p>常用的开发套路：javafx 框架 + Jfoenix UI控件 + Scene Builder可视化工具</p>\n<p>下面举个简单的小栗子来初步感受吧～ （ <a href=\"https://github.com/ZhouRuikang015/javafx-havefun/tree/master/javafx01-helloworld\" target=\"_blank\">源码记录</a></p>\n<p><strong>环境准备：</strong></p>\n<ul>\n<li><a href=\"https://www.jetbrains.com/idea/\" target=\"_blank\">IntelliJ IDEA</a></li>\n<li><a href=\"http://www.oracle.com/technetwork/java/javase/overview/index.html\" target=\"_blank\">JDK 1.8</a></li>\n<li><a href=\"https://gluonhq.com/products/scene-builder/\" target=\"_blank\">Scene Builder 8.5.0</a></li>\n</ul>\n<h1><a id=\"1javafx_23\"></a>1.新建一个javafx项目</h1>\n<p>使用IDEA自带的新建项目菜单即可</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-1.png\" alt=\"\" /></p>\n<p>目录结构如下，直接点击Main运行，效果如右图</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-2r.png\" alt=\"\" /></p>\n<p>接下来可以大致感受javafx框架的想法，项目从Main.java进入，加载view层sample.fxml，窗口设置title和scene参数后展出。sample.fxml与controller层Controller.java进行行为绑定。与MVC类似，理解起来比较容易，接下来就开始玩吧～</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-3.png\" alt=\"\" /></p>\n<h1><a id=\"2Maven_39\"></a>2.添加Maven</h1>\n<p>为方便引入三方库管理项目，引入Maven。</p>\n<ul>\n<li>首先将设置里Maven配置修改为自己提前安装好的maven地址。</li>\n</ul>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-4.png\" alt=\"\" /></p>\n<ul>\n<li>引入maven</li>\n</ul>\n<p>右键项目名选择 <code>Add Framework Support...</code> ，选择Maven确认，并修改一下目录结构。</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-5rr.png\" alt=\"\" /></p>\n<p>**注：**将sample.fxml移动到resource目录下需修改Main.java中的加载路径<code>FXMLLoader.load(getClass().getResource(&quot;/sample.fxml&quot;))</code></p>\n<h1><a id=\"3Jfoenix_UI_61\"></a>3.引入Jfoenix UI库</h1>\n<p><a href=\"https://github.com/jfoenixadmin/JFoenix\" target=\"_blank\">Jfoenix项目地址及Components演示</a></p>\n<ul>\n<li>项目引入：<code>pom.xml</code> 文件里添加</li>\n</ul>\n<pre><div class=\"hljs\"><code class=\"lang-xml\"><span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">dependencies</span>&gt;</span>\n	<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">dependency</span>&gt;</span>\n		<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">groupId</span>&gt;</span>com.jfoenix<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">groupId</span>&gt;</span>\n		<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">artifactId</span>&gt;</span>jfoenix<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">artifactId</span>&gt;</span>\n		<span class=\"hljs-tag\">&lt;<span class=\"hljs-name\">version</span>&gt;</span>8.0.8<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">version</span>&gt;</span>\n	<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">dependency</span>&gt;</span>\n<span class=\"hljs-tag\">&lt;/<span class=\"hljs-name\">dependencies</span>&gt;</span>\n</code></div></pre>\n<ul>\n<li>scene builder工具引入</li>\n</ul>\n<p>图中第二步从Maven仓库找到刚下载好的 <code>jfoenix-8.0.8.jar</code>，第三步全部或按需引入。</p>\n<p>完成后在Library的Custom栏即引入的Jfoenix组件。</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-6r.png\" alt=\"\" /></p>\n<h1><a id=\"4scene_builder_89\"></a>4.使用scene builder编写界面</h1>\n<p>通过scene builder工具可视化编写<code>.fxml</code>视图文件。</p>\n<ul>\n<li>打开设置，建立IDEA与scene builder工具关联</li>\n</ul>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-7.png\" alt=\"\" /></p>\n<p>这样选中<code>sample.fxml</code>文件右键点击 <code>Open In SceneBuilder</code> 即可直接打开scene builder可视化编辑该文件。</p>\n<ul>\n<li>scene builder编辑演示</li>\n</ul>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-8.png\" alt=\"\" /></p>\n<p>简单说明，</p>\n<ol>\n<li>\n<p>图中引入三个组件，AnchorPane做背景板，一个按钮JFXButton，一个文本栏JFXTextField</p>\n</li>\n<li>\n<p>右侧code栏处，JFXButton的Id名设置为button，响应方法名为changeInput。JFXTextField的Id名为textField，无响应方法。</p>\n</li>\n<li>\n<p>左侧Controller指定<code>sample.fxml</code>的控制器<code>Controller.java</code>，方便自动生成Controller代码</p>\n</li>\n</ol>\n<ul>\n<li>使用自动生成的Controller代码</li>\n</ul>\n<p>打开scene builder工具左上角 <code>View / Show Simple Controller Skeleton</code>，复制粘贴至项目中<code>Controller.java</code>，然后再编辑省时省力～</p>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1-9.png\" alt=\"\" /></p>\n<ul>\n<li>更新项目中sample.fxml</li>\n</ul>\n<p>工具里 点保存 ！！。</p>\n<ul>\n<li>预览效果</li>\n</ul>\n<p>在scene builder工具使用中不免想预览界面效果，左上角 <code>Preview / Show Preview in Window</code></p>\n<h1><a id=\"5_135\"></a>5.初体验</h1>\n<ul>\n<li>简单编写Controller.java完成组件的行为响应。</li>\n</ul>\n<pre><div class=\"hljs\"><code class=\"lang-java\"><span class=\"hljs-keyword\">package</span> sample;\n\n<span class=\"hljs-keyword\">import</span> com.jfoenix.controls.JFXButton;\n<span class=\"hljs-keyword\">import</span> com.jfoenix.controls.JFXTextField;\n<span class=\"hljs-keyword\">import</span> javafx.event.ActionEvent;\n<span class=\"hljs-keyword\">import</span> javafx.fxml.FXML;\n\n<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">Controller</span> </span>{\n\n    <span class=\"hljs-meta\">@FXML</span>\n    <span class=\"hljs-keyword\">private</span> JFXButton button;\n\n    <span class=\"hljs-meta\">@FXML</span>\n    <span class=\"hljs-keyword\">private</span> JFXTextField textField;\n\n    <span class=\"hljs-meta\">@FXML</span>\n    <span class=\"hljs-function\"><span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">changeInput</span><span class=\"hljs-params\">(ActionEvent event)</span> </span>{\n        String text = textField.getText();\n        <span class=\"hljs-keyword\">if</span> (text.equals(<span class=\"hljs-string\">\"hello\"</span>)){\n            textField.setText(<span class=\"hljs-string\">\"world!\"</span>);\n        }<span class=\"hljs-keyword\">else</span>{\n            textField.setText(<span class=\"hljs-string\">\"\"</span>);\n        }\n    }\n\n}\n</code></div></pre>\n<ul>\n<li>运行效果</li>\n</ul>\n<p><img src=\"http://img.zhouruikang.cn/2020/0326/javafx1.gif\" alt=\"\" /></p>\n<p>还挺好玩的～</p>\n', 14, 42, 'javafx,jfoenix,scene builder', '之前哈工大的同学找到我，他的老师接的海绵城市系统评价相关的项目在最后的提交里需要有一个桌面应用。尽管对桌面开发没多了解，但想到咱java无所不能，加上顺手谷歌一下看到javafx能做。既然能做那就不是问题，帮他这个忙啦～', 6, 1, '2020-04-09 20:46:10', '2020-04-09 20:46:10', 1);
COMMIT;

-- ----------------------------
-- Table structure for article_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_relation`;
CREATE TABLE `article_tag_relation` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`relation_id`),
  KEY `article_id` (`article_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `article_tag_relation_fk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE,
  CONSTRAINT `article_tag_relation_fk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_tag_relation
-- ----------------------------
BEGIN;
INSERT INTO `article_tag_relation` VALUES (380, 26, 79);
INSERT INTO `article_tag_relation` VALUES (381, 26, 78);
INSERT INTO `article_tag_relation` VALUES (382, 26, 77);
INSERT INTO `article_tag_relation` VALUES (383, 27, 79);
INSERT INTO `article_tag_relation` VALUES (384, 27, 78);
INSERT INTO `article_tag_relation` VALUES (385, 27, 77);
INSERT INTO `article_tag_relation` VALUES (386, 28, 89);
INSERT INTO `article_tag_relation` VALUES (387, 28, 90);
INSERT INTO `article_tag_relation` VALUES (388, 28, 91);
COMMIT;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `category_name` varchar(64) NOT NULL COMMENT '分类的名称',
  `category_describe` varchar(64) NOT NULL DEFAULT '' COMMENT '分类详情',
  PRIMARY KEY (`category_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `category_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` VALUES (29, 1, 'love', '爱');
INSERT INTO `category` VALUES (41, 13, 'deploy', 'vue+springboot项目部署');
INSERT INTO `category` VALUES (42, 14, 'javafx', 'java桌面开发');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `article_id` int(11) NOT NULL COMMENT '关联的article主键',
  `commentator_name` varchar(64) NOT NULL COMMENT '评论者名称',
  `commentator_email` varchar(64) NOT NULL COMMENT '评论人的邮箱',
  `comment_content` varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_location` varchar(64) NOT NULL DEFAULT '' COMMENT '评论时的ip位置',
  `reply_content` varchar(200) NOT NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否显示评论 0-回收站 1-已发布 2-待审核',
  PRIMARY KEY (`comment_id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `comment_fk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (41, 28, '瑞康', '949872703@qq.com', '来自管理员的评论哈哈哈', '2020-04-09 21:50:51', '[]', 'OK!', '2020-04-09 21:51:59', 1);
INSERT INTO `comment` VALUES (42, 27, '瑞康', '949872703@qq.com', '管理员来啦', '2020-04-09 21:53:07', '[]', '', '2020-04-09 21:53:07', 1);
COMMIT;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '记录login的id,若为0即后台schedule记录',
  `new_comments` int(11) NOT NULL DEFAULT '0' COMMENT '文章数统计',
  `comments_count` int(11) NOT NULL DEFAULT '0' COMMENT '文章数统计',
  `new_views` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论数统计',
  `views_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论数统计',
  `record_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`record_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `record_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
BEGIN;
INSERT INTO `record` VALUES (283, 12, 3, 3, 5, 5, '2020-03-09 00:00:00');
INSERT INTO `record` VALUES (284, 12, 5, 8, 22, 27, '2020-03-10 00:00:00');
INSERT INTO `record` VALUES (285, 12, 10, 18, 33, 60, '2020-03-11 00:00:00');
INSERT INTO `record` VALUES (286, 12, 17, 35, 20, 80, '2020-03-12 00:00:00');
INSERT INTO `record` VALUES (287, 12, 13, 48, 42, 122, '2020-03-13 00:00:00');
INSERT INTO `record` VALUES (288, 12, 22, 70, 30, 152, '2020-03-14 00:00:00');
INSERT INTO `record` VALUES (289, 12, 5, 75, 12, 164, '2020-03-15 00:00:00');
INSERT INTO `record` VALUES (290, 1, 0, 0, 0, 0, '2020-04-09 19:44:03');
INSERT INTO `record` VALUES (301, 13, 0, 0, 0, 0, '2020-04-09 20:26:56');
INSERT INTO `record` VALUES (307, 14, 0, 0, 0, 0, '2020-04-09 20:43:32');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(64) DEFAULT NULL COMMENT 'springboot标准名称',
  `role_name_zh` varchar(64) DEFAULT NULL COMMENT '角色中文名称',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'ROLE_admin', '系统管理员');
INSERT INTO `role` VALUES (2, 'ROLE_user', '普通用户');
INSERT INTO `role` VALUES (3, 'ROLE_test', '测试用户');
COMMIT;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(64) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
BEGIN;
INSERT INTO `tag` VALUES (57, '');
INSERT INTO `tag` VALUES (75, '%E4%BD%A0%E5%A5%BD%2C%E5%86%8D%E8%A7%81');
INSERT INTO `tag` VALUES (10, 'bye');
INSERT INTO `tag` VALUES (21, 'computer');
INSERT INTO `tag` VALUES (79, 'deploy');
INSERT INTO `tag` VALUES (65, 'happy');
INSERT INTO `tag` VALUES (68, 'hdhshs');
INSERT INTO `tag` VALUES (5, 'hello');
INSERT INTO `tag` VALUES (89, 'javafx');
INSERT INTO `tag` VALUES (90, 'jfoenix');
INSERT INTO `tag` VALUES (40, 'love');
INSERT INTO `tag` VALUES (20, 'oneTag');
INSERT INTO `tag` VALUES (91, 'scene builder');
INSERT INTO `tag` VALUES (15, 'Spring');
INSERT INTO `tag` VALUES (78, 'springboot');
INSERT INTO `tag` VALUES (77, 'vue');
INSERT INTO `tag` VALUES (71, '你好');
INSERT INTO `tag` VALUES (72, '再见');
INSERT INTO `tag` VALUES (62, '奥术大');
INSERT INTO `tag` VALUES (76, '春天来啦');
INSERT INTO `tag` VALUES (70, '饿');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `email` varchar(64) NOT NULL COMMENT '用户邮箱',
  `gender` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户性别 0女性 1男性 ',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否锁定 0锁定 1未锁定 2注册待审核',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'zrk', '$2a$10$pfic6Al/x2ew29RfbqwzX.aOqRzHcdw6cGlBvRXE4aL78mE1I5JOW', '瑞康', '949872703@qq.com', 1, '2019-05-01 15:46:15', 1);
INSERT INTO `user` VALUES (6, 'schedule', '$2a$10$pfic6Al/x2ew29RfbqwzX.aOqRzHcdw6cGlBvRXE4aL78mE1I5JOW', '日常记录id', '949872703@qq.com', 1, '2019-01-01 00:00:00', 1);
INSERT INTO `user` VALUES (12, 'systemSchedule', '$2a$10$JTc3PM/FCriUeADIScyjo.lx9QrcSnlsVrwVYTNasuAV1D.HTL5Be', '系统日程记录', '949872703@qq.com', 1, '2020-04-09 19:12:51', 1);
INSERT INTO `user` VALUES (13, 'xx', '$2a$10$38wozA3pdADG7FJ68aWDT.L9YweHLLC0qb8t7B9tg2E.PYZSkSKsW', '小新', 'zhouruikang015@163.com', 1, '2020-04-09 20:26:36', 1);
INSERT INTO `user` VALUES (14, 'nn', '$2a$10$VysuubSKA5UJsmLQ3JF0aeV1IardMsku8tpJiNZWechnIq2Zm/nFe', '妮妮', '949872703@qq.com', 0, '2020-04-09 20:43:06', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL DEFAULT '2' COMMENT '角色id',
  PRIMARY KEY (`relation_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_relation_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_relation_fk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------
BEGIN;
INSERT INTO `user_role_relation` VALUES (2, 1, 1);
INSERT INTO `user_role_relation` VALUES (6, 1, 2);
INSERT INTO `user_role_relation` VALUES (30, 12, 1);
INSERT INTO `user_role_relation` VALUES (32, 14, 2);
INSERT INTO `user_role_relation` VALUES (33, 13, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
