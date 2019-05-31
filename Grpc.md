# GRPC系统学习

## RPC结构模型

如果要自己实现一个RPC框架的流程：  
1）首先可以想到要建立两个应用间的跨服务器通信（有两种网络协议HTTP、TCP/IP）；  
2）然后需要知道对方有提供什么服务（接口以及参数），可以使用服务发现框架实现；  
3）如果要想像调用本地接口一样调用远程服务接口，则需要本地实现和远程接口长得一样的一个接口，
接口的实现则通过第一步建立的连接将接口名和参数进行序列化，再添加一些头信息说明这个请求是远程过程调用以及来源等，传输到远程服务器；
4）远程服务器需要判断头信息，将远程过程调用请求体分析一下，反序列化要调用接口的信息，再调用本地接口，然后将结果返回回去。

上面是RPC实现的核心流程，如下图：
![](https://upload-images.jianshu.io/upload_images/1779921-d66a7ff6caad8955.png?imageMogr2/auto-orient/)

不过里面仍然有很多容易忽略的细节的实现  
1）为何要用服务发现？直接约定远程服务地址接口等信息有什么不好？  
易拓展   
2）分布式架构一个服务多个实例如何选择？  
负载均衡  
3）分布式架构很多微服务互相调用都去注册中心查的效率问题？  
设置调用者本地缓存  
4）客户端远程调用服务端接口如果总是同步等待会有什么问题？  
对耗时的请求通过异步调用  
5）服务端接口更新了，老版本的接口还有人在使用，怎么办？ 
版本控制？  
6）服务端应该怎么处理从各个客户端来的大量的远程调用？  
线程池  
7）因服务端或网络异常导致请求无返回怎么处理？  

## GRPC组件

### Protobuf

是序列化结构化的数据的协议，类似xml、yaml等格式。不过protobuf数据
更加紧凑，处理速度更快。

语法：[Language Guide (proto3)](https://developers.google.com/protocol-buffers/docs/proto)  
生成代码的结构和规则：[Java Generated Code](https://developers.google.com/protocol-buffers/docs/reference/java-generated)  
编译生成的类的API：[Protobuf API](https://developers.google.com/protocol-buffers/docs/reference/java/)  
序列化：[Encoding](https://developers.google.com/protocol-buffers/docs/encoding)  
[Protobuf源码](https://github.com/protocolbuffers/protobuf)  

#### Protobuf语法

protobuf定义了一系列对应到各种语言的语法规则。

#### Protobuf使用

[Protobuf Java 入门](https://developers.google.com/protocol-buffers/docs/javatutorial)

1）定义消息格式  

其实相当于定义了一个类，然后通过protoc可以将这个类使用不同的语言实现。

2）使用Protobuf编译器编译  

```
protoc --version    #查看是否安装编译器, macos 默认安装了
```

proto编译.proto文件，生成的代码的格式都是一样的。
里面包含下面几类方法：

+ Getters and Setters

+ Builder

+ 标准Message方法

+ 序列化和反序列化方法

3）使用Protobuf API 读写消息  


### GRPC中的代理模式

### GRPC四种类型服务接口

### GRPC实现同步和异步远程过程调用

### 