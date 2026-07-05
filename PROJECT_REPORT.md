# 智能订餐助手 - 项目报告

## 一、项目概述

### 1.1 项目简介

**智能订餐助手**是一款基于Android平台开发的餐饮点餐应用，旨在为用户提供便捷、智能的订餐体验。应用集成了菜单浏览、购物车管理、订单历史、AI智能推荐等核心功能，并支持管理员对菜品进行增删查改操作。

### 1.2 项目背景

随着移动互联网的快速发展，线上订餐已经成为人们日常生活中不可或缺的一部分。传统的点餐方式效率低下，用户体验不佳。智能订餐助手通过结合人工智能技术，为用户提供更加个性化、智能化的点餐服务，提升用户体验和餐厅运营效率。

### 1.3 项目目标

| 目标类别 | 具体目标 |
|----------|----------|
| 用户体验 | 提供简洁、直观的点餐界面，支持菜品浏览、购物车管理、订单查看 |
| 智能化 | 集成AI智能推荐功能，根据用户口味偏好推荐菜品 |
| 管理功能 | 提供管理员后台，支持菜品的增删查改操作 |
| 安全性 | 实现用户登录系统，区分普通用户和管理员权限 |

### 1.4 技术栈

| 技术分类 | 技术名称 | 版本 |
|----------|----------|------|
| 开发语言 | Java | 11 |
| 开发工具 | Android Studio | 2023.x |
| 构建工具 | Gradle | 8.5 |
| Android SDK | compileSdk | 34 |
| Android SDK | minSdk | 21 |
| Android SDK | targetSdk | 34 |
| UI框架 | AndroidX | - |
| 设计风格 | Material Design | 3 |
| 数据库 | SharedPreferences | - |

---

## 二、需求分析

### 2.1 功能需求

#### 2.1.1 用户功能需求

| 需求编号 | 需求描述 | 优先级 | 来源 |
|----------|----------|--------|------|
| F001 | 用户登录功能，支持普通用户、管理员、游客三种模式 | 高 | 项目要求 |
| F002 | 菜单浏览功能，展示所有菜品信息（图片、名称、价格、描述） | 高 | 项目要求 |
| F003 | 购物车功能，支持添加、删除、修改商品数量 | 高 | 项目要求 |
| F004 | 下单功能，生成订单并跳转付款页面 | 高 | 项目要求 |
| F005 | 订单历史功能，查看已完成的订单 | 中 | 项目要求 |
| F006 | AI智能推荐功能，与AI聊天获取菜品推荐 | 高 | 项目要求（加分项） |
| F007 | 付款页面，展示付款二维码 | 高 | 项目要求 |

#### 2.1.2 管理员功能需求

| 需求编号 | 需求描述 | 优先级 | 来源 |
|----------|----------|--------|------|
| A001 | 管理员登录功能，验证用户名和密码 | 高 | 项目要求 |
| A002 | 菜品管理功能，查看所有菜品列表 | 高 | 项目要求 |
| A003 | 添加菜品功能，新增菜品信息 | 高 | 项目要求 |
| A004 | 编辑菜品功能，修改菜品名称、描述、价格 | 高 | 项目要求 |
| A005 | 删除菜品功能，移除指定菜品 | 高 | 项目要求 |

### 2.2 非功能需求

| 需求编号 | 需求描述 | 优先级 |
|----------|----------|--------|
| N001 | 界面美观，采用现代化设计风格 | 高 |
| N002 | 响应速度快，操作流畅 | 高 |
| N003 | 兼容Android 5.0及以上版本 | 中 |
| N004 | 数据本地存储，无需网络即可使用 | 中 |

---

## 三、系统设计

### 3.1 架构设计

#### 3.1.1 整体架构

本项目采用经典的MVC（Model-View-Controller）架构模式，将数据层、视图层和控制层分离，提高代码的可维护性和可扩展性。

```
┌─────────────────────────────────────────────────────────────┐
│                        View Layer                          │
│  LoginActivity / MainActivity / CartActivity / AdminActivity│
│  AIRecommendActivity / OrderHistoryActivity / PaymentActivity│
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                     Controller Layer                        │
│         UserManager / AppData / AIApiClient                 │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                          Model Layer                        │
│         FoodItem / CartItem / Order / ChatMessage           │
└─────────────────────────────────────────────────────────────┘
```

#### 3.1.2 模块划分

| 模块名称 | 职责描述 | 包含文件 |
|----------|----------|----------|
| **登录模块** | 用户身份验证、登录状态管理 | LoginActivity, UserManager |
| **菜单模块** | 菜品展示、添加购物车 | MainActivity, FoodAdapter, FoodItem |
| **购物车模块** | 购物车管理、下单功能 | CartActivity, CartAdapter, CartItem |
| **订单模块** | 订单历史查看 | OrderHistoryActivity, OrderAdapter, Order |
| **AI模块** | 智能推荐、聊天功能 | AIRecommendActivity, ChatAdapter, AIApiClient |
| **付款模块** | 付款页面展示 | PaymentActivity |
| **管理模块** | 菜品增删查改 | AdminActivity, AdminFoodAdapter, EditFoodDialog |
| **数据模块** | 全局数据管理 | AppData |

