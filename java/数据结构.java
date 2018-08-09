###逻辑结构类型：集合 （无序 不重复）  implements Set
###数据对象集：集合只存在元素（无序），不存在关系
1.顺序存储（数组）
2.链式存储（链表）
	a.存储结点实现：
	public class Node {
		Object elements; //数值域
		Node next;       //指针域
		//构造函数
		public Node(Node nt) {
			next=nt;
		}
		public Node(Object obj, Node nt) {
			elements=obj;
			next=nt;
		}
	}
	b.链表操作方法：
	（此处省略为重写的接口抽象方法）
	public class LinkSet<E> implements Set<E> {
		private Node head;  //表头指针
		private int len;    //链表长度

		public LinkSet() {  //无参构造函数：初始化集合为空
			len=0;
			head=new Node(null);
		}
		

		@Override
		public boolean add(E e) {
			Node p=head;
			while(p.next!=null) {
				if(p.next.elements.equals(e)) return false;
				else p=p.next;
			}
			p.next=new Node(e,null); //循环正常结束时，集合内无重复值，则将待插入元素插入链表最后
			len++;
			return true;
		}
		
		@Override
		public boolean remove(Object o) {
			Node p=head;
			while(p.next!=null){          //循环遍历链表，找到待删除元素
				if(p.next.elements.equals(o)) break;
				else p=p.next;
			}
			if(p.next!=null) {      //若非空退出上面循环，说明找到待删元素，接下来进行删除操作
				p.next=p.next.next;
				len--;
				return true;
			}
			else 
				return false;
		}

		@Override
		public boolean contains(Object o) {
			Node p=head.next;
			while(p!=null) {
				if(p.elements.equals(o)) return true;
				else p=p.next;
			}
			return false;
		}
		
		//返回集合中第i个元素的值(下标从1开始)
		public Object value(int i) {
			if(i<=0||i>len) {
				System.out.println("参数i有误！");
				System.exit(1);
			}
			int c=1;
			Node p=head.next;
			while(p!=null) {
				if(c==i) break;
				else {
					c++;
					p=p.next;
				}
			}
			return p.elements;
		}
		
		//从集合中按obj值查找元素
		public Object find(Object o) {
			Node p=head.next;
			while(p!=null) {
				if(p.elements.equals(o))
					return p.elements;
				else
					p=p.next;
			}
			return null;
		}
		
		@Override
		public int size() {
			return len;
		}
		
		@Override
		public boolean isEmpty() {
			return len==0;
		}
		
		public void output(){
			Node p=head.next;
			while(p!=null) {
				System.out.println(p.elements.toString());
				p=p.next;
			}
			System.out.println();
		}
		
		@Override
		public void clear() {
			len=0;
			head.next=null;
		}
	}

3.HashSet(Java自带的实现类)常用方法：
a.操作元素
	.add(Object element):在set集合的尾部添加指定元素
	.remove(Object element):如果集合中存在指定元素，移除之
	.clear():从集合移除所有元素
b.判断元素
	.isEmpty():判断集合中是否有元素，没有返回true,有返回false
	.contains(Object element):判断集合是否包含指定元素，包含返回true,否则返回false
c.其他
	.iterator():返回迭代器对象，迭代器用于遍历集合
	.size():返回集合中的元素数



###类型名：线性表  （有序 可重复） implements Link
###数据对象集：线性表是n个元素构成的有序序列,每个结点含有数据域和指针域两个部分，List只需记录头结点位置
1.顺序存储（数组）——ArrayList(Java自带的实现类)
2.链式存储（链表）——LinkedList(Java自带的实现类)
	
3.ArrayList/LinkedList(Java自带的实现类)常用方法
ArrayList是实现了基于动态数组的数据结构;LinkedList基于链表的数据结构.
ArrayList访问元素速度优于LinkedList,LinkedList占用的空间比较大,但LinkedList在批量插入或删除数据时优于ArrayList.
两者都继承自List接口，内部实现不同，但对外的方法一致，常用方法如下：
a.操作元素
	get(int index):返回线性表中指定位置的元素
	set(int index,Object element):用指定元素替换线性表中指定位置的元素
	.add(Object element):在线性表的尾部添加指定元素
	add（int index,Object element）:在线性表指定位置插入指定元素
	.remove(Object element):如果线性表中存在指定元素，移除之
	remove(int index)：移除线性表中指定位置元素
	.clear():从线性表移除所有元素
