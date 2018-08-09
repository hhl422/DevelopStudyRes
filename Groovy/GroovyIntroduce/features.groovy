//1 可选的类型定义
def version = 1;

//2 assert
assert version == 2 //异常

//3 括号可选
println(version) //1
println version //1

//4 字符串
//仅字符串
def s1 = 'imooc'
//可以插入变量
def s2 = "gradle version is ${version}"
//可以换行（和python一样）
def s3 = '''my
name is
imooc'''

println s1
println s2
println s3

//5 集合API
//list
def builtTools = ['ant','maven']
builtTools << 'gradle' // 添加内容
assert builtTools.getClass() == ArrayList
assert builtTools.size() == 3
//map
def builtYears = ['ant':2000,'maven':2004]
builtYears.gradle = 2009
println builtYears.ant
println builtYears['gardle']
println builtYears.gteClass()  //class java.util.LinkedHashMap

//6 闭包
def c1 = {
    v ->
        println v
}

def c2 = {
    println 'hello'
}

def method1(Closure closure){
    closure('param')
}

def method2(Closure closure){
    closure()
}

//将闭包作为参数传递给方法
method1(c1); //param
method2(c2); //hello