### 3.2 数据库设计

由于本项目数据量较小且无需复杂查询，采用SharedPreferences进行本地数据存储。

#### 3.2.1 用户信息存储

| Key | Value类型 | 说明 |
|-----|-----------|------|
| user_type | int | 用户类型（0-游客，1-普通用户，2-管理员） |
| username | String | 用户名 |
| is_logged_in | boolean | 是否登录 |

#### 3.2.2 购物车数据存储

购物车数据以JSON格式存储在SharedPreferences中，结构如下：

```json
{
  "cart": [
    {
      "foodId": 1,
      "name": "红烧肉",
      "price": 38.0,
      "imageResId": 2131230720,
      "quantity": 2
    }
  ]
}
```

#### 3.2.3 订单数据存储

订单数据以JSON格式存储在SharedPreferences中，结构如下：

```json
{
  "orders": [
    {
      "orderId": "ORD202401010001",
      "items": [...],
      "totalPrice": 76.0,
      "orderTime": "2024-01-01 12:00:00"
    }
  ]
}
```

### 3.3 界面设计

#### 3.3.1 登录界面

**设计要点**：
- 全屏背景图片（login.png）
- 半透明白色登录卡片
- 用户名、密码输入框
- 用户类型选择（普通用户/管理员）
- 登录按钮和游客进入按钮

**界面示意图**：

```
[登录界面截图位置]

┌─────────────────────────────────────┐
│                                     │
│           【背景图片】               │
│                                     │
│         ┌──────────────────┐        │
│         │   订餐助手        │        │
│         │  智能订餐助手     │        │
│         │                  │        │
│         │  [用户名输入框]   │        │
│         │  [密码输入框]     │        │
│         │  ○ 普通用户       │        │
│         │  ○ 管理员         │        │
│         │                  │        │
│         │    [登录按钮]     │        │
│         │   [游客进入]      │        │
│         └──────────────────┘        │
│                                     │
│     管理员账号：admin / admin123     │
│                                     │
└─────────────────────────────────────┘
```

#### 3.3.2 主界面（菜单页面）

**设计要点**：
- Material Design风格顶部导航栏
- 用户信息显示区域
- 管理员后台入口按钮
- 菜品列表（RecyclerView）
- 底部导航栏（菜单/购物车/订单/AI）

**界面示意图**：

```
[主界面截图位置]

┌─────────────────────────────────────┐
│  菜单              用户：xxx        │
│                                    │
│  [管理后台]                         │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  红烧肉           ¥38.00     │  │
│  │  肥而不腻，入口即化            │  │
│  │              [加入购物车]      │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  宫保鸡丁         ¥28.00     │  │
│  │  鸡肉嫩滑，花生酥脆            │  │
│  │              [加入购物车]      │  │
│  └──────────────────────────────┘  │
│                                    │
│  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ │
│  菜单   购物车   订单   AI推荐     │
└─────────────────────────────────────┘
```

#### 3.3.3 购物车界面

**设计要点**：
- 返回按钮
- 购物车商品列表
- 商品数量增减按钮
- 删除商品按钮
- 合计金额显示
- 下单按钮

**界面示意图**：

```
[购物车界面截图位置]

┌─────────────────────────────────────┐
│  ← 购物车                           │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  红烧肉           ¥38.00     │  │
│  │                              │  │
│  │  [-]  2  [+]    [删除]       │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  宫保鸡丁         ¥28.00     │  │
│  │                              │  │
│  │  [-]  1  [+]    [删除]       │  │
│  └──────────────────────────────┘  │
│                                    │
│  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ │
│  合计：¥66.00                      │
│          [立即下单]                 │
└─────────────────────────────────────┘
```

#### 3.3.4 订单历史界面

**设计要点**：
- 返回按钮
- 订单列表
- 订单编号、时间、金额显示
- 订单详情展开

**界面示意图**：

```
[订单历史界面截图位置]

┌─────────────────────────────────────┐
│  ← 订单历史                         │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  订单号：ORD202401010001     │  │
│  │  时间：2024-01-01 12:00:00    │  │
│  │  金额：¥66.00                 │  │
│  │                              │  │
│  │  红烧肉 x2                    │  │
│  │  宫保鸡丁 x1                  │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  订单号：ORD202401020002     │  │
│  │  时间：2024-01-02 18:30:00    │  │
│  │  金额：¥45.00                 │  │
│  └──────────────────────────────┘  │
└─────────────────────────────────────┘
```

#### 3.3.5 AI推荐界面

**设计要点**：
- 返回按钮
- 聊天消息列表（RecyclerView）
- 输入框和发送按钮
- 加载进度条

**界面示意图**：

```
[AI推荐界面截图位置]

┌─────────────────────────────────────┐
│  ← AI智能推荐                       │
│                                    │
│  ┌──────────────────────────────┐  │
│  │ AI: 您好！我是您的智能点餐助   │  │
│  │     手，请问有什么可以帮您的？ │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │ 我: 有什么推荐的吗？          │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │ AI: 我们店的招牌菜是红烧肉... │  │
│  └──────────────────────────────┘  │
│                                    │
│  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ │
│  [输入消息]          [发送]        │
└─────────────────────────────────────┘
```

