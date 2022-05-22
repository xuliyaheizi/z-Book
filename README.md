# zBook

### 项目地址：http://zhulinz.top 		
### 开源地址：https://gitee.com/xuliyaheizi/z-book.git 

### 项目介绍：一个简易的前后端分离网上书城项目，用户可浏览图书，选购图书和利用支付宝进行支付，管理员在后台管理 系统上对图书和订单进行管理。 

#### 项目难点： 

- 为解决跨域身份验证问题，项目整合shiro+jwt来防止未授权用户通过url地址访问页面。 
- 考虑做集群和负载均衡，利用redis保存用户登录信息和购物车信息来实现会话共享。 
- 为实现对多个数据库配置的管理，将数据库配置信息保存在zookeeper的节点中，实现可在项目上线期间更换数据库。 
- 整合swagger+knife4j生成项目接口文档。 
- 调用第三方接口发送验证码，支持用户使用支付宝进行购物车支付。

### 技术栈：
- Springboot
- Zookeeper
- Shiro+Jwt
- Mybatis-plus
- Vue2
- Element UI
- Docker
- Swagger+Knife4j 

