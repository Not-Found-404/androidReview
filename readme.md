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
            + serivce由其他组件启动的,但是停止过程可以由其他组件或者自身完成;
            + 如果仅以启动的方式使用的service,这个service需要具备**自我管理的能力**,并且不需要通过外部组件提供的数据或者功能
        + 引用自zyy资料
            > + 启动方式：隐式启动和显式启动
            > + 隐式调用，通过调Context.startService()启动Service，通过调用Context.stopService()或Service.stopSelf()停止ServiceService是由其他的组件启动的，但停止过程可以通过其他组件或自身完成；如果仅以启动方式使用的Service，这个Service需要具备自我管理的能力，且不需要通过函数调用向外部组件提供数据或功能(不能获取服务的状态或数据,只是一次性的服务，且启动后一直独立运行，不会随启动它的组件一起消亡)。
            > + 显式启动:在Intent中指明Service所在的类，并调用startService(Intent)函数启动Service,示例代码如
                <pre>final Intent serviceIntent = new Intent(this,RandomService.class);
                startService(serviceintent);</pre>
            > + 隐式启动代码如下
                <pre>final Intent serviceIntent = new Intent();
                serviceIntent.setAction("xxxService");
                startService(serivceIntent); 
                </pre>

    + 分类
        + 根据**启动方式**分为两类：Started和Bound。
            + Started是通过startService()来启动，主要用于程序内部使用的Service。
            + Bound是通过bindService()来启动，允许多个应用程序共享同一个Service。
        + 根据**使用范围**分为**本地服务**和**远程服务**
            + 本地服务用于应用程序内部
            + 远程服务用于安卓系统内部之间的应用程序

1. **简述Android系统中利用HandlerThread的实现界面更新的步骤。**
    + 在单线程模型下，进行多线程程序设计时，Android可以通过Message Queue并结合Handler和Looper在线程间进行信息交换。

    + Message(消息)  —  可理解为线程间交流(双向)的信息。一般情况下，处理数据的后台线程需要更新UI，则发送Message(内含一些数据->Bundle对象-> setData())给前台UI主线程；或者，前台UI主线程需要发送一些数据给后台线程进行 查询/计算/统计 等处理;
    + Handler(处理者) -- 是Message的主要处理者，负责：
        + Message的发送
        + Message内容的接收/执行处理。
1. 绑定方式启动服务的特点与ServiceConnection的作用。
1. Handler如何实现与指定线程的绑定？
1. **SharedPreferences的概念及访问模式**。
    + 概念
        + SharedPreferences是一种轻量级的数据保存方式
        + 通过SharedPreferences可以将NVP（Name/Value Pair，名称/值对）保存在Android的文件系统中，而且SharedPreferences完全屏蔽对文件系统的操作过程
        + 开发人员仅是通过调用SharedPreferences对NVP进行保存和读取
        + 一般用于程序配置参数，或存储少量的程序数据。
    + 访问模式
        + 私有（MODE_PRIVATE）：仅创建程序有权限对其进行读取或写入;
        + 全局读（MODE_WORLD_READABLE）：允许其他应用程序有读取操作的权限，但没有写入操作的权限;
        + 全局写（MODE_WORLD_WRITEABLE）：其他程序可以对其进行写操作。

1. 对于FileOutputStream对象，关闭前必须调用flush（）方法，为什么？
    + 为了提高文件系统的性能，一般调用write()函数时，如果写入的数据量较小，系统会把数据保存在数据缓冲区中，等数据量累积到一定程度时再一次性的写入文件中，因此在调用close()函数关闭文件前，务必要调用flush()函数，将缓冲区内所有的数据写入文件。

1. **在SD卡上操作文件的步骤**
    + 添加SD卡访问权限

    + 判断SD卡是否正确安装

    + 用File类、FileOutput类或者
    FileInputStream类完成对文件的操作和步骤:
        + 创建File类的对象时，指定文件路径和名称
        + 使用File类的对象来调用前面文件操作方法
        + 读写文件前，创建FileOutputStream或者FileInputStreaml类的对象时关联要读取/写的file类对象。
        + 写文件完成后一定要调用flush()和close()。
        > 參考代码: ExternalFileDemo

1. 读取资源中的原始格式文件的步骤。
    + 原始格式文件可以是任何格式的文件，例如视频格式文件、音频格式文件、图像文件和数据文件等等，在应用程序编译和打包时，/res/raw目录下的所有文件都会保留原有格式不变。
    + /res/xml目录下的XML文件，一般用来保存格式化的数据，在应用程序编译和打包时会将XML文件转换为高效的二进制格式，应用程序运行时会以特殊的方式进行访问。
    + 读取原始格式文件的步骤
        1. 首先需要调用getResources()函数获得资源对象
        1. 然后通过调用资源对象的openRawResource()函数，以二进制流（InputStream  --  FileInputStream的父类）的形式打开指定的原始格式文件:
            + 通过流对象读取文件;
            + 读取文件结束后，调用close()函数关闭文件流.
        1. 将从文件读出来的数据转换为String输出时，应格外注意文件的编码问题，一定要使用与原始格式文件属性中相同的编码(简体中文--GBK)。

1. SQLite数据库的特点
    + SQLite数据库特点（1）
        + SQLite，是一款轻型的数据库;
        + 适用于嵌入式系统，可以嵌入到使用它的应用程序中;
        + 占用资源非常少，运行高效可靠，可移植性好;
        + 提供了零配置（zero-configuration）运行模式;
        + 客户端和服务器在同一个进程内运行，这样能简化管理、减少开销、使应用程序更加易于部署和使用;
    + SQLite数据库特点（2）
        + SQLite 和其他数据库最大的不同就是对数据类型的支持，创建一个表时，可以在 CREATE TABLE 语句中指定某列的数据类型，但是你可以把任何数据类型放入任何列中。SQLite 称之为“弱类型”（manifest typing.）
        + SQLite 不支持一些标准的 SQL 功能，特别是外键约束（FOREIGN KEY constrains），嵌套 transaction 和 RIGHT OUTER JOIN 和 FULL OUTER JOIN, 还有一些 ALTER TABLE （不支持添加多列，但可以一次添加单列/改列名）功能。除了上述功能外，SQLite 是一个完整的 SQL 系统，拥有完整的触发器，事务等，支持视图，子查询。
        + 免费，代码完全开放
1. SQLite数据库遵循的ACID是什么？
    + A:原子性(Atomicity）
    + C:一致性（Consistency）
    + I:隔离性（Isolation—可串行化）
    + D:持久性（Durability）
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
1. Android中实现Tween动画的方式及步骤。
1. Frame动画的具体实现步骤。
1. 动画监听器的概念及实现。
1. 简述从网络架构模式角度，网络编程的分类及其主要内容。
1. 网络编程中，服务器端返回客户端的数据内容格式有哪些？
1. 简述TCP Socket通信时服务器端和客户端的操作步骤。
1. 简述UDP Socket通信时服务器端（接收）和客户端（发送）的操作步骤。
1. HttpURLConnection编程的主要步骤。
1. 简述JSON字符串的解析过程。
1. 调用Web Service的主要步骤。