#### 3.3.6 付款界面

**设计要点**：
- 返回按钮
- 订单信息展示
- 付款二维码
- 付款金额显示

**界面示意图**：

```
[付款界面截图位置]

┌─────────────────────────────────────┐
│  ← 付款                             │
│                                    │
│        ┌────────────────┐          │
│        │   [付款二维码]  │          │
│        │                │          │
│        └────────────────┘          │
│                                    │
│        请使用微信/支付宝扫码付款    │
│                                    │
│        ─ ─ ─ ─ ─ ─ ─ ─ ─ ─        │
│                                    │
│        订单金额：¥66.00            │
│                                    │
│        [确认付款]                   │
└─────────────────────────────────────┘
```

#### 3.3.7 管理员后台界面

**设计要点**：
- 返回按钮
- 菜品管理标题
- 菜品列表（含编辑、删除按钮）
- 添加菜品按钮

**界面示意图**：

```
[管理员后台界面截图位置]

┌─────────────────────────────────────┐
│  ← 管理后台                         │
│                                    │
│  [添加菜品]                         │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  红烧肉           ¥38.00     │  │
│  │  [编辑]    [删除]             │  │
│  └──────────────────────────────┘  │
│                                    │
│  ┌──────────────────────────────┐  │
│  │  [菜品图片]                   │  │
│  │  宫保鸡丁         ¥28.00     │  │
│  │  [编辑]    [删除]             │  │
│  └──────────────────────────────┘  │
└─────────────────────────────────────┘
```

### 3.4 类设计

#### 3.4.1 数据模型类

##### FoodItem类

| 属性名 | 类型 | 说明 | 访问权限 |
|--------|------|------|----------|
| id | int | 菜品ID | private |
| name | String | 菜品名称 | private |
| description | String | 菜品描述 | private |
| price | double | 菜品价格 | private |
| imageResId | int | 菜品图片资源ID | private |

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| FoodItem() | - | - | 空构造函数 |
| FoodItem(int, String, String, double, int) | - | id, name, description, price, imageResId | 带参构造函数 |
| getId() | int | - | 获取菜品ID |
| getName() | String | - | 获取菜品名称 |
| getDescription() | String | - | 获取菜品描述 |
| getPrice() | double | - | 获取菜品价格 |
| getImageResId() | int | - | 获取菜品图片资源ID |
| setName(String) | void | name | 设置菜品名称 |
| setDescription(String) | void | description | 设置菜品描述 |
| setPrice(double) | void | price | 设置菜品价格 |

##### CartItem类

| 属性名 | 类型 | 说明 | 访问权限 |
|--------|------|------|----------|
| foodId | int | 菜品ID | private |
| name | String | 菜品名称 | private |
| price | double | 菜品价格 | private |
| imageResId | int | 菜品图片资源ID | private |
| quantity | int | 购买数量 | private |

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| CartItem() | - | - | 空构造函数 |
| CartItem(int, String, double, int, int) | - | foodId, name, price, imageResId, quantity | 带参构造函数 |
| getFoodId() | int | - | 获取菜品ID |
| getName() | String | - | 获取菜品名称 |
| getPrice() | double | - | 获取菜品价格 |
| getImageResId() | int | - | 获取菜品图片资源ID |
| getQuantity() | int | - | 获取购买数量 |
| setQuantity(int) | void | quantity | 设置购买数量 |
| getTotalPrice() | double | - | 计算小计金额 |

##### Order类

| 属性名 | 类型 | 说明 | 访问权限 |
|--------|------|------|----------|
| orderId | String | 订单编号 | private |
| items | List\<CartItem\> | 订单项列表 | private |
| totalPrice | double | 订单总金额 | private |
| orderTime | String | 下单时间 | private |

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| Order() | - | - | 空构造函数 |
| Order(String, List\<CartItem\>, double, String) | - | orderId, items, totalPrice, orderTime | 带参构造函数 |
| getOrderId() | String | - | 获取订单编号 |
| getItems() | List\<CartItem\> | - | 获取订单项列表 |
| getTotalPrice() | double | - | 获取订单总金额 |
| getOrderTime() | String | - | 获取下单时间 |

##### ChatMessage类

| 属性名 | 类型 | 说明 | 访问权限 |
|--------|------|------|----------|
| content | String | 消息内容 | private |
| type | int | 消息类型（1-用户，2-AI） | private |

| 常量名 | 值 | 说明 |
|--------|-----|------|
| TYPE_USER | 1 | 用户消息类型 |
| TYPE_AI | 2 | AI消息类型 |

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| ChatMessage(String, int) | - | content, type | 带参构造函数 |
| getContent() | String | - | 获取消息内容 |
| getType() | int | - | 获取消息类型 |
| isUserMessage() | boolean | - | 判断是否为用户消息 |

#### 3.4.2 控制器类

