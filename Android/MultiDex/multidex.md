
《Android开发艺术探索 Chapter13 综合技术》
1. MultiDex



《集成MultiDex项目实战》
http://www.imooc.com/learn/876

课程大纲：

原理：
	1.*.apk（Android Package Android安装包）
		文件内容（AndroidManifest.xml配置信息、META-INF 签名信息、class.dex Java字节码文件、res资源文件、resources.arsc二进制资源文件）
	2.*.dex（Dalvik VM executes Android Dalvik虚拟机执行程序）
		获取方式（1.apk解压 2.apk反编译 apktool d /location -o /outputLocation  ; apktool b /location）
	3.编译打包
	4.dvm(Android Dalvik 虚拟机)
		JIT编译
		应用安装时执行dexopt命令，将dex文件翻译为odex文件
		应用运行时，有将二进制翻译为机器码的流程
	  art (Android Runtime Android4.4引入)
		AOT编译
		应用安装时执行dex2oat命令，将dex文件翻译为oat文件
		应用运行时，无将二进制翻译为机器码的流程（安装时已经执行过）
实践：
	1.gradle集成MultiDex
		环境：
			Android Studio 2.1+ Gradle 2.10
			Android SDK Build Tools 21.1及以上
			使用镜像地址（AS-Settings-HTTP Proxy-Manual / SDK Manager设置）
	2.拆包与代码混淆
	3.单个dex文件进行设置
	4.使用multiDexKeepProguard
分析：
	1.编译加速
	2.性能问题
	3.兼容性