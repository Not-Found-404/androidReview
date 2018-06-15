# 安卓复习

1. **简述服务的特点及启动方式的分类。**

    + 特点
        + 没有用户界面，其他特性很像Activity
        + 比后台Activity优先级高，不会被轻易终止
        + 即使service被终止，当系统资源恢复的时候，也将自动恢复运行状态，（onStartCommand 返回 START_STICKY）
        + 用于进程之间通信，解决两个安卓应用程序之间调用的关系

    + 启动方式
        + 通过 Context.startService(Intent serviceIntent) 启动service
        + 代码如
            <pre>
            serviceIntent = new Intent(MainActivity.this, MyService.class);
            startService(serviceIntent);</pre>
        + 通过调用Context.stopService()或者Service.stopSelf()停止Service
            > + serivce由其他组件启动的,但是tngzhi过程可以由其他组件或者自身完成;
            > + 如果仅以启动的方式使用的service,这个service需要具备**自我管理的能力**,并且不需要通过外部组件提供的数据或者功能

    + 分类
        + 根据<font color='red'>启动方式</font>分为两类：Started和Bound。
            + Started是通过startService()来启动，主要用于程序内部使用的Service。
            + Bound是通过bindService()来启动，允许多个应用程序共享同一个Service。
        + 根据<font color='red'>使用范围</font>分为**本地服务**和**远程服务**
            + 本地服务用于应用程序内部
            + 远程服务用于安卓系统内部之间的应用程序 

1. **简述Android系统中利用HandlerThread的实现界面更新的步骤。**
1. 绑定方式启动服务的特点与ServiceConnection的作用。
1. Handler如何实现与指定线程的绑定？
1. SharedPreferences的概念及访问模式。
1. 对于FileOutputStream对象，关闭前必须调用flush（）方法，为什么？ 
1. 在SD卡上操作文件的步骤。 
1. 读取资源中的原始格式文件的步骤。
1. SQLite数据库的特点。
1. SQLite数据库遵循的ACID是什么？
1. 简述SQLiteOpenHelper类的作用及使用方法。
1. Android系统中SQLite事务及其使用方法。
1. ContentProvider的概念及作用。 
1. URI的构成及作用。 
1. 创建ContentProvider的步骤。 
1. 什么是位置服务，相关的类有哪些？
1. 如何实现追踪定位？
1. 如何实现敏感区域设置？
1. 如何使用Overlay?
1. Projection 类的作用是什么？
1. 自定义View的定义/调用步骤。
1. Android 2D绘图相关的对象有哪些？
1. Android动画的分类/实现原理及作用对象。
1. Android补间动画的基本类型有哪些？
1. 简述Android动画中的坐标表示方法。
26、Android中实现Tween动画的方式及步骤。
27、Frame动画的具体实现步骤。
28、动画监听器的概念及实现。
29、简述从网络架构模式角度，网络编程的分类及其主要内容。
30、网络编程中，服务器端返回客户端的数据内容格式有哪些？
31、简述TCP Socket通信时服务器端和客户端的操作步骤。
32、简述UDP Socket通信时服务器端（接收）和客户端（发送）的操作步骤。
33、HttpURLConnection编程的主要步骤。
34、简述JSON字符串的解析过程。
35、调用Web Service的主要步骤。
