《新一代构建工具gradle》
https://www.imooc.com/learn/833

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
	
	