b.判断元素
	.isEmpty():判断线性表中是否有元素，没有返回true,有返回false
	.contains(Object element):判断线性表是否包含指定元素，包含返回true,否则返回false

c.查询元素
	indexOf(Object o):从前往后查找线性表中的元素，返回第一次出现指定元素的索引，如果不存在，则返回-1
	lastindexOf(Object o):从后往前查找线性表中的元素，返回第一次出现指定元素的索引，如果不存在，则返回-1

d.其他
	.iterator():返回迭代器对象，迭代器用于遍历集合
	.size():返回线性表中的元素数
	subList(int fromindex,int toindex):返回List中指定的fromindex(包含)和toindex(不包含)之间的元素集合，返回List类型


###类型名：堆栈  （后进先出） extends Vector
###数据对象集：长度为MaxSize的堆栈S，堆栈元素item
1.顺序存储（数组）
/**
 * 基于数组实现的顺序栈
 * @param <E>
 */
public class Stack<E> {
    private Object[] data = null;
    private int maxSize=0;   //栈容量
    private int top =-1;  //栈顶指针
    
    /**
     * 构造函数：根据给定的size初始化栈
     */
    Stack(){
        this(10);   //默认栈大小为10
    }
    
    Stack(int initialSize){
        if(initialSize >=0){
            this.maxSize = initialSize;
            data = new Object[initialSize];
            top = -1;
        }else{
            throw new RuntimeException("初始化大小不能小于0：" + initialSize);
        }
    }
    
    //判空
    public boolean empty(){
        return top==-1 ? true : false;
    }
    
    //进栈,第一个元素top=0；
    public boolean push(E e){
        if(top == maxSize -1){
            throw new RuntimeException("栈已满，无法将元素入栈！");
        }else{
            data[++top]=e;
            return true;
        }    
    }
    
    //查看栈顶元素但不移除
    public E peek(){
        if(top == -1){
            throw new RuntimeException("栈为空！");
        }else{
            return (E)data[top];
        }
    }
    
    //弹出栈顶元素
    public E pop(){
        if(top == -1){
            throw new RuntimeException("栈为空！");
        }else{
            return (E)data[top--];
        }
    }
    
    //返回对象在堆栈中的位置，以 1 为基数
    public int search(E e){
        int i=top;
        while(top != -1){
            if(peek() != e){
                top --;
            }else{
                break;
            }
        }
        int result = top+1;
        top = i;
        return result;      
    }
}
2.链式存储（链表）
public class LinkStack<E> {
    //链栈的节点
    private class Node<E>{
        E e;
        Node<E> next;
        
        public Node(){}
        public Node(E e, Node next){
            this.e = e;
            this.next = next;
        }
    }
    
    private Node<E> top;   //栈顶元素
    private int size;  //当前栈大小
    
    public LinkStack(){
        top = null;
    }
    
    //当前栈大小
    public int length(){
        return size;
    }
    
    //判空
    public boolean empty(){
        return size==0;
    }
    
    //入栈：让top指向新创建的元素，新元素的next引用指向原来的栈顶元素
    public boolean push(E e){
        top = new Node(e,top);
        size ++;
        return true;
    }
    
    //查看栈顶元素但不删除
    public Node<E> peek(){
        if(empty()){
            throw new RuntimeException("空栈异常！");
        }else{
            return top;
        }
    }
    
    //出栈
    public Node<E> pop(){
        if(empty()){
            throw new RuntimeException("空栈异常！");
        }else{
            Node<E> value = top; //得到栈顶元素
            top = top.next; //让top引用指向原栈顶元素的下一个元素 
            value.next = null;  //释放原栈顶元素的next引用
            size --;
            return value;
        }
    }
}
3.Stack(Java自带的实现类)常用方法   
	import java.util.LinkedList;

/**
 * 基于LinkedList实现栈
 * 在LinkedList实力中只选择部分基于栈实现的接口
 */
public class StackList<E> {
    private LinkedList<E> ll = new LinkedList<E>();
    
    //入栈
    public void push(E e){
        ll.addFirst(e);
    }
    
    //查看栈顶元素但不移除
    public E peek(){
        return ll.getFirst();
    }
    
    //出栈
    public E pop(){
        return ll.removeFirst();
    }
    
    //判空
    public boolean empty(){
        return ll.isEmpty();
    }
    
    //打印栈元素
    public String toString(){
        return ll.toString();
    }
}

