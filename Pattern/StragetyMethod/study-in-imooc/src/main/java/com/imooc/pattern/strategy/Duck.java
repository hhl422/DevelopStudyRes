package com.imooc.pattern.strategy;

/*
 * 超类，所有的鸭子都要继承此类
 * 抽象了鸭子的行为：显示和鸣叫
 */
public abstract class Duck {
  
	/*
	 * 鸭子发出叫声
	 * 通用行为，由超类实现
	 */
	public void quack(){
		System.out.println("嘎嘎嘎");
	}
	
	/*
	 * 显示鸭子的外观
	 * 鸭子的外观各不相同，声明为abstract， 由子类实现
	 */
	public abstract void display();
	
	//将飞行行为抽象为借口，在父类中持有该接口，并由该接口代理飞行行为
	private FlyingStragety flyingStragety;

	public void setFlyingStragety(FlyingStragety flyingStragety) {
		this.flyingStragety = flyingStragety;
	}
	
	public void fly(){
		flyingStragety.performFly();
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
