
# 订餐助手 Android 应用

## 项目简介
这是一个用于课程作业的Android订餐应用，实现了完整的订餐流程功能，包括菜单浏览、购物车管理、订单历史查看以及AI智能推荐。

## 功能特点
1. **菜单浏览** - 浏览各类美食菜品
2. **购物车** - 添加、删除、修改菜品数量
3. **订单管理** - 查看历史订单
4. **AI智能推荐** - 基于模拟算法推荐菜品
5. **Material Design** - 现代化的UI设计

## 技术栈
- Android SDK
- Java
- Material Components
- RecyclerView
- CardView
- AndroidX

## 项目结构
```
app/
├── src/
│   └── main/
│       ├── java/com/example/foodordering/
│       │   ├── MainActivity.java          # 主页面
│       │   ├── CartActivity.java          # 购物车
│       │   ├── OrderHistoryActivity.java  # 订单历史
│       │   ├── AIRecommendActivity.java   # AI推荐
│       │   ├── FoodAdapter.java           # 菜单适配器
│       │   ├── CartAdapter.java           # 购物车适配器
│       │   ├── OrderAdapter.java          # 订单适配器
│       │   ├── FoodItem.java              # 菜品模型
│       │   ├── CartItem.java              # 购物车项模型
│       │   └── Order.java                 # 订单模型
│       ├── res/
│       │   ├── layout/                    # 布局文件
│       │   ├── values/                    # 资源文件
│       │   └── menu/                      # 菜单文件
│       └── AndroidManifest.xml
└── build.gradle
```

## 使用说明
1. 使用 Android Studio 打开项目
2. 等待 Gradle 同步完成
3. 连接 Android 设备或启动模拟器
4. 点击运行按钮即可

## 项目要求完成度
✅ 独立完成，自命题项目  
✅ 使用主流技术（Material Design, RecyclerView等）  
✅ 接入AI功能（智能推荐模块）  
✅ 完整的项目功能

## 作者
课程作业项目

