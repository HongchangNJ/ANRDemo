# ANRDemo
添加了一些ANR学习记录
ANR
1.机制、 产生原理、情形
频繁GC
线程阻塞
死锁
2. 如何去分析
确认类型
事件
服务
广播

3. /data/anr/trace.txt
data/system/dropbox 中的anr信息 cpu使用信息

4. （1.） anr发生时间
（2）看时间节点CPU使用情况 ----造成时间节点拉长的而原因，要么锁了，要么GC了。 STW： stop the world
（3）先找到主线程，看状态，比如 ： thread_wait， thread_zombie， timed wait， thead_monitor， block（死锁）等状态
thread_native: native线程： 本地JNI线程，或者IO操作的linux函数调用， 用了AQS的处理:LockSupport.park（wait状态）
总结：先分析（主）线程状态

线程状态、故障点、时间点
mainlog文件协助分析：程序所在进程打印的log adb logcat -v time -b main > mainlog.log，看事故发生前后发生了什么。（根据时间点确认前后位置）


5. 崩溃的日志data/system/dropbox 中的anr信息相对比较完整 data_app_anr@14758373732737.txt CPU的使用信息比较全面。
总CPU的利用率等
CPU内核使用率高，一般是IO操作导致


看内存使用。看GC： GC的情况，用了多少，释放(回收)了多少，复制总时间等。累计移动字节数，移动对象
并发总复制时间，GC的次数是否正常，总的GC时间是否正常。每次回收的平均字节率，复制活动字节


CPU状况：
1. 其他进程使用高，自己进程低
2. 如果当前进程CPU占用高：80%以上，本进程代码不合理消耗了CPU资源），结合ANR日志和mainlog 进一步分析
3. CPU占用不高 （或者IO频繁，死循环，上锁等）

Service:基本上是发生在onCreate里面
事件： 5s内事件没有被消费，就迎来了下一个事件



监控：
1. 监控 data/anr目录 FileObserver(问题点：有时没有权限)
ANRFileObserver observer = new ANRFileObserver("/data/anr/trace.txt")

日志收集： Looper.getMainLooper().getThread().getStackTrace()



问题：bugly怎么监控ANR的？


