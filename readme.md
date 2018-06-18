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
        + 引用自第三方资料
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

1. **对于FileOutputStream对象，关闭前必须调用flush（）方法，为什么？**
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

1. **读取资源中的原始格式文件的步骤。**
    + 原始格式文件可以是任何格式的文件，例如视频格式文件、音频格式文件、图像文件和数据文件等等，在应用程序编译和打包时，/res/raw目录下的所有文件都会保留原有格式不变。
    + /res/xml目录下的XML文件，一般用来保存格式化的数据，在应用程序编译和打包时会将XML文件转换为高效的二进制格式，应用程序运行时会以特殊的方式进行访问。
    + 读取原始格式文件的步骤
        1. 首先需要调用getResources()函数获得资源对象
        1. 然后通过调用资源对象的openRawResource()函数，以二进制流（InputStream  --  FileInputStream的父类）的形式打开指定的原始格式文件:
            + 通过流对象读取文件;
            + 读取文件结束后，调用close()函数关闭文件流.
        1. 将从文件读出来的数据转换为String输出时，应格外注意文件的编码问题，一定要使用与原始格式文件属性中相同的编码(简体中文--GBK)。

1. **SQLite数据库的特点**
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
1. **SQLite数据库遵循的ACID是什么？**
    + A:原子性(Atomicity）
    + C:一致性（Consistency）
    + I:隔离性（Isolation—可串行化）
    + D:持久性（Durability）
1. **简述SQLiteOpenHelper类的作用及使用方法**
    + 作用
        + 用于对数据库版本进行管理，该类是一个抽象类，必须继承它才能使用。
        + SQLiteOpenHelper 类根据开发应用程序的需要，封装了创建和更新数据库使用的逻辑，至少需要实现三个方法：
            + 构造函数: 这个方法需要四个参数--上下文环境，数据库名字，一个可选的游标工厂（通常是 null），正在使用的数据库的版本。
                > 数据库文件固定位置：/data/data/包名/databases/
            + onCreate()方法，它需要一个 SQLiteDatabase 对象作为参数，根据需要对这个对象创建表和初始化数据。
            + onUpgrade() 方法，它需要三个参数，一个 SQLiteDatabase 对象，一个旧的版本号和一个新的版本号，通过这三个参数就可以把一个数据库从旧的模型转变到新的模型。----实际上,只要SQLiteOpenHelper的构造函数中的版本与数据库文件版本不同就会回调该方法。

    + 使用方法:
        + 当调用SQLiteOpenHelper的getWritableDatabase()或者getReadableDatabase()方法获取用于操作数据库的SQLiteDatabase实例的时候，如果打开数据库时不存在，Android系统会自动生成一个数据库，接着调用onCreate()方法，onCreate()方法在初次生成数据库时才会被调用，在onCreate()方法里可以生成数据库表结构及添加一些初始化数据。

        + 一般情况下getReadableDatabase()与getWritableDatabase()返回的都是可读写的数据库对象，只有在数据库仅开放只读权限或磁盘已满时getReadableDatabase()方法才会返回一个只读的数据库对象。
        + onUpgrade()方法在数据库的版本发生变化时会被调用，数据库的版本是由程序员控制的，假设数据库现在的版本是1，由于业务的需要，修改了数据库表的结构，这时候就需要升级软件，升级软件时希望更新用户手机里的数据库表结构，为了实现这一目的，可以把新的数据库版本设置为2，并且在onUpgrade()方法里面实现表结构的更新。当系统在构造SQLiteOpenHelper类的对象时，如果发现版本号不一样（比较构造函数的参数中的版本和数据库文件版本），就会自动调用onUpgrade函数，在这里对数据库进行升级----改现有表为临时表，创建新表，复制/修改数据，删除临时表（注意执行效率问题）。
    + SQLiteDataBase对象
        + 使用专用方法（SQLiteDatabase对象的insert、delete、update和query方法）操作数据库更加简洁、易用，而且不需要了解太多的SQL语法—专为不太熟悉SQL语言的程序员准备。专用方法需要使用ContentValues对象。ContentValues对象是一个数据承载容器，类似于MAP，它提供了存取数据对应的put(String key, xxxx value)和getAsxxxx(String key)方法，key为字段名称，value为字段值，xxxx指的是各种常用的数据类型， 如：String、Integer等

        + insert方法（long insert (String table, String nullColumnHack, ContentValues values) )
            + 首先构造一个ContentValues对象，然后调用ContentValues对象的put()方法，将每个属性的值写入到ContentValues对象中，最后使用SQLiteDatabase对象的insert()方法，将ContentValues对象中的数据写入指定的数据库表中。
            + insert()方法的返回值是新数据插入的位置，若返回-1则表示插入失败。
            + 对于表中自增的字段，不需要在ContentValues对象中设置它的值。
            + 如果第三个参数values 不为null并且其中元素的个数大于0 (否则插入会出错)，可以把第二个参数(空值列名)设置为null，以保证后台生成的SQL语句的values部分不是空，避免语法错误。
        + query方法（Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy , String limit)  ）
            + query()方法实际上是把select语句拆分成了若干个组成部分，然后作为方法的输入参数。
            + 不用的参数（条件）应设置为null；
            + 查询条件子句selection中允许使用占位符，其对应的字符串在selectionArgs数组中一一对应设置；
            + limit指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分
        + update方法（ int update (String table, ContentValues values, String whereClause, String[] whereArgs) ）
            + 首先构造一个ContentValues对象，然后调用ContentValues对象的put()方法，将要修改的属性的值写入到ContentValues对象中，最后使用SQLiteDatabase对象的update()方法，将ContentValues对象中的数据写入指定的数据库表中。
            + update()方法的返回值是修改数据的行数。
            + whereClause是更新条件，可以使用占位符，占位符与whereArgs数组中的字符串一一对应。
        + delete方法（ int delete (String table, String whereClause, String[] whereArgs) ）
            + delete()方法的返回值是删除的行数。
            + whereClause是更新条件，可以使用占位符，占位符与whereArgs数组中的字符串一一对应。
            + 如果后2个参数为null，则删除所有数据。
        > 示例代码:SQLiteDemoSecond
