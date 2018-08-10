博客地址：https://www.cnblogs.com/yihec/p/9454762.html
基本springboot 2.0版本 spring-cloud的使用
Spring Cloud与Spring Boot版本匹配关系
Spring Cloud Spring Boot
Finchley 兼容Spring Boot 2.0.x，不兼容Spring Boot 1.5.x
Dalston和Edgware 兼容Spring Boot 1.5.x，不兼容Spring Boot 2.0.x
Camden 兼容Spring Boot 1.4.x，也兼容Spring Boot 1.5.x
Brixton 兼容Spring Boot 1.3.x，也兼容Spring Boot 1.4.x
Angel 兼容Spring Boot 1.2.x

从Spring Cloud与Spring Boot版本匹配关系可以看出，如果项目使用的spring boot 版本在 2.0以上，那么必须使用spring cloud 版本 为
Finchley.RELEASE版本 这个版本与以前的D版本有一些区别，下面主要讲的就是spring cloud F版本的简单使用
我这里也自己搭了一套关于spring-cloud的基本使用的demo，下面我来详细介绍下该项目
其中gitHub地址为:https://github.com/yihec/springCloud

首先介绍下各个module的用途：
       1.首先是eureka-server 这是一个服务注册中心,所有的服务都会在此上面管理,在Application上增加@EnableEurekaServer注解即可, Eureka服务端可以部署成为高可用，每一个服务器都会复制注册的服务状态到其他服务器，然后打开网址http://localhost:1001/可以看到各个注册的服务信息

      
 2.config-server 配置中心,将各个服务配置保存在git,用于统一话管理配置文件,在 Application 上增加@EnableConfigServer注解即可。

      3.server-1 和 server-2  这两个都是服务生产者,里面除了本地服务外，另外在server-1中使用Feign 调用了server-2的接口，Feign封装了HTTP调用服务方法，使得客户端像调用本地方法那样直接调用方法，类似Dubbo中暴露远程服务的方式，区别在于Dubbo是基于私有二进制协议，而Feign本质上还是个HTTP客户端。 另外还使用了一个Spring Cloud 断路器 Hystrix， 以下是关于Hystrix的详细介绍：
          <1>服务降级: 
                  在调用接口的时候，可以实现一个fallback方法, 当请求后端服务出现异常的时候, 可以使用fallback 
                  方法返回的值.fallback方法的返回值一般是设置的默认值或者来自缓存.
          <2>断路器:
                  当Hystrix Command请求后端服务失败数量超过一定比例(默认50%), 断路器会切换到开路状态(Open). 
                  这时所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 
                  自动切换到半开路状态(HALF-OPEN). 这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路  
                  状态(CLOSED),否则重新切换到开路状态(OPEN). 即有自我检测并恢复的能力.
          <3> 资源隔离:
                  在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线 
                  程池. 例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要
                  优点是运行环境被隔离开了. 这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗  
                  尽时, 不会对系统的其他服务造成影响.  但是带来的代价就是维护多个线程池会对系统带来额外的性能开
                  销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 可以使用Hystrix的信号 
                  模式(Semaphores)来隔离资源.
     
     另外当服务越来越多的时候，这时候就需要用到服务链路追踪(Spring Cloud Sleuth)
     在spring Cloud为F版本的时候，已经不需要自己构建Zipkin Server了，只需要下载jar即可，下载地址：
     https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/ 然后使用java  -jar启动，
     再打开http://localhost:9411/的界面，我在server-1 中调用了server-2的接口，然后点击依赖分析，可以看到详细的调用情况


     
 4：service-zuul   路由网关(zuul)服务，zuul是微服务架构的不可或缺的一部分，提供动态路由，监控，安全，权限认证等服务。可以在这里面进行统一的比如异常处理，权限认证等等。其实跟spring中的uri过滤器有点像，首先所有的请求第一时间经过这里，然后通过过滤处理，跳转不同的页面
5：hystrix-dashboard    断路器监控(Hystrix Dashboard)  在微服务架构中为例保证程序的可用性，防止程序出错导致网络阻塞，出现了断路器模型。断路器的状况反应了一个程序的可用性和健壮性，它是一个重要指标。Hystrix Dashboard是作为断路器状态的一个组件，提供了数据监控和友好的图形化界面。
启动server-1项目，输入网址http://localhost:2001/hystrix，


  在SpringBoot 2.0版本中会出现Unable to connect to Command Metric Stream.异常信息，此时需要配置servlet




然后在界面依次输入：http://localhost:2001/hystrix.stream 、2000 、aa(标题，随便取)；点确定。然后调用接口输入网址 http://localhost:2001/HelloServer2 ，会出现下面页面，这个就是服务调用错误的提示

最后启动server-2，接口调用成功

以上就是关于spring-cloud的一些简单使用
参考资料：https://blog.csdn.net/forezp/article/details/70148833/ 
                  https://gitee.com/didispace/SpringCloud-Learning
