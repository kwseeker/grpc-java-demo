## gRPC 使用

gRPC Java API => https://grpc.io/grpc-java/javadoc/  
gRPC官方文档中文版 => https://doc.oschina.net/grpc  
#### 一个完整的RPC过程
网上找到个图片挺完整的
![](http://www.10tiao.com/img.do?url=https%3A//mmbiz.qpic.cn/mmbiz_png/YriaiaJPb26VPdtSZXibkQBDXICiclL3xQMgdFABiaggySI6t3DlEfUFnEp1MdniaibnkhgIHar1uya10AGHbLvT45u2g/640%3Fwx_fmt%3Dpng)
#### gRPC的工作原理

参考文章：  
gRPC客户端创建和调用原理解析  
http://www.10tiao.com/html/46/201803/2651001766/1.html  

gRPC支持多路复用；  
gRPC使用HTTP/2作为网络传输协议，其他大部分RPC框架都是使用TCP协议；  
gRPC支持同步和异步调用；  
```
Stub    //异步
BlockingStub    //同步
FutureStub      //
```
gRPC支持认证访问控制；

##### 核心类

+ BindableService  
    提供了一种绑定实例到服务器的方法。proto文件生成的 xxxGrpc 类中的内部抽象类 xxxImplBase(服务类接口)实现此接口，通过实现 bindService()方法，
    绑定服务到服务器（主要是将实现的服务方法绑定进去）。
    
+ StreamObserver  
    应答观察者接口，用于从消息流接收提醒
 
+ Sever 和 ServerBuilder 、 ServerProvider  
    Server是gRPC服务器类的接口， 提供start()，shutdown()，getPort()，getServices() 及判断服务器状态等方法；    
    SeverBuilder是用于创建Sever实例的抽象类，通过build()方法创建Server类实例；  
        gRPC提供了两种实现：InProcessServerBuilder 和 NettyServerBuilder；  
    ServerProvider可以通过端口创建ServerBuilder类实例；  
        gRPC提供了一种实现：NettyServerProvider。

+ ManagedChannel 和 ManagedChannelBuilder 、 ManagedChannelProvider  
    ManagedChannel 提供生命周期管理  
    ManagedChannelBuilder   
    ManagedChannelProvider  

#### gRPC常用的名词
+ 截止时间
+ PRC终止
+ 取消RPC
+ 元数据集 ？
+ 流控制
+ 配置
+ 频道

#### 编码流程
1. 在proto中定义一个服务， 指定其可以被远程调用的方法及其参数和返回类型。  
    四类服务（针对输入和返回数据一对一，一对多，多对一，多对多区分）：
    + 单项RPC（输入一个数据返回一个数据，如 routeguide 这个实例，给一个经纬度点的数据，要求查出这个点对应的那个地址）
    + 服务端流式RPC（输入一个数据返回一串数据，如 routeguide 这个实例，给一个经纬度矩形数据，要求查出所有这个范围内的地址）
    + 客户端流式RPC（输入一组数据返回一个数据，如 routeguide 这个实例，给一个经纬度链表数据，要求查出一次经过这十个点走过的路程）
    + 双向流式RPC（输入一串数据，返回一串数据，如 routeguide 这个实例，客户端发送四条，从服务接收四条数据）
    客户端流式RPC和双向流式RPC只能是异步的。
    1) 定义服务类；
        ```
        option java_package = "io.grpc.examples";
        service RouteGuide {
            //单项RPC
            rpc GetFeature(Point) returns (Feature) {}
            //服务端流式RPC
            rpc ListFeatures(Rectangle) returns (stream Feature) {}
            //客户端流式RPC
            rpc RecordRoute(stream Point) returns (RouteSummary) {}
            //双向流式RPC
            rpc RouteChat(stream RouteNote) returns (stream RouteNote) {}
        }
        ```
    2) 方法request及response的类型；
        ```
        message Point {
            int32 latitude = 1;
            int32 longitude = 2;
        }
        ```
2. 使用protocol buffer编译插件从proto文件生成客户端和服务端代码；
3. 服务侧，服务端实现服务接口，运行一个gRPC服务器来处理客户端调用；
    gRPC 底层架构会解码传入的请求，执行服务方法，编码服务应答。
    1) 
    2) 
    3) 
4. 客户端，客户端有一个存根实现了服务端同样的方法；
    客户端可以在本地存根调用这些方法，用合适的 protocol buffer 消息类型封装这些参数，
    gRPC 来负责发送请求给服务端并返回服务端 protocol buffer 响应。
    1) 
    2) 
    3)




