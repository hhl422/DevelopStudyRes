//构建脚本中默认都有个Project实例

//apply是一个方法，plugin: 'java'是一个赋值参数
apply plugin: 'java'

//version是这个Project实例的一个参数
version = '0.1'

//repositories也是一个方法，{jcenter()}是一个闭包，作为参数调用repositories方法（闭包内容见features.groovy）
repositories {
    jcenter()
}

//同上
repositories {
    compile 'com.android.support:appcompat-v7:26.+'
}