##### UserManager类

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| login(Context, int, String) | void | context, userType, username | 用户登录 |
| logout(Context) | void | context | 用户登出 |
| isLoggedIn(Context) | boolean | context | 判断是否已登录 |
| isAdmin(Context) | boolean | context | 判断是否为管理员 |
| isNormalUser(Context) | boolean | context | 判断是否为普通用户 |
| getUsername(Context) | String | context | 获取用户名 |
| getUserType(Context) | int | context | 获取用户类型 |

| 常量名 | 值 | 说明 |
|--------|-----|------|
| USER_TYPE_GUEST | 0 | 游客类型 |
| USER_TYPE_NORMAL | 1 | 普通用户类型 |
| USER_TYPE_ADMIN | 2 | 管理员类型 |

##### AppData类

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| getFoodList() | List\<FoodItem\> | - | 获取菜品列表 |
| setFoodList(List\<FoodItem\>) | void | foodList | 设置菜品列表 |
| addFood(FoodItem) | void | food | 添加菜品 |
| removeFood(int) | void | foodId | 删除菜品 |
| updateFood(FoodItem) | void | food | 更新菜品 |
| getFoodById(int) | FoodItem | foodId | 根据ID获取菜品 |
| getCartItems(Context) | List\<CartItem\> | context | 获取购物车列表 |
| saveCartItems(Context, List\<CartItem\>) | void | context, items | 保存购物车列表 |
| clearCart(Context) | void | context | 清空购物车 |
| getOrders(Context) | List\<Order\> | context | 获取订单列表 |
| saveOrder(Context, Order) | void | context, order | 保存订单 |

##### AIApiClient类

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| sendMessage(String, OnResponseListener) | void | message, listener | 发送消息到AI |

| 接口名 | 方法 | 说明 |
|--------|------|------|
| OnResponseListener | onSuccess(String) | 成功回调 |
| OnResponseListener | onError(String) | 失败回调 |

### 3.5 流程图

#### 3.5.1 用户登录流程

```
[登录流程图位置]

开始 → 显示登录界面 → 用户选择登录类型
    ├─ 游客进入 → 设置游客身份 → 进入主界面
    └─ 输入用户名密码
        ├─ 普通用户 → 验证通过 → 设置用户身份 → 进入主界面
        └─ 管理员 → 验证用户名密码(admin/admin123)
            ├─ 验证通过 → 设置管理员身份 → 进入主界面(显示管理后台按钮)
            └─ 验证失败 → 提示错误 → 返回登录界面
```

#### 3.5.2 点餐流程

```
[点餐流程图位置]

开始 → 浏览菜单 → 选择菜品 → 点击"加入购物车"
    → 购物车数量+1 → 继续点餐或查看购物车
        ├─ 继续点餐 → 返回菜单浏览
        └─ 查看购物车 → 修改数量/删除商品 → 点击"立即下单"
            → 生成订单 → 跳转付款页面 → 扫码付款 → 保存订单 → 显示订单历史
```

#### 3.5.3 AI聊天流程

```
[AI聊天流程图位置]

开始 → 进入AI推荐界面 → 输入消息 → 点击发送
    → 显示用户消息 → 调用AI API
        ├─ API可用 → 获取AI回复 → 显示AI消息
        └─ API不可用 → 生成本地智能回复 → 显示AI消息
```

#### 3.5.4 管理员管理流程

```
[管理员管理流程图位置]

开始 → 管理员登录 → 进入管理后台 → 选择操作
    ├─ 添加菜品 → 填写菜品信息 → 保存 → 更新菜品列表
    ├─ 编辑菜品 → 修改菜品信息 → 保存 → 更新菜品列表
    └─ 删除菜品 → 确认删除 → 删除 → 更新菜品列表
```

---

## 四、代码实现

### 4.1 项目目录结构

