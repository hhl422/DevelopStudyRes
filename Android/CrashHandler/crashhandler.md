
《Android代码混淆与加固技术》
http://www.imooc.com/learn/879

《Android开发艺术探索 Chapter13 综合技术》
1. CrashHandler获取应用的crash信息
    a. 自定义异常处理器CrashHandler（继承UncaughtExceptionHandler）
    b. 在Application的onCreate方法中init自定义的异常处理器CrashHandler
    c. 程序抛出未捕获的异常时，会由CrashHandler中的uncaughtException方法进行处理
