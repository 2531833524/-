# -
20计嵌电商管理系统项目源代码
1、项目概述
1.1项目主要内容
实现了简单的电商管理系统，系统分用户，管理员，商家三大块主要逻辑。
用户可以浏览商品、对商品进行模糊查询、添加商品至购物车、查看购物车、将商品移出购物车、下单（会反馈给用户订单号）、查看所有订单、查看未发货订单、查看已收货订单、确认收获。
商家可以发货商品（发货之后商品库存会对应相减，订单状态会变成已发货状态）、上架商品、下架商品、查看我的商品。
管理员可以查看所有商品、下架商品（违规商品由管理员下架）、查看系统内所有用户信息、修改用户密码。
1.2项目需求分析
1、用户可以实现一些对商品的购买、搜索和加购物车等功能，用户对自己的购物车和订单有一定的管理能力。
2、商家可以浏览自己的商品，对自己的商品进行一些基本的维护功能。
3、管理员要对整个系统内的用户和商品进行管理。
4、实现电商管理系统的基本登录和购买功能。
2、项目设计
2.1项目目标
实现用户下单购买的功能（下单成功后返回订单号）
用户可以浏览商品、将商品加入购物车、将商品移出购物车、按订单结算、查看自己各种状态的订单。
用户可以对商品进行模糊查寻，输入关键字即可查找到相关商品。
商家可以自己上架、下架商品
商家可以处理自己的订单发货，订单发货对应的订单状态也会发生变化，对应商品的库存也会发生变化。
管理员可以审核违规商品予以下架处理。
管理员可以为系统内所有的用户修改密码
2.2 构建开发环境
数据库可视化软件：Navicat Premium
数据库：mysql8.0
系统开发平台：IntelliJ IDEA 2020.1 x64
系统开发语言：Java
运行平台：Win11