```
anzhuo/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/foodordering/
│   │       │       ├── Activities/
│   │       │       │   ├── LoginActivity.java       # 登录界面
│   │       │       │   ├── MainActivity.java        # 主界面
│   │       │       │   ├── CartActivity.java        # 购物车界面
│   │       │       │   ├── OrderHistoryActivity.java# 订单历史界面
│   │       │       │   ├── AIRecommendActivity.java # AI推荐界面
│   │       │       │   ├── PaymentActivity.java     # 付款界面
│   │       │       │   └── AdminActivity.java       # 管理员后台
│   │       │       ├── Adapters/
│   │       │       │   ├── FoodAdapter.java         # 菜品列表适配器
│   │       │       │   ├── CartAdapter.java         # 购物车适配器
│   │       │       │   ├── OrderAdapter.java        # 订单列表适配器
│   │       │       │   ├── ChatAdapter.java         # 聊天消息适配器
│   │       │       │   └── AdminFoodAdapter.java    # 管理员菜品适配器
│   │       │       ├── Models/
│   │       │       │   ├── FoodItem.java            # 菜品数据模型
│   │       │       │   ├── CartItem.java            # 购物车项数据模型
│   │       │       │   ├── Order.java               # 订单数据模型
│   │       │       │   └── ChatMessage.java         # 聊天消息数据模型
│   │       │       ├── Managers/
│   │       │       │   ├── UserManager.java         # 用户管理类
│   │       │       │   └── AppData.java             # 全局数据管理
│   │       │       ├── API/
│   │       │       │   └── AIApiClient.java         # AI API客户端
│   │       │       └── Dialogs/
│   │       │           └── EditFoodDialog.java      # 菜品编辑对话框
│   │       ├── res/
│   │       │   ├── drawable/                        # 图片资源
│   │       │   │   ├── login.png                    # 登录背景图片
│   │       │   │   ├── qr_code.jpg                  # 付款二维码
│   │       │   │   ├── food_hongshaorou.jpg         # 红烧肉图片
│   │       │   │   ├── food_gongbaojiding.jpg       # 宫保鸡丁图片
│   │       │   │   ├── food_qingzhengluyu.jpg       # 清蒸鲈鱼图片
│   │       │   │   ├── food_mapodoufu.jpg           # 麻婆豆腐图片
│   │       │   │   ├── food_tangcupaigu.jpg         # 糖醋排骨图片
│   │       │   │   ├── food_yuxiangrousi.jpg        # 鱼香肉丝图片
│   │       │   │   ├── food_huiguorou.jpg           # 回锅肉图片
│   │       │   │   ├── food_suanrongxilan.jpg       # 蒜蓉西兰花图片
│   │       │   │   └── edittext_border.xml          # 输入框边框样式
│   │       │   ├── layout/                          # 布局文件
│   │       │   │   ├── activity_login.xml           # 登录界面布局
│   │       │   │   ├── activity_main.xml            # 主界面布局
│   │       │   │   ├── activity_cart.xml            # 购物车界面布局
│   │       │   │   ├── activity_order_history.xml   # 订单历史布局
│   │       │   │   ├── activity_ai_recommend.xml    # AI推荐布局
│   │       │   │   ├── activity_payment.xml         # 付款界面布局
│   │       │   │   ├── activity_admin.xml          # 管理员后台布局
│   │       │   │   ├── item_food.xml               # 菜品列表项布局
│   │       │   │   ├── item_cart.xml               # 购物车项布局
│   │       │   │   ├── item_order.xml              # 订单项布局
│   │       │   │   ├── item_user_message.xml       # 用户消息布局
│   │       │   │   ├── item_ai_message.xml         # AI消息布局
│   │       │   │   ├── item_admin_food.xml         # 管理员菜品项布局
│   │       │   │   └── dialog_edit_food.xml        # 编辑对话框布局
│   │       │   ├── values/                         # 资源文件
│   │       │   │   ├── strings.xml                 # 字符串资源
│   │       │   │   ├── colors.xml                  # 颜色资源
│   │       │   │   └── styles.xml                  # 样式资源
│   │       │   └── menu/                           # 菜单资源
│   │       └── AndroidManifest.xml                 # Android配置文件
│   └── build.gradle                                # 模块构建配置
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties                               # Gradle属性配置
├── settings.gradle                                 # 项目设置
└── build.gradle                                    # 项目构建配置
```

### 4.2 核心代码实现

#### 4.2.1 登录功能实现

**LoginActivity.java 关键代码**：

```java
// 用户登录处理
private void handleLogin() {
    String username = usernameInput.getText().toString().trim();
    String password = passwordInput.getText().toString().trim();
    
    // 验证用户名和密码不为空
    if (username.isEmpty()) {
        Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        return;
    }
    if (password.isEmpty()) {
        Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        return;
    }
    
    // 判断用户类型
    int selectedId = userTypeGroup.getCheckedRadioButtonId();
    int userType = UserManager.USER_TYPE_NORMAL;
    
    if (selectedId == R.id.radioAdmin) {
        // 管理员验证
        if (!username.equals("admin") || !password.equals("admin123")) {
            Toast.makeText(this, "管理员账号或密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        userType = UserManager.USER_TYPE_ADMIN;
    }
    
    // 保存用户登录状态
    UserManager.login(this, userType, username);
    
    // 跳转主界面
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
}
```

#### 4.2.2 购物车功能实现

**CartActivity.java 关键代码**：

```java
// 添加菜品到购物车
public static void addToCart(Context context, FoodItem food) {
    List<CartItem> cartItems = AppData.getCartItems(context);
    boolean found = false;
    
    // 检查购物车中是否已有该菜品
    for (CartItem item : cartItems) {
        if (item.getFoodId() == food.getId()) {
            item.setQuantity(item.getQuantity() + 1);
            found = true;
            break;
        }
    }
    
    // 如果没有，添加新项
    if (!found) {
        cartItems.add(new CartItem(
            food.getId(),
            food.getName(),
            food.getPrice(),
            food.getImageResId(),
            1
        ));
    }
    
    // 保存购物车数据
    AppData.saveCartItems(context, cartItems);
}

// 下单功能
private void placeOrder() {
    List<CartItem> cartItems = AppData.getCartItems(this);
    if (cartItems.isEmpty()) {
        Toast.makeText(this, "购物车为空", Toast.LENGTH_SHORT).show();
        return;
    }
    
    // 计算总金额
    double totalPrice = 0;
    for (CartItem item : cartItems) {
        totalPrice += item.getTotalPrice();
    }
    
    // 生成订单
    String orderId = "ORD" + System.currentTimeMillis();
    String orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    Order order = new Order(orderId, new ArrayList<>(cartItems), totalPrice, orderTime);
    
    // 保存订单
    AppData.saveOrder(this, order);
    
    // 清空购物车
    AppData.clearCart(this);
    
    // 跳转付款页面
    Intent intent = new Intent(this, PaymentActivity.class);
    intent.putExtra("orderId", orderId);
    intent.putExtra("totalPrice", totalPrice);
    startActivity(intent);
    finish();
}
```

