《新一代构建工具gradle》
https://www.imooc.com/learn/833

##Gradle的使用流程：新建项目-->编码-->测试-->打包-->发布
	1. 新建工程
		build.gradle
	2. 编码
		依赖管理
	3. 测试
		自动执行
	4. 打包
		插件
	5. 发布
		插件

###构建脚本概要

#####构建块
	两个基本概念
	1. 项目(Project)
	2. 任务(task)
每个构建至少包含一个项目，项目中包含一个或多个任务。
在多项目构建中，一个项目可以依赖于其他项目；类似的，任务可以形成一个依赖关系图来确保他们的执行顺序。
	
	项目（Project）
	- 一个项目代表一个正在构建的组件（比如一个jar文件），当构建启动后，Gradle会基于build.gradle实例化一个`org.gradle.api.Project`类，并且能够通过project变量使其隐式可用。
	
	- 属性： group、name、version（可以唯一确定一个组件/jar包）
	- 方法：
	apply（应用一个插件）、
	dependencies（申明依赖哪些jar或者其他项目）、
	repositories（申明去哪个仓库寻找依赖的jar）、
	task（申明项目中的任务）
	- 属性的其他配置方式：
	ext、
	gradle.properties
	
	任务（task）
	- 任务对应org.gradle.api.Task。主要包括任务动作和任务依赖。任务动作定义了一个最小的工作单元。可以定义依赖于其他任务、动作序列和执行条件。
	- 方法:
	dependsOn（申明任务依赖）
	doFirst、doLast<<（在动作列表的位置）
	
###自定义任务
		def createDir = {
			paths ->
				File dir = new File(paths);
				if(!dir.exists()){
					dir.mkdirs();
				}
		}

		task makeJavaDir(){
			def paths = ['src/main/java','src/main/resources','src/test/java','src/test/resources'];
			doFirst{
				paths.foreach(createDir);//调用闭包
			}
		}

		task makeWebDir(){
			dependsOn 'makeJavaDir' //任务依赖
			def paths = ['src/main/web','src/test/web'];
			doLast{
				paths.foreach(createDir);//调用闭包
			}
		}
	
###构建生命周期
	- 初始化
		初始化需要构建哪些项目
	- 配置
		task loadVersion{
			project.version='1.0'
		}
	- 执行
		task loadVersion <<{
			print'success'
		}
	- Hook
	
###依赖管理
	几乎所有的基于JVM的软件项目都需要依赖外部类库来重用现有的功能。
	自动化的以来管理可以明确依赖的版本，可以解决因传递性依赖带来的版本冲突。
	
	- 工件坐标
		工件：比如一个jar包
		坐标：group、name、version，在仓库中唯一确定一个jar包
		
	- 常用仓库
		mavenLocal        //本机使用过的jar
		mavenCentral      //公共
		jcenter           //公共
		自定义maven仓库   //maven私服
			maven{
				url ''
			}
		文件仓库
		
	-依赖的传递性
		依赖冲突的原因
		编译时依赖
		运行时依赖
		
###解决版本冲突
	- 修改默认解决策略
		configurations.all{
			resolutionStrategy{
				failOnVersionConflict()	
			}
		}
	- 排除传递性依赖
		compile( 'org.hibernate:hibernate-core:3.6.3.Final' ){
			exclude group:"org.slf4j" ,module:"slf4j-api"
			//transitive = false
		}
	- 强制指定一个（最高）版本
		configurations.all{
			resolutionStrategy{
				force 'org.slf4j:slf4j-api:1.7.24'
			}
		}
	https://www.imooc.com/video/14793
	
###多项目构建介绍
	####项目模块化
	在企业项目中，包层析和类关系比较复杂，把代码拆分成模块通常是最佳实践，这需要清晰的划分功能的边界，比如把业务逻辑和数据持久化拆分开来。
	项目符合高内聚低耦合时，模块化就变得很容易，这是一条非常好的软件开发实践。
	
	- 配置要求
		所有项目应用Java插件
		web子项目打包成WAR
		所有项目添加logback日志功能
		统一配置公共属性
		
	- 项目范围
		allprojects
			root
		subprojects
			:model
			:repository
			:web
			
		子项目依赖：compile project(":repository")
				
	####多项目构建实战
	- settings.gradle
		配置主项目和子项目
		rootProject.name = 'todo'
		include 'repository'
		include 'model'
		include 'web'
	- 所有项目应用Java插件
		在root的build.gradle中:
		allprojects{
			apply plugin:'java'
			sourceCompatibility = 1.8
		}
		
	- web子项目打包成WAR
		apply plugin:'war'
		
	- 所有项目添加logback日志功能
		在root的build.gradle中:
		subprojects/allprojects{
			repositories{}
			dependencies{}
		}
		
	- 统一配置公共属性(group,version)
		在root的gradle.properties中:
		group ='com.hhl.gradle'
		version = 1.0-snapshot
	
###自动化测试
	一些开源的测试框架比如JUnit,TestNG能够帮助编写可复用的结构化的测试，为了运行这些测试，要先编译它们，就像编译源代码一样。
	测试代码的作用仅仅用于测试的情况，不应该被发布到生产环境中，需要把源代码和测试代码分开来。
	
	assert
	
	- 测试发现
	1. 任何继承自junit.framework.TestCase或groovy.util.GroovyTestCase的类
	2. 任何被@Runwith注解的类
	3. 任何至少包含一个被@Test注解的类

	###发布
	发布到本地和远程仓库
		apply plugin:'maven-publish'
		publishing{
			publications{
				//一个叫做myPublish的发布包
				myPublish（MavenPublication）{
					from components.java
				}
			}
			repositories{
				maven{
					name "myRepo"
					url ""//私服地址；上传maven需要提工单，等待审批
				}
			}
		}
		
	 1. 配置maven-publish插件
	 2. 配置需要发布的包、发布的地址
	 3. 执行发布任务