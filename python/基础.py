#1.7循环
for i in range(0,100):
	print("Item{0} {1}".format(i,hello python))  #format 传入参数 拼接字符串

#1.8定义函数
def sayhello():
	print("hello world")
def max(a,b):
	if a>b:
		return a
	else:
		return b
sayhello()
print(max(2,3))

#1.9 面向对象
class hello:									#定义一个类	
	def __init__(self,name):					#定义类的一个属性
		self._name=name
	def sayhello(self):							#定义类的一个方法
		print("hello {0}".format(self._name))

class hi(hello):								#继承自hello的类
	def__init__(self,name):
		hello.__init__(self,name)
	def sayhi(self):
		print("hi {0}".format(self._name))