#### 4.2.3 AI聊天功能实现

**AIRecommendActivity.java 关键代码**：

```java
// 发送消息
private void sendMessage() {
    String text = inputText.getText().toString().trim();
    if (text.isEmpty()) return;
    
    // 添加用户消息
    messages.add(new ChatMessage(text, ChatMessage.TYPE_USER));
    inputText.setText("");
    chatAdapter.notifyItemInserted(messages.size() - 1);
    chatRecyclerView.scrollToPosition(messages.size() - 1);
    
    // 构建上下文消息
    String fullMessage = buildMessageWithContext(text);
    
    // 调用AI API
    AIApiClient.sendMessage(fullMessage, new AIApiClient.OnResponseListener() {
        @Override
        public void onSuccess(String response) {
            messages.add(new ChatMessage(response, ChatMessage.TYPE_AI));
            chatAdapter.notifyItemInserted(messages.size() - 1);
            chatRecyclerView.scrollToPosition(messages.size() - 1);
        }
        
        @Override
        public void onError(String error) {
            // API不可用时使用本地智能回复
            String fallbackResponse = generateSmartResponse(text);
            messages.add(new ChatMessage(fallbackResponse, ChatMessage.TYPE_AI));
            chatAdapter.notifyItemInserted(messages.size() - 1);
            chatRecyclerView.scrollToPosition(messages.size() - 1);
        }
    });
}

// 本地智能回复生成
private String generateSmartResponse(String userMessage) {
    String lowerMsg = userMessage.toLowerCase();
    
    // 识别"你是谁"
    if (containsAny(lowerMsg, "你是谁", "你叫什么", "自我介绍")) {
        return getRandomResponse(new String[]{
            "我是您的智能点餐助手，专门为您推荐美味佳肴！",
            "您好！我是餐厅的智能点餐助手，可以帮您推荐菜品、介绍菜单哦~"
        });
    }
    
    // 识别"推荐"
    if (containsAny(lowerMsg, "推荐", "好吃", "特色")) {
        FoodItem randomFood = getRandomFood();
        return "我们店的招牌菜是" + randomFood.getName() + "，" + 
               randomFood.getDescription() + "，价格" + randomFood.getPrice() + "元，强烈推荐！";
    }
    
    // 识别"不能吃辣"
    if (containsAny(lowerMsg, "不能吃辣", "不吃辣", "清淡")) {
        return "为您推荐不辣的菜品：清蒸鲈鱼、糖醋排骨、蒜蓉西兰花";
    }
    
    // 更多识别逻辑...
}
```

#### 4.2.4 管理员管理功能实现

**AdminActivity.java 关键代码**：

```java
// 添加菜品
private void addFood() {
    EditFoodDialog dialog = new EditFoodDialog(this, null, (name, desc, price) -> {
        FoodItem newFood = new FoodItem(
            AppData.getFoodList().size() + 1,
            name,
            desc,
            price,
            R.drawable.food_hongshaorou // 默认图片
        );
        AppData.addFood(newFood);
        adminFoodAdapter.notifyDataSetChanged();
        Toast.makeText(AdminActivity.this, "菜品添加成功", Toast.LENGTH_SHORT).show();
    });
    dialog.show();
}

// 编辑菜品
private void editFood(FoodItem food) {
    EditFoodDialog dialog = new EditFoodDialog(this, food, (name, desc, price) -> {
        food.setName(name);
        food.setDescription(desc);
        food.setPrice(price);
        AppData.updateFood(food);
        adminFoodAdapter.notifyDataSetChanged();
        Toast.makeText(AdminActivity.this, "菜品修改成功", Toast.LENGTH_SHORT).show();
    });
    dialog.show();
}

// 删除菜品
private void deleteFood(int foodId) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("确认删除");
    builder.setMessage("确定要删除这个菜品吗？");
    builder.setPositiveButton("确定", (dialog, which) -> {
        AppData.removeFood(foodId);
        adminFoodAdapter.notifyDataSetChanged();
        Toast.makeText(AdminActivity.this, "菜品删除成功", Toast.LENGTH_SHORT).show();
    });
    builder.setNegativeButton("取消", null);
    builder.show();
}
```

---

## 五、测试与验证

### 5.1 功能测试

#### 5.1.1 登录功能测试

