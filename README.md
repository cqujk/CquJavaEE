# CquJavaEE

这里存储重庆大学JAVA企业级开发的实验代码，供后来者借鉴参考，以进一步提高

切换不同的分支查看

**抄袭记零分！**

## 第一次实验

## 第二次实验

## 第三次实验

共享Cookie+jwt实现单点登录（sso)

采用redis保存每个系统上用户的会话信息与在线人数；采用MySQL持久化用户与历史登录信息

Todo（Maybe):可以考虑将redis数据与MySQL上的数据作一致性同步（？）

## 第四次实验


网关 80 ： Nginx

前端 3000: Next.js+TypeScript

用户鉴权 8081： spirngSecurity+Jwt+H2DataBase

后端 8080：SpringBoot+MySQL

MySQL数据库性能调优,JSON字段；维护数据一致性

触发器，存储过程用于在部分得分更新时，同步更新总成绩；以及某科总成绩更新后，同步更新学生gpa；

数据生成generator:采用java Faker生成拟真姓名