1. **Android系统中SQLite事务及其使用方法。**
    + 事务定义了一组SQL命令的边界，这组命令或者作为一个整体被全部执行，或者都不执行。例如：转帐操作。
    + 在Android平台上，数据库操作被意外中止的情况会频繁出现:  Android系统会杀死apps/threads/activities 中断数据库的使用，电池电量会耗尽或被移除等，所以，使用数据库事务至关重要。
    + 使用SQLiteDatabase的beginTransaction()方法可以开启一个事务，程序执行到endTransaction() 方法时会检查事务的标志是否为成功，如果为成功则提交事务，否则回滚事务。
    + 事务成功设置: 当应用需要提交事务，必须在程序执行到endTransaction()方法之前使用setTransactionSuccessful() 方法设置事务的标志为成功；如果不调用该方法，默认会回滚事务。示例: SQLiteDemoThird

1. **ContentProvider的概念及作用。**
    + ContentProvider概念
        + ContentProvider（数据提供者）是在应用程序间共享数据的一种接口机制。
        + ContentProvider提供了高级的数据共享方法，应用程序可以指定需要共享的数据，而其他应用程序则可以在不知数据来源、路径的情况下，对共享数据进行查询、添加、删除和更新（CRUD）等完全一致的操作。
        + 许多Android系统的内置数据也通过ContentProvider提供给用户使用，例如通讯录、音视频文件和图像文件等。
        + 程序开发人员使用ContentResolver对象与ContentProvider进行交互，而ContentResolver则通过URI确定需要访问的ContentProvider的数据集（对应数据库、文件或网络等中的数据）。
        + 在发起一个请求的过程中，Android首先根据URI确定处理这个查询的ContentProvider，然后初始化ContentProvider所有需要的资源，这个初始化的工作是Android系统完成的，无需程序开发人员参与。
        + 一般情况下针对特定的数据只有一个ContentProvider对象，但却可以同时与多个需要使用该数据的ContentResolver进行交互，从这个角度讲：
            > ContentProvider对象是服务对象，类似于“网站”的功能，而URI类似于“网址”，客户端使用的ContentResolver 对象则类似于“浏览器”