| 测试用例 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 |
|----------|----------|----------|----------|----------|
| TC001 | 游客进入 | 直接进入主界面，显示游客模式 | [填写实际结果] | [填写] |
| TC002 | 普通用户登录（任意账号） | 进入主界面，显示用户名 | [填写实际结果] | [填写] |
| TC003 | 管理员登录（admin/admin123） | 进入主界面，显示管理后台按钮 | [填写实际结果] | [填写] |
| TC004 | 管理员登录（错误密码） | 提示"管理员账号或密码错误" | [填写实际结果] | [填写] |

#### 5.1.2 菜单浏览测试

| 测试用例 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 |
|----------|----------|----------|----------|----------|
| TC005 | 查看菜单列表 | 显示8道菜品，包含图片、名称、价格、描述 | [填写实际结果] | [填写] |
| TC006 | 添加菜品到购物车 | 点击"加入购物车"按钮，购物车数量+1 | [填写实际结果] | [填写] |

#### 5.1.3 购物车功能测试

| 测试用例 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 |
|----------|----------|----------|----------|----------|
| TC007 | 修改商品数量（+1） | 数量增加，小计金额更新 | [填写实际结果] | [填写] |
| TC008 | 修改商品数量（-1） | 数量减少，小计金额更新 | [填写实际结果] | [填写] |
| TC009 | 删除商品 | 商品从购物车中移除 | [填写实际结果] | [填写] |
| TC010 | 清空购物车后下单 | 提示"购物车为空" | [填写实际结果] | [填写] |
| TC011 | 正常下单 | 生成订单，跳转付款页面 | [填写实际结果] | [填写] |

#### 5.1.4 AI聊天功能测试

| 测试用例 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 |
|----------|----------|----------|----------|----------|
| TC012 | 输入"你是谁" | AI介绍自己是智能点餐助手 | [填写实际结果] | [填写] |
| TC013 | 输入"推荐一下" | AI推荐一道菜品 | [填写实际结果] | [填写] |
| TC014 | 输入"不能吃辣" | AI推荐不辣的菜品 | [填写实际结果] | [填写] |
| TC015 | 输入"菜单" | AI列出所有菜品 | [填写实际结果] | [填写] |
| TC016 | 输入菜品名称 | AI介绍该菜品详情 | [填写实际结果] | [填写] |

#### 5.1.5 管理员功能测试

| 测试用例 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 |
|----------|----------|----------|----------|----------|
| TC017 | 添加菜品 | 填写信息后保存，菜品列表更新 | [填写实际结果] | [填写] |
| TC018 | 编辑菜品 | 修改信息后保存，菜品列表更新 | [填写实际结果] | [填写] |
| TC019 | 删除菜品 | 确认后删除，菜品列表更新 | [填写实际结果] | [填写] |

### 5.2 兼容性测试

| 测试用例 | 测试设备 | Android版本 | 测试结果 | 是否通过 |
|----------|----------|-------------|----------|----------|
| TC020 | 模拟器 | Android 5.0 (API 21) | [填写实际结果] | [填写] |
| TC021 | 模拟器 | Android 8.0 (API 26) | [填写实际结果] | [填写] |
| TC022 | 模拟器 | Android 13 (API 33) | [填写实际结果] | [填写] |
| TC023 | 真机 | Android 12 | [填写实际结果] | [填写] |

### 5.3 性能测试

| 测试项目 | 测试结果 | 备注 |
|----------|----------|------|
| 应用启动时间 | [填写实际结果] | 从点击图标到主界面显示 |
| 菜单加载时间 | [填写实际结果] | 从进入菜单页面到菜品列表显示 |
| 购物车加载时间 | [填写实际结果] | 从进入购物车页面到商品列表显示 |
| AI响应时间 | [填写实际结果] | 从发送消息到收到回复 |

---

## 六、项目特色与创新

### 6.1 智能AI推荐系统

本应用集成了AI智能推荐功能，用户可以与AI聊天获取菜品推荐。AI能够：

- **识别菜品名称**：当用户提到具体菜品时，AI会介绍该菜品的详情和价格
- **口味推荐**：根据用户的口味偏好（如"不能吃辣"、"喜欢辣"）推荐合适的菜品
- **智能对话**：支持日常问候、菜单查询、价格咨询等多种对话场景
- **多轮回复**：每个问题都有多个不同的回复，避免重复，增加交互性

### 6.2 完善的用户权限管理

应用实现了三级用户权限系统：

- **游客模式**：无需登录即可使用基本功能（浏览菜单、下单）
- **普通用户**：登录后可使用完整功能，数据本地存储
- **管理员**：拥有菜品管理权限，可进行增删查改操作

### 6.3 现代化UI设计

采用Material Design 3设计风格：

- **渐变背景**：登录页面使用自定义背景图片
- **卡片式布局**：半透明白色卡片，现代化设计
- **圆角输入框**：带边框和圆角的输入框样式
- **响应式布局**：适配不同屏幕尺寸

### 6.4 本地数据存储

所有数据均存储在本地SharedPreferences中：

- 无需网络即可使用
- 数据安全可靠
- 支持离线操作

---

## 七、技术亮点

### 7.1 模块化设计

项目采用模块化设计，各功能模块独立：

