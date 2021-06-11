SpringBoot+Redis+Consul:实现学生成绩管理系统 分布式+邮箱功能
# 简介
<center>
	<img src ="https://img-blog.csdnimg.cn/20210421022916813.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70" width="800"/>
</center>
<br/>
<center>
	<img src="https://img-blog.csdnimg.cn/20210612012224893.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>
<br/>
<br/>
<center>
	<img src="https://img-blog.csdnimg.cn/20210514005409827.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>
<br/>
<br/>
<center>
	<img src="https://img-blog.csdnimg.cn/20210612012330209.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>



<hr/>
<font face="楷体" size="+1" color="#000033">

<font face="楷体" size="+1" color="#000033">本文基于**SpringBoot2+Redis+Consul(+MyBatisPlus+Thymeleaf+Echarts)** 实现一个分布式的学生成绩管理系统。它在上一[SpringBoot+MyBatisPlus+Redis+Thymeleaf+Echarts版本]((https://blog.csdn.net/qq_36937684/article/details/115924220))基础上吸收分布式架构思想，将项目拆分为多个模块，使整个项目易于扩展和维护。


# 任务
<center><img src="https://img-blog.csdnimg.cn/20200923171232277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center"/></center>
<br/>

# 相关工作

 - [MySQL+java:   实现学生成绩管理系统（1.0版本）](https://blog.csdn.net/qq_36937684/article/details/108757156)
- [整合Spring+Mybatis    学生成绩管理系统（完整代码）](https://blog.csdn.net/qq_36937684/article/details/113622364)
- [JavaWeb+MySQL实现学生成绩管理系统（1.0版本完整代码）](https://blog.csdn.net/qq_36937684/article/details/114846331?spm=1001.2014.3001.5501)   
- [SSM 实现学生成绩管理系统（完整代码）](https://blog.csdn.net/qq_36937684/article/details/115924220)
- [SpringBoot+MyBatisPlus+Redis+Thymeleaf+Echarts: 实现学生成绩管理系统](https://blog.csdn.net/qq_36937684/article/details/116773611)

<font face="楷体" size="+1" color="#000033">本项目基于以上[项目](https://github.com/AsajuHuishi/StudentScoreManagementSystemSpringBoot)进行改进。主要内容有：
- 应用SpringCloud H+SpringBoot2 目前主流的开发框架
- 项目原有部分拆分为公共模块、用户模块和学生模块三个模块部署
- 使用自带图形界面的Consul作为服务注册中心
- 使用Redis实现不同端口的Session共享
- 增加邮箱模块，实现向指定邮箱发送学生成绩统计信息
- 增加未登录或登录过期页面


<b>更新日志（已经在github上更新）</b>
- 暂无
<hr/>

# 项目结构
<font face="楷体" size="+1" color="#000033">这是一个maven工程，具有4个子模块，都是maven项目。
<center>
	<img src="https://img-blog.csdnimg.cn/20210612014725154.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>
<hr/>

# 注册中心
<font face="楷体" size="+1" color="#000033">使用Consul作为注册中心管理不同服务的状态
```xml
        <!--Consul注册中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
            <version>3.0.3</version>
        </dependency>
```

<center>
	<img src="https://img-blog.csdnimg.cn/20210612015630225.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70" width="800"/>
</center>
<hr/>

# 数据库

<font face="楷体" size="+1" color="#000033">使用MySQL实现，和上一版本一致。

```sql
USE student_score_ssm;

CREATE TABLE student_score(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NO VARCHAR(10) UNIQUE NOT NULL,
	NAME VARCHAR(20) NOT NULL,
	score FLOAT(20),
	class_name INT
);


CREATE TABLE USER(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) UNIQUE,
	PASSWORD VARCHAR(20) NOT NULL,
	email VARCHAR(20)
);

```
<hr/>

# 邮箱功能

<font face="楷体" size="+1" color="#000033">使用javax.mail实现发送信息给指定邮箱功能

```xml
        <!--邮箱-->
        <dependency>
            <groupId>javax.mail</groupId>
            <artifactId>mail</artifactId>
            <version>1.5.0-b01</version>
        </dependency>
```
<font face="楷体" size="+1" color="#000033">邮箱模块自测：
<center>
	<img src="https://img-blog.csdnimg.cn/20210612015714288.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70" width="800"/>
</center>

<hr/>

# Session共享

```xml
        <!--redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
```

<font face="楷体" size="+1" color="#000033">Redis实现Session共享：实现用户模块的Session共享到其它模块，避免公共模块的拦截器拦截不同模块之间的正常调用.
<font face="楷体" size="+1" color="#000033">自测：两个模块分别为8080和8081端口，使用8081可以访问8080端口的session.

<center>
	<img src ="https://img-blog.csdnimg.cn/20210611075625868.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>

<font face="楷体" size="+1" color="#000033">参考：[https://codeload.github.com/2010yhh/springBoot-demos/zip/refs/heads/master](https://codeload.github.com/2010yhh/springBoot-demos/zip/refs/heads/master)
<hr/>

## 增加未登录界面

<center>
	<img src ="https://img-blog.csdnimg.cn/20210611075625934.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>
<hr/>

 其他页面见[SpringBoot+MyBatisPlus+Redis+Thymeleaf+Echarts版本]((https://blog.csdn.net/qq_36937684/article/details/115924220))