###类型名：队列  （先进先出） extends Vector
###数据对象集：队列容量maxsize;队列头front;队列尾rear
1.顺序存储（数组）
public class Queue<E> {
    private Object[] data=null;
    private int maxSize; //队列容量
    private int front;  //队列头，允许删除
    private int rear;   //队列尾，允许插入

    //构造函数
    public Queue(){
        this(10);
    }
    
    public Queue(int initialSize){
        if(initialSize >=0){
            this.maxSize = initialSize;
            data = new Object[initialSize];
            front = rear =0;
        }else{
            throw new RuntimeException("初始化大小不能小于0：" + initialSize);
        }
    }
    
    //判空
    public boolean empty(){
        return rear==front?true:false;
    }
    
    //插入
    public boolean add(E e){
        if(rear== maxSize){
            throw new RuntimeException("队列已满，无法插入新的元素！");
        }else{
            data[rear++]=e;
            return true;
        }
    }
    
    //返回队首元素，但不删除
    public E peek(){
        if(empty()){
            throw new RuntimeException("空队列异常！");
        }else{
            return (E) data[front];
        }    
    }
    
    //出队
    public E poll(){
        if(empty()){
            throw new RuntimeException("空队列异常！");
        }else{
            E value = (E) data[front];  //保留队列的front端的元素的值
            data[front++] = null;     //释放队列的front端的元素                
            return value;
        }            
    }
    
    //队列长度
    public int length(){
        return rear-front;
    }
}

2.链式存储（链表）
public class LinkQueue<E> {
    // 链栈的节点
    private class Node<E> {
        E e;
        Node<E> next;

        public Node() {
        }

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
    }
    
    private Node front;// 队列头，允许删除  
    private Node rear;// 队列尾，允许插入  
    private int size; //队列当前长度 
    
    public LinkQueue() {
        front = null;
        rear = null;
    }
    
    //判空
      public boolean empty(){
          return size==0;
      }
      
      //插入
      public boolean add(E e){
          if(empty()){    //如果队列为空
              front = new Node(e,null);//只有一个节点，front、rear都指向该节点
              rear = front;
          }else{
              Node<E> newNode = new Node<E>(e, null);
              rear.next = newNode; //让尾节点的next指向新增的节点
              rear = newNode; //以新节点作为新的尾节点
          }
          size ++;
          return true;
      }
      
      //返回队首元素，但不删除
      public Node<E> peek(){
          if(empty()){
              throw new RuntimeException("空队列异常！");
          }else{
              return front;
          }
      }
      
      //出队
      public Node<E> poll(){
          if(empty()){
              throw new RuntimeException("空队列异常！");
          }else{
              Node<E> value = front; //得到队列头元素
              front = front.next;//让front引用指向原队列头元素的下一个元素
              value.next = null; //释放原队列头元素的next引用
              size --;
              return value;
          }        
      }
      
      //队列长度
      public int length(){
          return size;
      }
}

3.Queue(Java自带的实现类)常用方法  
/**
 * 使用java.util.Queue接口,其底层关联到一个LinkedList（双端队列）实例.
 */
import java.util.LinkedList;
import java.util.Queue;

public class QueueList<E> {
    private Queue<E> queue = new LinkedList<E>();
    
    // 将指定的元素插入此队列（如果立即可行且不会违反容量限制），在成功时返回 true，
    //如果当前没有可用的空间，则抛出 IllegalStateException。
    public boolean add(E e){
        return queue.add(e);
    }
    
    //获取，但是不移除此队列的头。
    public E element(){
        return queue.element();
    }
    
    //将指定的元素插入此队列（如果立即可行且不会违反容量限制），当使用有容量限制的队列时，
    //此方法通常要优于 add(E)，后者可能无法插入元素，而只是抛出一个异常。
    public boolean offer(E e){
        return queue.offer(e);
    }
    
    //获取但不移除此队列的头；如果此队列为空，则返回 null
    public E peek(){
        return queue.peek();
    }
    
    //获取并移除此队列的头，如果此队列为空，则返回 null
    public E poll(){
        return queue.poll();
    }
    
    //获取并移除此队列的头
    public E remove(){
        return queue.remove();
    }
    
    //判空
    public boolean empty() {
        return queue.isEmpty();
    }
}


###类型名 最大堆 （任一结点关键字是其子树所有结点最大值的完全二叉树）
###数据对象集：存储堆元素的数组（完全二叉树存储用数组,元素从下标1开始，下标0放最大值作为哨兵）;堆当前元素个数Size;堆的最大容量Capacity

public class Heap<E>{
	E[] 
}