- **Activity层**：负责界面展示和用户交互
- **Adapter层**：负责RecyclerView数据绑定和渲染
- **Model层**：负责数据模型定义
- **Manager层**：负责业务逻辑处理和数据管理

### 7.2 代码复用

通过封装工具类实现代码复用：

- **UserManager**：统一管理用户登录状态
- **AppData**：统一管理全局数据（菜品、购物车、订单）
- **AIApiClient**：统一管理AI API调用

### 7.3 异常处理

完善的异常处理机制：

- AI API调用失败时自动切换到本地智能回复
- 购物车为空时提示用户
- 管理员登录失败时提示错误信息

### 7.4 用户体验优化

多项用户体验优化措施：

- 登录界面使用背景图片，提升视觉效果
- 购物车操作即时反馈（数量变化、金额更新）
- 聊天界面自动滚动到最新消息
- 加载状态显示（AI回复时显示进度条）

---

## 八、总结与展望

### 8.1 项目总结

本项目成功实现了一个功能完整的智能订餐助手应用，包含以下核心功能：

1. **用户登录系统**：支持游客、普通用户、管理员三种模式
2. **菜单浏览功能**：展示8道菜品，支持添加到购物车
3. **购物车管理**：支持修改数量、删除商品、下单功能
4. **订单历史**：查看已完成的订单记录
5. **AI智能推荐**：与AI聊天获取菜品推荐
6. **付款界面**：展示付款二维码
7. **管理员后台**：菜品的增删查改操作

### 8.2 项目亮点

- ✅ 集成AI智能推荐功能（符合项目要求的加分项）
- ✅ 完整的用户权限管理系统
- ✅ 现代化的Material Design界面设计
- ✅ 本地数据存储，无需网络即可使用
- ✅ 完善的错误处理和用户反馈
- ✅ 良好的代码结构和模块化设计

### 8.3 后续改进方向

| 改进项 | 描述 | 优先级 |
|--------|------|--------|
| 网络功能 | 添加网络请求，连接真实的后端API | 高 |
| 用户注册 | 添加用户注册功能 | 中 |
| 图片上传 | 支持管理员上传菜品图片 | 中 |
| 订单状态 | 添加订单状态跟踪（待付款、已付款、已完成） | 中 |
| 评价系统 | 添加菜品评价和评分功能 | 低 |
| 多语言支持 | 支持中英文切换 | 低 |

### 8.4 致谢

感谢指导老师在项目开发过程中给予的指导和帮助！

---

## 附录

### 附录A：菜品列表

| 菜品名称 | 价格 | 描述 | 图片资源 |
|----------|------|------|----------|
| 红烧肉 | ¥38.00 | 肥而不腻，入口即化，经典中式菜肴 | food_hongshaorou.jpg |
| 宫保鸡丁 | ¥28.00 | 鸡肉嫩滑，花生酥脆，微辣鲜香 | food_gongbaojiding.jpg |
| 清蒸鲈鱼 | ¥45.00 | 鱼肉鲜嫩，原汁原味，清淡爽口 | food_qingzhengluyu.jpg |
| 麻婆豆腐 | ¥18.00 | 麻辣鲜香，豆腐嫩滑，下饭神器 | food_mapodoufu.jpg |
| 糖醋排骨 | ¥32.00 | 酸甜可口，外酥里嫩，老少皆宜 | food_tangcupaigu.jpg |
| 鱼香肉丝 | ¥26.00 | 肉丝鲜嫩，配菜丰富，酸甜微辣 | food_yuxiangrousi.jpg |
| 回锅肉 | ¥30.00 | 肥而不腻，香气四溢，川菜经典 | food_huiguorou.jpg |
| 蒜蓉西兰花 | ¥22.00 | 蒜香浓郁，营养丰富，清爽可口 | food_suanrongxilan.jpg |

### 附录B：AI聊天支持的问题

| 问题类型 | 示例问题 | AI回复内容 |
|----------|----------|------------|
| 自我介绍 | 你是谁？你叫什么？ | 介绍自己是智能点餐助手 |
| 菜品推荐 | 推荐一下？有什么好吃的？ | 随机推荐一道菜品 |
| 口味筛选 | 不能吃辣？想吃辣的？ | 推荐符合口味的菜品 |
| 菜单查询 | 菜单？有什么菜品？ | 列出所有菜品 |
| 价格咨询 | 多少钱？贵不贵？ | 说明价格范围 |
| 菜品详情 | 红烧肉？宫保鸡丁？ | 介绍该菜品详情 |
| 日常问候 | 你好？谢谢？再见？ | 礼貌回应 |
| 帮助查询 | 帮助？怎么用？ | 列出所有功能 |

### 附录C：管理员登录信息

- **用户名**：admin
- **密码**：admin123

### 附录D：项目GitHub链接

[https://github.com/Qjm524/anzhuo](https://github.com/Qjm524/anzhuo)

---

**项目报告撰写日期**：2024年1月

**作者**：[请在此处添加作者姓名]

**指导老师**：[请在此处添加指导老师姓名]

**完成度**：[请在此处填写项目完成度，如：90%]