1. **URI的构成及作用**
    + URI是通用资源标志符（Uniform Resource Identifier），用来定位任何远程或本地的可用资源。
    + 一个Uri由以下几部分组成：
        + scheme：ContentProvider（内容提供者）的scheme已经由Android所规定为：content://
        + 主机名（或Authority）：用于唯一标识这个ContentProvider，外部调用者可以根据这个标识来找到它，一般用包名+ContentProvider的类名来表示
        + 路径（path）：可以用来表示我们要操作的数据，路径的构建应根据业务而定，具体示例如下：

1. **创建ContentProvider的步骤。**
    + 程序开发人员通过继承ContentProvider类可以创建一个新的数据提供者，过程可以分为三步：
        1. 声明CONTENT_URI，实现UriMatcher
        1. 继承ContentProvider，并重载六个函数
            + delete():删除数据
            + insert()：添加数据
            + qurey()：查询数据
            + update()：更新数据
            + onCreate()：初始化底层数据和建立数据连接等工作
            + getType()：返回指定URI的MIME数据类型，

        1. 在Manifest文件中注册ContentProvider
    + 为了写代码方便，一般需要定义一个常量构成的类，专门用于存放URI、MIME类型、数据集（表）的构成字段名称等。
    > 示例代码：ContentProviderDatabaseDemo
1. 什么是位置服务，相关的类有哪些？
    + 位置服务（Location-Based Services，LBS），又称定位服务或基于位置的服务，融合了GPS定位、移动通信、导航等多种技术，提供了与空间位置相关的综合应用服务

    + 提供位置服务，首先需要获得LocationManager对象，使用LocationManager对象的isProviderEnabled(provider)方法来检测定位设备是否已经启用

1. 如何实现追踪定位？
    + 通过设置监听器，来监听位置变化。可以根据位置的距离变化(关键设置)和时间间隔设定产生位置改变事件的条件，这样可以避免因微小的距离变化而产生大量的位置改变事件。
    > + LocationManager提供了一种便捷、高效的位置监视方法requestLocationUpdates()： LocationManager中设定监听位置变化的代码如下：
    > + locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    > + 第1个参数是定位的方法，GPS定位或网络定位(字符串)
    > + 第2个参数是产生位置改变事件的时间间隔，单位为毫秒
    > + 第3个参数是距离条件，单位是米---关键设置
    > + 第4个参数是回调函数，在满足条件后的位置改变事件的处理函数

    <pre>
    LocationListener locationListener = new LocationListener(){
        public void onLocationChanged(Location location) {
        } //在设备的位置改变时被调用
        public void onProviderDisabled(String provider) {
        }//在用户禁用具有定位功能的硬件时被调用
        public void onProviderEnabled(String provider) {
        }//在用户启用具有定位功能的硬件时被调用
        public void onStatusChanged(String provider, int status, Bundle extras) {
         /*在提供定位功能的硬件的状态改变时被调用，如从不可获取位置信息状态到可以获取位置信息的状态，反之亦然*/
        }
    };</pre>
1. **如何实现敏感区域设置？**
    + 当用户的手机靠近事先设定的固定点时,系统可以检测到,并触发相应的处理。
    + 具体步骤
        + 使用LocationManager对象的addProximityAlert()方法添加一个临近警告，具体参数如下：
            + Latitude: 固定点的纬度
            + Longitude：固定点的经度
            + Radius：敏感区的半径
            + Expiration：警告失效期（ms），-1表示永不失效
            + PendingIntent(满足一定条件才执行的Intent):内部包括警告触发的intent对象--经Android系统分析确定接收者（如: 广播接收器，Activity或Service，不同的接收者产生PendingIntent的方式不同）

    + 编写接收响应上述Intent对象的广播接收器

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
