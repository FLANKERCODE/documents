——————————————————————MOOC JAVA——————————————————————————————————————
1.多项式加法（输入降幂排列）
#输入：总共要输入两个多项式，每个多项式的输入格式如下：
每行输入两个数字，第一个表示幂次，第二个表示该幂次的系数，所有的系数都是整数。如果某个幂次的系数为0，就不出现在输入数据中了；0次幂的系数为0时还是会出现在输入数据中。
#输出：从最高幂开始依次降到0幂，如：2x6+3x5+12x3-6x+20
思路：采用一维数组存放多项式，其中下标表示幂次，下标对应的数组元素表示对应幂的指数项,输出时，通过result字符串变量根据每项情况存放结果，一起输出
编程需要考虑细节：
	1.输出首项前无"+"号，可设置falg标志判断，其前面是否有其他项
	2.当系数为1时（包括1和-1），系数1不输出（-1输出-号而不出1）
	3.当系数为负时，除首项外其他项前的"+"不输出
	4.当幂次为1时，幂次1不输出
	5.0次幂系数非零时，直接输出其系数，不出"x"项；若结果为零多项式，则无论0次幂系数是否为0都直接输出其系数
   *6.数组初始化根据最高次幂+1设置，在程序中时刻保证数组下标不越界问题，尤其在1次幂项（1次幂可能不存在在数组中）
////////////////////////////////////////////////////////////////////////
import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		String result="";
		Scanner in=new Scanner(System.in);
		int expn,flag=0;
		//读入第一个多项式
		expn=in.nextInt();
		int[] poly1=new int[expn+1];
		while(expn!=0)
		{
			poly1[expn]=in.nextInt();
			expn=in.nextInt();
		}
		poly1[expn]=in.nextInt();

		//读入第二个多项式
		expn=in.nextInt();
		int[] poly2=new int[expn+1];
		int[] res=new int[poly1.length>poly2.length?poly1.length:poly2.length];
		while(expn!=0)
		{
			poly2[expn]=in.nextInt();
			expn=in.nextInt();
		}
		poly2[expn]=in.nextInt();
		
		//加法
		for(int i =0;i<res.length;i++) {
			if(i<poly1.length&&i<poly2.length)
				res[i]=poly1[i]+poly2[i];
			else if(i<poly1.length)
				res[i]=poly1[i];
			else if(i<poly2.length)
				res[i]=poly2[i];
		}
		
//打印  
		//除1次幂和0次幂外其他幂次项的判断
		for(int i=res.length-1;i>1;i--){    
			if(res[i]>0){                       //正项情况下，考虑是否为首项，系数是否为1
				if(flag!=0)
					result+="+";
				if(res[i]!=1)
					result+=res[i];
				result+="x"+i;
				flag++;
			}
			else if(res[i]<0){                 //负项情况下，考虑系数是否为-1，否则省略1只出"-"
				if(res[i]==-1)
					result+="-";
				else
					result+=res[i];
				result+="x"+i;
				flag++;
			}
		}
		//1次幂                                         
		if(res.length>1) {                        //保证数组下标不溢出
			if(res[1]>0){                         //正负项情况考虑同上，最后输出无幂次
				if(flag!=0)
					result+="+";
				if (res[1]!=1)		
					result+=res[1];
				result+="x";
				flag++;
			}
			else if(res[1]<0){
				if(res[1]==-1)
					result+="-";
				else
					result+=res[1];
				result+="x";
				flag++;
			}
		}
		//0次幂          
		if(flag==0)                 
			result+=res[0];
		else{ 
			if(res[0]>0)                  //在非零多项式时，只需考虑正数前加"+"，否则直接输出系数
				result+="+";
			result+=res[0];
		}
		System.out.print(result);
	}
}

2.GPS数据处理（字符串的操作）
其中$GPRMC语句的格式如下：
$GPRMC,024813.640,A,3158.4608,N,11848.3737,E,10.05,324.27,150706,,,A*50
字段0：$GPRMC，语句ID，表明该语句为Recommended Minimum Specific GPS/TRANSIT Data（RMC）推荐最小定位信息
字段1：UTC时间，hhmmss.sss格式
字段2：状态，A=定位，V=未定位
...
这里整条语句是一个文本行，行中以逗号“,”隔开各个字段，每个字段的大小（长度）不一，这里的示例只是一种可能，并不能认为字段的大小就如上述例句一样。这里，“*”为校验和识别符，其后面的两位数为校验和，代表了“$”和“*”之间所有字符（不包括这两个字符）的异或值的十六进制值。上面这条例句的校验和是十六进制的50，也就是十进制的80。

你的程序要从中找出$GPRMC语句，计算校验和，找出其中校验正确，并且字段2表示已定位的语句，从中计算出时间，换算成北京时间。一次数据中会包含多条$GPRMC语句，以最后一条语句得到的北京时间作为结果输出。你的程序一定会读到一条有效的$GPRMC语句。

###输入：多条GPS语句，每条均以回车换行结束。最后一行是END三个大写字母。
###输出：6位数时间，表达为：hh:mm:ss
其中，hh是两位数的小时，不足两位时前面补0；mm是两位数的分钟，不足两位时前面补0；ss是两位数的秒，不足两位时前面补0。
// 输入样例：
// $GPRMC,024813.640,A,3158.4608,N,11848.3737,E,10.05,324.27,150706,,,A*50
// END
// 输出样例：
// 10:48:13
提示：^运算符的作用是异或。将$和*之间所有的字符做^运算(第一个字符和第二个字符异或，结果再和第三个字符异或，依此类推)之后的值对65536取余后的结果，应该和*后面的两个十六进制数字的值相等，否则的话说明这条语句在传输中发生了错误。注意这个十六进制值中是会出现A-F的大写字母的。另外，如果你需要的话，可以用Integer.parseInt(s)从String变量s中得到其所表达的整数数字；而Integer.parseInt(s, 16)从String变量s中得到其所表达的十六进制数字
/////////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String date="";
		int[] BJTime= new int[3];
		Scanner in=new Scanner(System.in);
		date=in.nextLine();
		while(!date.equals("END")) {
			if(date.indexOf("$GPRMC")==-1||date.indexOf(",A,")==-1) {
				date=in.nextLine();
				continue;
			}
			int startindex=date.indexOf("$GPRMC")+1;
			int endindex=date.indexOf('*');
			String Gprmc=date.substring(startindex, endindex);
			int checkSum=Gprmc.charAt(0)^Gprmc.charAt(1);
			for(int i=2;i<Gprmc.length();i++) {
				checkSum^=Gprmc.charAt(i);
			}
			if((checkSum%65536)==Integer.parseInt(date.substring(date.indexOf('*')+1,date.indexOf('*')+3), 16)) { //满足校验和，则进行时间转换
				BJTime[0]=(Integer.parseInt(Gprmc.substring(6, 8))+8)%24; //时
				BJTime[1]=Integer.parseInt(Gprmc.substring(8, 10));//分
				BJTime[2]=Integer.parseInt(Gprmc.substring(10, 12));//秒
			}	
			date=in.nextLine();
		}
		in.close();
		System.out.printf("%02d:%02d:%02d",BJTime[0],BJTime[1],BJTime[2]);
	}
}

3.分解质因数
每个非素数（合数）都可以写成几个素数（也可称为质数）相乘的形式，这几个素数就都叫做这个合数的质因数。比如，6可以被分解为2x3，而24可以被分解为2x2x2x3。
现在，你的程序要读入一个[2,100000]范围内的整数，然后输出它的质因数分解式；当读到的就是素数时，输出它本身。
###输入：一个整数，范围在[2,100000]内。
###输出：形如：n=axbxcxd 或 n=n 所有的符号之间都没有空格，x是小写字母x。
////////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;

public class Main {
     /*判断素数函数*/
	//public static boolean isprime(int i) {              
	// 	boolean flag=true;
	// 	if(i==2||i==3);
	// 	else if(i%2==0) flag=false;
	// 	else {
	// 		for(int j=2;j<=(i/2);j++) {
	// 			if(i%j==0) {
	// 				flag=false;
	// 				break;
	// 			}
	// 		}
	// 	}
	// 	return flag;
	// }
	
	public static boolean isprime(int i) { 
		boolean isprime=true; 
		if(x==1||x%2==0&&x!=2){
			isprime=false;
		}
		else{
			for(int i=3;i<Math.sqrt(x);i+=2){
				if(x%i==0){
					isprime=false;
					break;
				}
			}
		}

	}
	
	/*寻找质因素函数，每次寻找一个，递归思想，直至另一个因数也为素数时停止递归*/
	public static void find(int n) {
		for(int i=2;i<n;i++) {
			if(n%i==0&&isprime(i)) {
				System.out.print(i+"x");
				if(isprime(n/i))
					System.out.print(n/i);
				else
					find(n/i);
				break;
			}
		}
		
	}
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		int num;
		num=input.nextInt();
		System.out.print(num+"=");
		if(isprime(num)) 
			System.out.print(num);
		else {
			find(num);
		}
		input.close();
	}
}

4.完数
一个正整数的因子是所有可以整除它的正整数。而一个数如果恰好等于除它本身外的因子之和，这个数就称为完数。例如6=1＋2＋3(6的因子是1,2,3)。
现在，你要写一个程序，读入两个正整数n和m（1<=n<m<1000），输出[n,m]范围内所有的完数。
###输入：两个正整数，以空格分隔
###输出：其间所有的完数，以空格分隔，最后一个数字后面没有空格。如果没有，则输出一个空行。
//////////////////////////////////////////////////////////////////////////////////////////
import java.util.Scanner;

public class Main {

	public static boolean iswanshu(int n) {
		boolean flag=false;
		int sum=0;
		for(int i=1;i<n;i++) {
			if(n%i==0) sum+=i;
		}
		if(sum==n) flag=true;
		return flag;
	}
	
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		int n,m,count=0;
		n=input.nextInt();
		m=input.nextInt();
		int[] ws=new int[m-n];
		for(int i=n;i<=m;i++) {
			if(iswanshu(i)) {
				ws[count++]=i;
			} 
		}
		if(count==0)
			System.out.println();
		else {
			for(int i=0;i<count-1;i++) {
				System.out.print(ws[i]+" ");
			}	
			System.out.print(ws[count-1]);
		}
		input.close();
	}
}

4.有秒计时的数字时钟
考察类与对象的掌握
public class Main {
	
	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		Clock clock = new Clock(in.nextInt(), in.nextInt(), in.nextInt());
		clock.tick();
		System.out.println(clock);
		in.close();
	}
}

 class Clock {
	private Display hour=new Display(24);
	private Display minute=new Display(60);
	private Display second=new Display(60);
	private String time="";
	public Clock(int hour,int minute,int second) {
		this.hour.setvalue(hour);
		this.minute.setvalue(minute);
		this.second.setvalue(second);
	}
	
	public void tick() {
		second.increase();
		if(second.getvalue()==0) {
			minute.increase();
			if(minute.getvalue()==0) {
				hour.increase();
			}
		}
	}
	
	public String toString(){
		time=String.format("%02d:%02d:%02d",hour.getvalue(),minute.getvalue(),second.getvalue() );
		return time;
	}

}
 
 class Display {
		private int value;
		private int limit;
		
		public Display(int limit){
			this.limit=limit;
		}
		
		public Display(int limit,int value){
			this.limit=limit;
			this.value=value;
		}
		public void setvalue(int value) {
			this.value=value;
		}
		public void increase() {
			value++;
			if(value==limit) {
				value=0;
			}
		}
		public int getvalue() {
			return value;
		}


	}

5.查找里程
考察HashMap的使用和理解
import java.util.HashMap;
public class Main {
	
	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		HashMap<String,Integer> citylist=new  HashMap<String,Integer>();
		String city1=new String();
		String city2=new String();
		String name=new String(in.next());
		
		int index=0;
		while(!name.equals("###")) {
		//	if(name.equals("Huzhou")) name="Suzhou";  //测试用例有问题，需要该处理才能通过测试(修正版不需要该行代码)
			citylist.put(name,index++);
			name=in.next();
		}
		
		int[][] citymap=new int[citylist.size()][citylist.size()];
        for(int i=0;i<citylist.size();i++) {
        	for(int j=0;j<citylist.size();j++) {
        		citymap[i][j]=in.nextInt();
        	}
        }
        
        city1=in.next();
        city2=in.next();
        System.out.print(citymap[citylist.get(city1)][citylist.get(city2)]);
        in.close();
		
	}
}


——————————————————————蓝桥网题集——————————————————————————————————————
1.Fibonacci数列  
Fibonacci数列的递推公式为：Fn=Fn-1+Fn-2，其中F1=F2=1。
当n比较大时，Fn也非常大，现在我们想知道，Fn除以10007的余数是多少。
输入格式:
输入包含一个整数n。（1 <= n <= 1,000,000）
输出格式:
输出一行，包含一个整数，表示Fn除以10007的余数。
注意点：当n很大时，求Fibonacci数列数值过大会导致溢出；取余运算多次叠加效果和单次一样，且取余之后数值会大大减少；因此在计算中直接记录取余结果进行累和即可
import java.util.Scanner;
public class Main{
	
	public static void main(String[] args) {
    	Scanner in=new Scanner(System.in);
    	int a=1,b=1,c=-1,n;
    	n=in.nextInt();
    	if(n<3)
    		System.out.print(1);
    	else {
    		for(int i=0;i<n-2;i++) {
        		c=(a+b)%10007;
        		a=b;
        		b=c;
        	}
        	System.out.print(c);
    	}
    }
    	
}

2.指定二进制位数格式输出
对于长度为5位的一个01串，每一位都可能是0或1，一共有32种可能。它们的前几个是：00000  00001  00010  00011  00100
请按从小到大的顺序输出这32种01串。
输入格式：
本试题没有输入。
输出格式：
输出32行，按从小到大的顺序每行一个长度为5的01串。
注意点：考虑用printf格式输出实现，但是格式输出无直接二进制输出方式，需要Integer.toBinaryString(i)转化为二进制字符后输出；而字符串格式输出%s不能指定位数补零，需要自行编写高位补零程序。（即不足五位数的二进制字符串如何实现高位补零？？？）, 本题中给出两种字符串补零方法
import java.util.Scanner;
public class Main{

	public static void main(String[] args) {
		String s;
		for(int i=0;i<=0b11111;i++) {
			s=Integer.toBinaryString(i);
			if(s.length()<5) {
//				String str="00000";
//				s=str.substring(0, 5-s.length())+s;  //补零方法一：已知位数
				s=addZeroForNum2(s,5);
			}
			System.out.printf("%5s%n", s);
		}
    }

    //补零方法一：已知位数5
    public static String addZeroForNum1(String str){
    	String zeros="00000";   //零的个数和已知位数一致
	    str=zeros.substring(0, 5-str.length())+str;  //左补零
//	    str=str+zeros.substring(str.length());
	    return str;
    }
	
	//补零方法二：指定len位数
	public static String addZeroForNum2(String str,int len) {
		int strlen=str.length();
		while(strlen<len) {
			StringBuffer sb=new StringBuffer();
			sb.append("0").append(str);   //左补零
//			sb.append(str).append("0");   //右补零
			str=sb.toString();
			strlen=str.length();
		}
		return str;
	}
}

3.字符串处理
利用字母可以组成一些美丽的图形，下面给出了一个例子：

ABCDEFG

BABCDEF

CBABCDE

DCBABCD

EDCBABC

这是一个5行7列的图形，请找出这个图形的规律，并输出一个n行m列的图形。
输入格式
输入一行，包含两个整数n和m，分别表示你要输出的图形的行数的列数。（1 <= n, m <= 26。）
输出格式
输出n行，每个m个字符，为你的图形。
注意点：String对象和StringBuffer对象方法的灵活运用
import java.util.Scanner;
public class Main{

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int m,n;
		String chlist="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer str=new StringBuffer();
		n=in.nextInt();
		m=in.nextInt();
		str=str.append(chlist.substring(0, m));
		System.out.println(str);
		for(int i=1;i<n;i++) {
			str=str.insert(0, chlist.charAt(i)).deleteCharAt(m);
			System.out.println(str);
		}
    }   	
}

4.打印杨辉三角（二维数组的应用）
杨辉三角形又称Pascal三角形，它的第i+1行是(a+b)i的展开式的系数。
它的一个重要性质是：三角形中的每个数字等于它两肩上的数字相加。
下面给出了杨辉三角形的前4行：
   1
  1 1　　
 1 2 1　　
1 3 3 1　　
给出n，输出它的前n行。
输入格式
输入包含一个数n。（1 <= n <= 34）
输出格式
输出杨辉三角形的前n行。每一行从这一行的第一个数开始依次输出，中间使用一个空格分隔。请不要在前面输出多余的空格。
注意点：非对称二维数组的定义（二维数组各行列数不同时，如何分行定义？？）
import java.util.Scanner;
public class Main{

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
        int n;
        n=in.nextInt();
        int[][] pascal=new int[n][];
        pascal[0]= new int[1];
        pascal[0][0]=1;
        for(int i=1;i<pascal.length;i++) {
        	pascal[i]=new int[i+1];
        	pascal[i][0]=1;
            for(int j=1;j<pascal[i].length-1;j++) {
            	pascal[i][j]=pascal[i-1][j-1]+pascal[i-1][j];
            }
            pascal[i][pascal[i].length-1]=1;
        }
        print_Pascal(pascal);
        
	}   	
	
	public static void print_Pascal(int[][] p) {
		for(int i=0;i<p.length-1;i++) {
			for(int j=0;j<p[i].length-1;j++) {
				System.out.print(p[i][j]+" ");
			}
			System.out.println(p[i][p[i].length-1]);
		}
		for(int j=0;j<p[p.length-1].length-1;j++) {
			System.out.print(p[p.length-1][j]+" ");
		}
		System.out.print(p[p.length-1][p[p.length-1].length-1]);
	}

}

5.特殊回文数
123321是一个非常特殊的数，它从左边读和从右边读是一样的。
　　输入一个正整数n， 编程求所有这样的五位和六位十进制数，满足各位数字之和等于n 。
输入格式
　　输入一行，包含一个正整数n。（1<=n<=54）
输出格式
　　按从小到大的顺序输出满足条件的整数，每个整数占一行。
注意点：特殊值，10001和100001同时满足n=2的情况，所以在五位数和六位数的判断要同时都判断；顺序容器的使用
import java.util.ArrayList;
import java.util.Scanner;
public class Main{
	private static ArrayList<Integer> num6=new ArrayList<Integer>();
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt(),x;
		for(int a=1;a<10;a++) {
			for(int b=0;b<10;b++) {
				for(int c=0;c<10;c++) {
					if(2*a+2*b+c==n) {
						x=a*10000+b*1000+c*100+b*10+a;
						System.out.println(x);
						
					}
					if(2*a+2*b+2*c==n) {                                //这里是if，而不是else if，因为特殊情况
						x=a*100000+b*10000+c*1000+c*100+b*10+a;
						num6.add(x);
					}
				}
			}
		}
		for(int i=0;i<num6.size();i++) {
			System.out.println(num6.get(i));
		}	
	}
	
}

6.十六进制转八进制  
给定n个十六进制正整数，输出它们对应的八进制数。
输入格式
　　输入的第一行为一个正整数n （1<=n<=10）。
　　接下来n行，每行一个由0~9、大写字母A~F组成的字符串，表示要转换的十六进制正整数，每个十六进制数长度不超过100000。

输出格式
　　输出n行，每行为输入对应的八进制正整数。
注意点：本题中十六进制长度不超过100000位，而int型最大值为（2,147,483,647）,如果直接使用java内的进制转换是行不通的。！！！必须自己写进制转换程序：先将十六进制按位转换成二进制数，然后每3位二进制转换成一位八进制，注意当二进制位数不是3的整数倍时，高位补零
//十六进制位数超过7位以后，其实际值转换成十进制时就会超过Integer类型变量的内存大小。（-2^31~2^31-1）
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		String[] hex=new String[n];
		StringBuffer bin=new StringBuffer(100000);
		StringBuffer oct=new StringBuffer(100000);
		for(int i=0;i<n;i++) {
			hex[i]=in.next();
		}
		for(int i=0;i<n;i++) {
			bin=hexTobin(hex[i]); //hex[i]中位数过多时，结果输出空值，考虑是否溢出
			oct=binToOct(bin);
			System.out.println(hex[i].length()+";"+bin.length()+";"+oct.length());
			System.out.println("h:"+hex[i]);
			System.out.println("o:"+oct);
		}
	}
	
	static StringBuffer hexTobin(String hex) {
		StringBuffer bin=new StringBuffer();
		String zeros="0000",tempbin;
		char ch;
		for(int i=0;i<hex.length();i++) {
			ch=hex.charAt(i);
		    tempbin=Integer.toBinaryString(Character.getNumericValue(ch));
			bin=bin.append(zeros.substring(0, 4-tempbin.length())).append(tempbin);
		}
		return bin;
	}
	
	static StringBuffer binToOct(StringBuffer bin) {
		StringBuffer oct=new StringBuffer();
		String tempoct;
		while(bin.length()%3!=0) {
			bin=bin.insert(0, 0);	
		}
		if(Integer.parseInt(bin.subSequence(0, 3).toString(), 2)!=0)
				oct=oct.append(Integer.parseInt(bin.subSequence(0, 3).toString()));
		for(int i=3;i<bin.length();i+=3) {
			tempoct=bin.substring(i, i+3);
			oct=oct.append(Integer.parseInt(tempoct, 2));
		}
		return oct;
	}
}



——————————————————————剑指offer——————————————————————————————————————
1.二维数组中的查找
在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
@法一：对矩阵（m*n）的每一行采用二分法查找，时间复杂度为o(m*logn)
public boolean Find(int target, int[][] array) {
		for(int i=0;i<array.length;i++) {
			int left=0,right=array[i].length-1;
			while(left<=right) {
				int mid=(left+right)/2;
				if(target<array[i][mid]) {right=mid-1;}
				else if(target>array[i][mid]) {left=mid+1;}
				else  return true;				
			}
		}
		return false;
	}
@法二：从左下角元素开始对比，若大于左下角元素向右，若小于左下角元素向上；以此类推
public boolean Find(int target, int[][] array) {
		int i=array.length-1;
		int j=0;
		while(i>=0&&j<=array[i].length-1 ) {
			if(target>array[i][j]) {
				j++;
			}
			else if(target<array[i][j]) {
				i--;
			}
			else return true;
		}
		return false;
	}

2.替换空格
请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
@法一：熟悉StringBuffer中的常用方法：delete(int start,int end),replace(int offset, String str);
public String replaceSpace(StringBuffer str) {
	String s="%20";
 	for(int i=0;i<str.length();i++) {
		if(str.charAt(i)==' ') {
		 str.deleteCharAt(i);
		 str.insert(i, s);
	 	}
 	}
 	return str.toString();
}

3.从尾到头打印链表
输入一个链表，从尾到头打印链表每个节点的值。
@法一：从头至尾先取出链表的值，利用反转函数（Collections.reverse(对象名)），反转ArryList对象。
	 /**
*    public class ListNode {
*        int val;
*        ListNode next = null;
*
*        ListNode(int val) {
*            this.val = val;
*        }
*    }
*
*/
import java.util.Collections;
import java.util.ArrayList;
public class Solution {
   	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		 ArrayList<Integer> res=new ArrayList<Integer>();
	        ListNode flag=listNode;
	        if(flag!=null){
	            do{
	                res.add(flag.val);
	                flag=flag.next;
	            }while(flag!=null);
	        }
	        Collections.reverse(res);
	        return res;
    }
}
@法二：链表反转（难点），然后从头至尾取出。单链表逆转的经典案例。
见PAT-线性结构3 Reversing Linked List

4.重建二叉树
给定二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设前序和中序遍历的结果中都不含重复的数组。
@思路：原题中是以类函数的形式给出，不便于程序调试，以下给出代码。在前序遍历中定位根结点位置，然后在中序遍历中找到对应根值记为r,然后分别将r左边序列作为左子树，r右边序列作为右子树，递归调用建树程序，递归过程中注意更新对应子树根结点的位置，左子树根结点位置为原来根结点位置+1，而右子树根结点为原来根结点位置+左子树序列长度+1.
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        return buildtree(pre,0,in,0,in.length-1);
    }
    
    static TreeNode buildtree(int [] pre,int rootindex,int[] in,int inl,int inr){
        if(inl>inr){
            return null;
        }
        if(inl==inr){
            return new TreeNode(in[inl]);
        }
        else{
            int r=in_find(in,pre[rootindex]);
            TreeNode root=new TreeNode(in[r]);
            root.left=buildtree(pre,rootindex+1,in,inl,r-1);
            root.right=buildtree(pre,rootindex+(r-inl)+1,in,r+1,inr);
             return root;
        }
       
    }
    static int in_find(int[] s, int x){
        for(int i=0;i<s.length;i++){
            if(s[i]==x)  return i; 
        }
        return -1;
    }
}
@思路2：为了便于测试，将类函数改写为主程序可测试的程序，通过层序遍历检验建树结果。
！！！层序遍历子程序。
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main{	
	
	 static class TreeNode {
		      int val;
		     TreeNode left;
		     TreeNode right;
		     TreeNode(int x) { val = x; }
		  }
	
	public static void main(String[] args) {  
//		Scanner in=new Scanner(System.in);
		int[] pre= {1,2,3,4,5,6,7};
		int[] in= {3,2,4,1,6,5,7};
		TreeNode r=buildtree(pre,0,in,0,in.length-1);
		System.out.print(LevelOrderTraversal(r) );
		
		
	} 	
	
	static ArrayList<Integer> LevelOrderTraversal(TreeNode root) {
		ArrayList<Integer> res=new ArrayList<Integer>();
		if(root==null) return res;
		Queue<TreeNode> queue=new LinkedList<TreeNode>();   //队列的实现
		queue.offer(root);
		while(!queue.isEmpty()) {
			TreeNode t=queue.poll();
			res.add(t.val);
			if(t.left!=null) queue.offer(t.left);
			if(t.right!=null) queue.offer(t.right);
		}
		return res;
	}
	
	 static TreeNode buildtree(int [] pre,int rootindex,int[] in,int inl,int inr){
	        if(inl>inr){
	            return null;
	        }
	        if(inl==inr){
	             return new TreeNode(in[inl]);
	        }
	        else{
	            int r=in_find(in,pre[rootindex]);
	            TreeNode root=new TreeNode(in[r]);
	            root.left=buildtree(pre,rootindex+1,in,inl,r-1);
	            root.right=buildtree(pre,rootindex+(r-inl)+1,in,r+1,inr);
	             return root;
	        }
	       
	    }
	    static int in_find(int[] s, int x){
	        for(int i=0;i<s.length;i++){
	            if(s[i]==x)  return i; 
	        }
	        return -1;
	    }

}

5.用两个栈实现一个队列。
@思路：stack1作为进队栈，每进一个元素i，就stack1.push(i);stack2作为出队栈，每次执行出队操作时，将stack1中所有元素pop出来依次进入stack2(相当于将所有元素进行倒序排列),然后在stack2.pop(),但是记得pop之后，要将stack2中的元素还原至stack1中，方便下一次的入队。
import java.util.Stack;

public class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    
    public void push(int node) {
        //Stack<Integer> stack=stack1.empty()?stack2:stack1;
        stack1.push(node);
    }
    
    public int pop() {
        while(!stack1.empty()){
            stack2.push(stack1.pop());
        }
        int popval=stack2.pop();
        while(!stack2.empty()){
            stack1.push(stack2.pop());
        }
        return popval;
    }
}

6.斐波那契数列
@思路：F(0)=1，F(1)=1, F(n)=F(n-1)+F(n-2)（n>=2，n∈N*）
public class Solution {
    public int Fibonacci(int n) {
         int [] f=new int[40];
         f[0]=0;
         f[1]=1;
         for(int i=2;i<=n;i++){
             f[i]=f[i-1]+f[i-2];
         }
         return f[n];   
    }
}

7.跳台阶
一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
@思路：从n阶台阶往前倒序考虑，跳上n阶台阶有两种情况：最后一步跳1级；最后一步跳2级。跳n阶方案=跳（n-1）方案+跳（n-2）方案，递推序列类似于斐波那契数列！题目转换为：f(0)=0,f(1)=1,f(2)=2;f(n)=f(n-1)+f(n-2),求f(n).通过递归或者循环均可以实现。
public class Solution {
    public int JumpFloor(int target) {
    	if(target==0)
            return 0;
        if(target==1)
            return 1;
        if(target==2)
            return 2;
        return JumpFloor(target-1)+JumpFloor(target-2);
    }
}

8.变态跳台阶
一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
@思路：同上，从最后一步倒序考虑：跳上n阶台阶可以分为n种情况：最后一步跳了1阶；最后一步跳了2阶；...；最后一步跳了n阶。得到递推公式，f(n)=f(n-1)+f(n-2)+...+f(n-1)+1,f(0)=0,f(1)=1！！！注意：递推式中的最后一项"1"代表的是，如果最后一步把所有台阶跳完了，剩下0个台阶，那么这也是一种跳法。
public class Solution {
    public int JumpFloorII(int target) {
        if(target==0)
            return 0;
        if(target==1)
            return 1;
        int res=1;
        for(int i=1;i<target;i++)
            res+=JumpFloorII(target-i);
        return res;
    }
}

9.矩阵覆盖
我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
@思路：从n倒序考虑：最后一块竖着放，那么剩下考虑2*（n-1）大矩形被填充的问题；最后一块横着放，其下方也必须横着放一块（否则不能无重叠全覆盖），那么考虑剩下2*（n-2）大矩阵被填充问题。同样是递推问题。f(0)=0;f(1)=1;f(2)=2;f(n)=f(n-1)+f(n-2).
public class Solution {
    public int RectCover(int target) {
        if(target==0)
            return 0;
        if(target==1)
            return 1;
        if(target==2)
            return 2;
        return RectCover(target-1)+RectCover(target-2);
    }
}

10.二进制中1的个数
输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
@思路：Integer.toBinaryString(int n)：将整型n按照二进制输出为字符串。
public class Solution {
    public int NumberOf1(int n) {
        String res=Integer.toBinaryString(n);
        int count=0;
        for(int i=0;i<res.length();i++){
            if(res.charAt(i)=='1') count++;
        }
        return count;
    }
}

11.数值的整数次方
给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
@思路：对于指数为正，为负，以及为零，三种情况要分别讨论，注意细节。
public class Solution {
    public double Power(double base, int exponent) {
        if(exponent==0) return 1;
        double res=1;
        if(exponent>0){
            for(int i=0;i<exponent;i++){
                res*=base;
            }
        }
        else{
            exponent=-exponent;
            for(int i=0;i<exponent;i++){
                res*=base;
            }
            res=1/res;
        }
        return res;
  }
}

12.调整数组顺序使奇数位于偶数前面
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
思路：定位第一个偶数下标为even，将even后的所有奇数按顺序都提到even之前即可。
public class Solution {
    public void reOrderArray(int [] array) {
        int even=0;
        for(int i=0;i<array.length;i++){
            if(array[i]%2==0){
                even=i;
                break;
            }
        }
        for(int i=even+1;i<array.length;i++){
            if(array[i]%2==1){
                insert(array,even,i);
                even++;
            }
        }
    }
    
    static void insert(int [] array,int index, int ele){
        int temp=array[ele];
        for(int i=ele;i>index;i--){
            array[i]=array[i-1];
        }
        array[index]=temp;
    }
}

13.链表中倒数第k个节点
输入一个链表，输出该链表中倒数第k个结点。
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindKthToTail(ListNode head,int k) {
        ListNode n=head;
        int count=0;
        while(n!=null){
            count++;
            n=n.next;
        }
        if(count<k) return null;
        n=head;
        for(int i=0;i<count-k;i++){
            n=n.next;
        }
        return n;
    }
}

14.反转链表
输入一个链表，反转链表后，输出链表的所有元素。
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode ReverseList(ListNode head) {
        if(head==null) return null;
        ListNode ne=head;
        ListNode old=head.next;
        while(old!=null){
            ListNode temp=old.next;
            old.next=ne;
            ne=old;
            old=temp;
        }
        head.next=old;
        return ne;
        
    }
}

15.合并两个排序的链表
输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
@思路：递归！
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1==null&&list2!=null){
            return list2;
        }
        if(list1!=null&&list2==null){
            return list1;
        }
        if(list1==null&&list2==null){
            return null;
        }
         ListNode res;
        if(list1.val<list2.val){
            res=new ListNode(list1.val);
            res.next=Merge(list1.next,list2);
        }
        else{
            res=new ListNode(list2.val);
            res.next=Merge(list1,list2.next);
        }
        return res;
    }
}

16.二叉树的镜像
操作给定的二叉树，将其变换为源二叉树的镜像。
@思路：考察层序遍历
import java.util.*;

public class Solution {
    public void Mirror(TreeNode root) {
        
        if(root!=null){
            Queue<TreeNode> q=new LinkedList<TreeNode>();
            q.offer(root);
            while(!q.isEmpty()){
                TreeNode n=q.poll();
                TreeNode t=n.left;
                n.left=n.right;
                n.right=t;
                if(n.left!=null) q.offer(n.left);
                if(n.right!=null) q.offer(n.right);
            }
        }
    }
}

17.顺时针打印矩阵
输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
@注意：矩阵可以是行向量，也可以是列向量，不一定是方针，注意特殊情况。每一轮不一定走完一个外圈，在半圈的时候需要额外判断是否全部数都被遍历过（通过res.size()和矩阵容量比较可判断是否全部遍历结束）
import java.util.ArrayList;
public class Solution {
    public ArrayList<Integer> printMatrix(int [][] matrix) {
       ArrayList<Integer> res=new ArrayList<Integer>();
       int raw=matrix.length;
       int column=matrix[0].length;
        int sum=raw*column;
       int i=0,j=-1;
       while(res.size()<sum){
            for(int x=0;x<column;x++){
                res.add(matrix[i][++j]);
            }
           
           for(int x=0;x<raw-1;x++){
               res.add(matrix[++i][j]);
           }
           
           if(res.size()==sum) break;
           
           for(int x=0;x<column-1;x++){
              res.add(matrix[i][--j]);
           }
          
           for(int x=0;x<raw-2;x++){
               res.add(matrix[--i][j]);
           }
           
           raw-=2;
           column-=2;
        }
       return res;
    }
}

18. 包含min函数的栈
定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。（用自带类stack实现，注意迭代器的使用方法）
import java.util.Stack;
import java.util.Iterator;
public class Solution {

    static Stack<Integer> s=new Stack<Integer>();
    public void push(int node) {
        s.push(node);
    }
    
    public void pop() {
        s.pop();
    }
    
    public int top() {
        return s.peek();
    }
    
    public int min() {
        Iterator<Integer> it=s.iterator();
		int min=it.next();
		while(it.hasNext()) {
            int temp=it.next();
			if(temp<min) min=temp;
		}
        return min;
    }
}

19.栈的压入、弹出序列
输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
@思路：建栈模拟入栈顺序，按照入栈顺序依次进栈，每次进栈检查栈顶元素是否是出栈序列中元素，若是依次弹出，直至不匹配为止，然后继续按进栈序列进栈；否则直接继续进栈，执行完全部进栈和出栈操作后，判断栈是否为空，空则正确，否则错误。
import java.util.*;

public class Solution {
    
    public boolean IsPopOrder(int [] pushA,int [] popA) {
      Stack<Integer> res=new Stack<Integer>();
      int j=0;
      for(int i=0;i<pushA.length;i++){
          res.push(pushA[i]);
          while(j<popA.length&&res.peek()==popA[j]){
              res.pop();
              j++;
          }
      }
      return res.empty()? true:false;
    }
}

20.二叉搜索树的后序遍历序列
输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
@思路：二叉搜索树左子树节点小于根节点，右子树节点大于根节点。对于给定序列，取最后元素为根节点，对其前面序列判断是否小于根的在左，大于根的在右，若不满足则返回false，否则递归判断其左子树和右子树，直至左右边界交错（l>=r），即所有节点都被判断过.
public class Solution {
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0)
            return false;
        return subtree(sequence,0,sequence.length-1);
    }
    
    static boolean subtree(int[] sequence,int l,int r){
        if(l>=r)
            return true;
        int root=sequence[r];
        int i=l;
        while(sequence[i]<root){
            i++;
        }
        for(int j=i;j<r;j++){
            if(sequence[j]<root) return false;
        }
        return subtree(sequence,l,i-1)&&subtree(sequence,i,r-1);
    }
}

21.二叉树中和为某一值的路径
输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
@思路：递归，多分支情况下的递归处理，全局变量，则递归返回值可无需迭代。注意最后一个节点的回溯，即root的左右子树都遍历完了，需要remove（list.size()-1）,返回上一层，相当于删掉了本层递归的root节点，从而返回root的父节点
import java.util.ArrayList;
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
 */

public class Solution {
    
    private ArrayList<Integer> list=new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> listall=new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if(root==null) return listall;
        list.add(root.val);
        if(target==root.val&&root.left==null&&root.right==null)
            listall.add(new ArrayList<Integer>(list));
        FindPath(root.left,target-root.val);
        FindPath(root.right,target-root.val);
        list.remove(list.size()-1);
        return listall;
    }
    

}

22.树的子结构
输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        boolean flag=false;
        if(root1==null||root2==null)
            return false;
        if(root1.val==root2.val)
            flag=Tree1containTree2(root1,root2);
        if(!flag)
            flag=HasSubtree(root1.left,root2);
        if(!flag)
            flag=HasSubtree(root1.right,root2);
        return flag;
    }
    
    public boolean Tree1containTree2(TreeNode root1,TreeNode root2){
        if(root2==null)
            return true;
        if(root1==null||root1.val!=root2.val)   //一定要先判断root1是否为空，否则空节点无法访问val值，会报错：指针溢出
            return false;
        return Tree1containTree2(root1.left,root2.left)&&Tree1containTree2(root1.right,root2.right);
    }
}

23.复杂链表的复制
输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
@法一：a.根据next指针顺序，先构造一个复制链表（其中所有random未设置）
	   b.从头遍历原链表每一个random指针，在复制链表中找到对应节点，再为复制链表节点的random复制 （时间复杂度O(n^2）)
@法二：构造长链表法
		a.将节点N的复制节点N''接在N的后面，然后原来N的一下个节点接在N''的后面，也就是原链表和复制链表交错链接，使得每个复制节点在原节点的下一个
		b.设置random的值，N的random指向S的话，那么N''的random对应指向S'',而根据上一步的构造，N.next=>N'',S.next=>S'',省去遍历寻找S''的过程，直接通过next指针可以读到S''
		c.将长链表根据奇偶再调整回两个链表，并返回复制链表的头结点
		时间复杂度O（N）
/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
public class Solution {
    public RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead==null) return null;
        ConnectToLonglist(pHead);
        Connectrandom(pHead);
        return CloneList(pHead);
    }
    
    public void ConnectToLonglist(RandomListNode pHead){
        RandomListNode sp=pHead;
        RandomListNode s;
        while(sp!=null){
            s=new RandomListNode(sp.label);
            RandomListNode temp=sp.next;
            sp.next=s;
            s.next=temp;
            sp=temp;
        }        

    }
    
    public void Connectrandom(RandomListNode pHead){
        RandomListNode sp=pHead;
        RandomListNode s;
        while(sp!=null){
            s=sp.next;
            if(sp.random!=null)
                s.random=sp.random.next;
            sp=s.next;
        }
    }
    
    public RandomListNode  CloneList(RandomListNode pHead){
        RandomListNode sp=pHead;
        RandomListNode CloneHead=sp.next;
        RandomListNode s=CloneHead;
        while(s.next!=null){
            sp.next=s.next;
            s.next=s.next.next;
            sp=sp.next;
            s=s.next;
        }
        sp.next=null;
        s.next=null;
        return CloneHead;
    }
}

24.二叉搜索树与双向链表
输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向
@法一：双向链表顺序与二叉搜索树的中序遍历顺序一致，先中序遍历将节点按顺序存进ArrayList中，然后对其中的节点进行指针修改  （时间复杂度：O(N^2),对每个节点访问了两次，不是最优方法）

/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
import java.util.ArrayList;
public class Solution {
    ArrayList<TreeNode> res= new ArrayList<TreeNode>();
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null) return null;
        InorderTraveral(pRootOfTree);
        if(res.size()>1){
            res.get(0).right=res.get(1);
            for(int i=1;i<res.size()-1;i++){
                res.get(i).left=res.get(i-1);
                res.get(i).right=res.get(i+1);
            }
            res.get(res.size()-1).left=res.get(res.size()-2);
        }
        return res.get(0);
    }
    
    public void InorderTraveral(TreeNode r){
        if(r==null) return;
        InorderTraveral(r.left);
        res.add(r);
        InorderTraveral(r.right);
    }
}

@法二：直接修改原有的中序遍历，在中序遍历处理当前节点时，直接修改指针。（每个节点只需遍历一次）关键设双向链表的左右头节点，在每次中序遍历时将当前根节点加入链表，调整左右头结点。
public class Solution {
    TreeNode lefthead=null;
    TreeNode righthead=null;
    
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null) return null;
        Convert(pRootOfTree.left);
        if(lefthead==null){
            lefthead=pRootOfTree;
            righthead=pRootOfTree;
        }
        else{
            righthead.right=pRootOfTree;
            pRootOfTree.left=righthead;
            righthead=pRootOfTree;
        }
        Convert(pRootOfTree.right);
        
        return lefthead;
    }
    
}

25.字符串的排列
输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
@思路：多分支递归！（参考21二叉树路径问题），注意回溯，每层递归结束返回上层时，要移除最后一个添加的元素，（类似21中的返回父节点）。另外，本题额外需要注意的是，可能存在相同的字母，因此在将一个完整序列加入ArrayList中时，需要先判断结果中是否已经包含此序列，若包含则无需重复加入。
import java.util.ArrayList;
import java.util.Arrays;
public class Solution {
    StringBuffer s=new StringBuffer();
    ArrayList<String> res=new ArrayList<String>();
    public ArrayList<String> Permutation(String str) {
       char[] ch=str.toCharArray();
	   Arrays.sort(ch);
	   boolean[] f=new boolean[ch.length];
        return find(ch,f);
    }
    
    public ArrayList<String> find(char[] ch,boolean[] f){
        if(ch.length==0) return res;
        if(s.length()==ch.length){
             if(!res.contains(s.toString()))
                res.add(s.toString());
            return res;
        }
            
        for(int i=0;i<ch.length;i++){
            if(f[i]==false){
                s.append(ch[i]);
                f[i]=true;
                find(ch,f);
                f[i]=false;
                s.deleteCharAt(s.length()-1);
            }
        }
        return res;
    }
}

26.连续最大子列和
子列中元素有正有负
@法一：动态规划思想，假设Si是以ai结尾的最大子列和，那么S(i+1)=Si+a(i+1)>a(i+1)?Si+a(i+1):a(i+1),由此计算出以每个元素结尾的最大子列和，其中的最大值就是整个序列的最大子列和
public class Solution {
    public int FindGreatestSumOfSubArray(int[] array) {
        int[] sum=(int[]) array.clone();
        for(int i=1;i<array.length;i++){
            if(sum[i-1]+array[i]>sum[i]) sum[i]=sum[i-1]+array[i];
        }
        int max=sum[0];
        for(int i=1;i<sum.length;i++){
            if(sum[i]>max) max=sum[i];
        }
       return max;
    }
}
@法二：在线处理，从头遍历一次数组，实时计算当前累和，若当前和小于零则置零，因为当前和若小于零，那么当前子列一定不是最大子列和的组成部分，负数不会使后面的部分和增大。在这个过程中，设置maxsum，随时比较记录最大和。
public class Solution {
    public int FindGreatestSumOfSubArray(int[] array) {
        int sum=0;
        int maxsum=array[0];
        for(int i=0;i<array.length;i++){
            sum+=array[i];
            if(sum>maxsum) maxsum=sum;
            if(sum<0) sum=0;
        }

       return maxsum;
    }
}

剑指offer-end


——————————————————————MOOC数据结构(PAT)——————————————————————————————————————
01-复杂度1 最大子列和问题（20 分）
例如给定序列{ -2, 11, -4, 13, -5, -2 }，其连续子列{ 11, -4, 13 }有最大的和20。现要求你编写程序，计算给定整数序列的最大子列和。
@法一：暴力破解，i为子列和起始，j为子列和末尾位置，双重循环i,j，遍历所有可能的子列组合，求最大和
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int k=in.nextInt();
		int[] array=new int[k];
		for(int i=0;i<k;i++) {
			array[i]=in.nextInt();
		}
		int sum=0, max=0;
		for(int i=0;i<array.length;i++) {
			sum=0;   //优化处理
			for(int j=i;j<array.length;j++) {
//注释部分为三层循环暴力破解，可以进行优化，即当起始位置不变，只有末尾位置改变时，sum不用从头开始计算，只需在j上一个值的sum再加上当前末尾a[j]
//				sum=0;
//				for(int m=i;m<=j;m++) {
//					sum+=array[m];
//				}
				sum+=array[j];     //优化处理
				if(sum>max) max=sum;
			}
		}
		System.out.print(max);

	}  
}

@法二：在线处理，从头遍历一次数组，实时计算当前累和，若当前和小于零则置零，因为当前和若小于零，那么当前子列一定不是最大子列和的组成部分，负数不会使后面的部分和增大。在这个过程中，设置maxsum，随时比较记录最大和。 时间复杂度： o(N)
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int k=in.nextInt();
		int[] array=new int[k];
		for(int i=0;i<k;i++) {
			array[i]=in.nextInt();
		}
		int thissum=0,maxsum=0;
		for(int i:array) {
			thissum+=i;
			if(thissum>maxsum) {maxsum=thissum;}
			if(thissum<0) {thissum=0;}
		}
		System.out.print(maxsum);

	}  
}

01-复杂度2 Maximum Subsequence Sum
上一题的变形，要求在输出最大和的基础上，还需要输出最大子列和的首尾两个元素；当所有元素为负时，最大和定义为零，输出整个序列的首尾两项；当有多个并列最大和时，输出首尾元素较靠前的那一组
@注意点：全为负数的情况！只有负数和零的情况！整行读入，减少输入流的使用次数，可以有效减少程序运行时间。
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int k=in.nextInt();
		int[] array=new int[k];
		String t=in.nextLine();
		while(t.equals("")) {t=in.nextLine();}  //保证读进有效数据，而不是换行符号
		in.close();
		int p=0;
		for(String i:t.split(" ")) {
			array[p++]=Integer.parseInt(i);
		}		
		int thissum=0,maxsum=-1,first=0,last=k-1,first_temp=0;
		for(int i=0;i<k;i++) {
			thissum+=array[i];
			if(thissum>maxsum) {
				maxsum=thissum;
				first=first_temp;
				last=i;
			}
			if(thissum<0) {
				thissum=0;
//				if(i+1<array.length)
					first_temp=i+1;
			}
		}
		
		if(maxsum==-1) maxsum=0;
		System.out.print(maxsum+" "+array[first]+" "+array[last]);
	} 	 
}

02-线性结构2 一元多项式的乘法与加法运算
设计函数分别求两个一元多项式的乘积与和。
输入格式:
输入分2行，每行分别先给出多项式非零项的个数，再以指数递降方式输入一个多项式非零项系数和指数（绝对值均为不超过1000的整数）。数字间以空格分隔。
输出格式:
输出分2行，分别以指数递降方式输出乘积多项式以及和多项式非零项的系数和指数。数字间以空格分隔，但结尾不能有多余空格。零多项式应输出0 0。
@法一：自己造轮子！自定义结点类以及多项式类，实现多项式乘法和加法。(129ms)
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int m=in.nextInt();
		poly p1=new poly();
		for(int i=0;i<m;i++) {
			p1.add(in.nextInt(), in.nextInt());
		}
		m=in.nextInt();
		poly p2=new poly();
		for(int i=0;i<m;i++) {
			p2.add(in.nextInt(), in.nextInt());
		}
		poly res=p1.mul(p2);
		res.print();
		System.out.println();
		res=p1.plus(p2);
		res.print();

		in.close();			
	} 	
}
//自定义单个结点类，为多项式类做准备
class Node{
	int exp=0;
	int cof=0;
	Node next=null;
	
	Node(){  	}
	
	Node(int c,int e){
		this.exp=e;
		this.cof=c;
	}
	
}
//自定义多项式的类
class poly{
	Node head=null;
	
	poly(){	}
	poly(Node x){
		this.head=x;
	}
	
	void add(int cof,int exp) {
		Node newnode=new Node(cof,exp);
		if(head!=null) {
			Node now=head;
			while(now.next!=null) {now=now.next;}
			now.next=newnode;
		}
		else {
			head=newnode;
		}
	}
	
	//为空包含两个含义：1，其中所有系数都为零，为零多项式；2.该多项式链表头结点为null;
	boolean IsEmpty() {
		Node now=head;
		while(now!=null) {
			if(now.cof!=0) return false;
			now=now.next;
		}
		return true;
	}
	
	poly plus(poly p){
		poly res=new poly();
		if(this.IsEmpty()||p.IsEmpty()) {
			res=this.IsEmpty()?p:this;
			//偷了懒，此时res和原多项式引用指向同一内存空间，是不安全的操作！
		}
		else {
			Node p1=this.head;
			Node p2=p.head;
			while(p1!=null&&p2!=null) {
				if(p1.exp==p2.exp) {
					if(p1.cof+p2.cof!=0)
						res.add(p1.cof+p2.cof, p1.exp);
					p1=p1.next;
					p2=p2.next;
				}
				else if(p1.exp>p2.exp){
					res.add(p1.cof, p1.exp);
					p1=p1.next;
				}
				else {
					res.add(p2.cof, p2.exp);
					p2=p2.next;
				}
			}
			Node remain=p1==null?p2:p1;
			while(remain!=null) {
				res.add(remain.cof, remain.exp);
				remain=remain.next;
			}
		}
		return res;
	}
	
	poly mul_node(Node n) {
		poly res=new poly();
		if(n==null);
		else {
			Node now=this.head;
			while(now!=null) {
				res.add(n.cof*now.cof, n.exp+now.exp);
				now=now.next;
			}
		}
		return res;
	}
	
	poly mul(poly p) {
		poly res=new poly();
		if(this.IsEmpty()||p.IsEmpty()) {
			res.add(0, 0);
		}
		else {
			Node p2=p.head;
			while(p2!=null) {
				res=res.plus(this.mul_node(p2));
				p2=p2.next;
			}
		}
		return res;
	}
	
	void print() {
		if(this.IsEmpty()) {
			System.out.print("0"+" "+"0");
		}
		else {
			Node now=this.head;
			while(now.next!=null) {
				System.out.print(now.cof+" "+now.exp+" ");
				now=now.next;
			}
			System.out.print(now.cof+" "+now.exp);
		}
	}
	
}

@法二：在java中，线性表的数组实现——ArrayList;线性表的链表实现——LinkedList.考虑利用现有的结构完成多项式存储，而非自己造轮子！
（一般大家都知道ArrayList和LinkedList的大致区别： 
     1.ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。 （LinkedList是双向链表，有next也有previous）
     2.对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。 
     3.对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。）
因为此处要根据下标访问读入两个多项式的每一项，因此采用arraylist速度更快。（131 ms）
运行显示，法一，法二的运行时间差不多。
import java.util.ArrayList;
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int m=in.nextInt();
		ArrayList<Node> poly1=new ArrayList<Node>();
		for(int i=0;i<m;i++) {
			Node n=new Node(in.nextInt(),in.nextInt());
			poly1.add(n);
		}
		m=in.nextInt();
		ArrayList<Node> poly2=new ArrayList<Node>();
		for(int i=0;i<m;i++) {
			Node n=new Node(in.nextInt(),in.nextInt());
			poly2.add(n);
		}
		ArrayList<Node> res=new ArrayList<Node>();
		res=mul(poly1,poly2);
		print(res);
		System.out.println();
		res=plus(poly1,poly2);
		print(res);
		in.close();			
	} 	
	
	static boolean IsZeroPoly(ArrayList<Node> p1) {
		if(!p1.isEmpty()) {
			for(Node i:p1) {
					if(i.cof!=0) return false;
				}
		}
		return true;
	}
	
	static ArrayList<Node> mul(ArrayList<Node> p1, ArrayList<Node> p2){
		ArrayList<Node>res=new ArrayList<Node>();
		if(IsZeroPoly(p1)||IsZeroPoly(p2)) {
			Node temp=new Node(0,0);
			res.add(temp);
		}
		else {
			for(Node i:p1) {
				res=plus(res,muln(i,p2));
			}
		}
		return res;
	}
	
	static ArrayList<Node> muln(Node n, ArrayList<Node> p){
		ArrayList<Node>res=new ArrayList<Node>();
		if(n.cof==0) {
			Node temp=new Node(0,0);
			res.add(temp);
		}
		else {
			for(Node x:p) {
				Node temp=new Node(n.cof*x.cof,n.exp+x.exp);
				res.add(temp);
			}
		}
		return res;
	}
	static ArrayList<Node> plus(ArrayList<Node> p1, ArrayList<Node> p2) {
		ArrayList<Node>res=new ArrayList<Node>();
		if(IsZeroPoly(p1)||IsZeroPoly(p2)) {
			res=IsZeroPoly(p1)?p2:p1;
		}
		else {
			int i=0,j=0;
			
			do {
				Node x=p1.get(i),y=p2.get(j);
				if(x.exp==y.exp) {
					if(x.cof+y.cof==0);
					else {
						Node temp=new Node(x.cof+y.cof,x.exp);
						res.add(temp);
					}
					i++;
					j++;
				}
				else if(x.exp>y.exp) {
					Node temp=new Node(x.cof,x.exp);
					res.add(temp);
					i++;
				}
				else {
					Node temp=new Node(y.cof,y.exp);
					res.add(temp);
					j++;
				}

			}while(i<p1.size()&&j<p2.size());
			
			while(j<p2.size()) {
				Node temp=new Node(p2.get(j).cof,p2.get(j).exp);
				res.add(temp);
				j++;
			}
			
			while(i<p1.size()) {
				Node temp=new Node(p1.get(i).cof,p1.get(i).exp);
				res.add(temp);
				i++;
			}	
		}
		return res;
	}
	
	static void print(ArrayList<Node> p) {
		if(IsZeroPoly(p))
			System.out.print("0"+" "+"0");
		else {
			for(int i=0;i<p.size()-1;i++) {
				System.out.print(p.get(i).cof+" "+p.get(i).exp+" ");
			}
			System.out.print(p.get(p.size()-1).cof+" "+p.get(p.size()-1).exp);
		}
	}
}

class Node{
	int exp=0;
	int cof=0;
	
	Node(){  	}
	
	Node(int c,int e){
		this.exp=e;
		this.cof=c;
	}
	
}

02-线性结构3 Reversing Linked List（25 分）
Given a constant K and a singly linked list L, you are supposed to reverse the links of every K elements on L. For example, given L being 1→2→3→4→5→6, if K=3, then you must output 3→2→1→6→5→4; if K=4, you must output 4→3→2→1→5→6.
nput Specification:
Each input file contains one test case. For each case, the first line contains the address of the first node, a positive N (≤10^​5​​ ) which is the total number of nodes, and a positive K (≤N) which is the length of the sublist to be reversed. The address of a node is a 5-digit nonnegative integer, and NULL is represented by -1.Then N lines follow, each describes a node in the format:   Address Data Next
Output Specification:
For each case, output the resulting ordered linked list. Each node occupies a line, and is printed in the same format as in the input.
@法一：采用Hashmap存储链表结构，键值为当前地址，方便后续根据地址存储;注意点，为了判断进行几次k反转，必须统计 
	1）有效结点数sum（即在链表中的结点数，测试数据中有可能存在多余结点不在链表上），
	2）当前反转到第几个结点，head_order;那么如果sum-head_order>=k,则递归调用反转函数！
		(测试结果：最大N时，运行超时！！其他运行时间大约120ms)
import java.util.HashMap;
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		HashMap<String,Node> list=new HashMap<String,Node>();
		String temp=in.nextLine();
		String[] head_num_k=temp.split(" ");
		String head=head_num_k[0];
		int num= Integer.valueOf(head_num_k[1]);
		int k=Integer.valueOf(head_num_k[2]);
		for(int i=0;i<num;i++) {
			temp=in.nextLine();
			String[] ADN=temp.split(" ");
			list.put(ADN[0],new Node(ADN[0],ADN[1],ADN[2]));
		}
		final int length=count(list, head);
		print(list,reverse(list,head,k,1,length));
		in.close();			
	} 	
	
	static int count(HashMap<String,Node> list,String head){
		int sum=1;
		Node p=list.get(head);
		while(!p.nextaddr.equals("-1")) {
			p=list.get(p.nextaddr);
			sum++;
		}
		return sum;
	}
	
	static String reverse(HashMap<String,Node> list,String head,int k,int head_order,int len) {
		Node newp=list.get(head);
		Node oldp=list.get(newp.nextaddr);
		int count=1;  //反转k-1次
		while(count<k) {             
			Node temp=list.get(oldp.nextaddr);
			oldp.nextaddr=newp.addr;
			newp=oldp;
			oldp=temp;
			count++;
			head_order++;
		}
		if(oldp==null)
			list.get(head).nextaddr="-1";
		else {
			if(len-head_order>=k)
				list.get(head).nextaddr=reverse(list,oldp.addr,k,head_order,len);
			else
				list.get(head).nextaddr=oldp.addr;
		}
		return newp.addr;
	}
	
	static void print(HashMap<String,Node> list,String head) {
		Node n=list.get(head);
		while(!n.nextaddr.equals("-1")) {
			System.out.println(n.addr+" "+n.value+" "+n.nextaddr);
			n=list.get(n.nextaddr);
		}
		System.out.println(n.addr+" "+n.value+" "+n.nextaddr);
	}
}

class Node{
	String addr;
	String value;
	String nextaddr;
	
	Node(){  	}
	
	Node(String a,String b,String c){
		this.addr=a;
		this.value=b;
		this.nextaddr=c;
	}
	
}

@法二：采用容量为最大N的类变量数组存储，下标为当前地址，便于后续根据地址读取数据
       (测试结果：最大N时，运行超时！！其他运行时间大约130ms)
import java.util.Scanner;

class Node{
	int value;
	int nextaddr;
	
	Node(int b,int c){
		this.value=b;
		this.nextaddr=c;
	}
	
}

public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int head=in.nextInt();
		int num=in.nextInt();
		int k=in.nextInt();
		Node[] list=new Node[100000];
		for(int i=0;i<num;i++) {
			list[in.nextInt()]=new Node(in.nextInt(),in.nextInt());
		}
		in.close();
		print(list,reverse(list,head,k,sum(list,head),1));
		
	} 	
	
	static int sum(Node[] list,int head) {
		int sum=0;
		int now=head;
		while(now!=-1) {
			sum++;
			now=list[now].nextaddr;
		}
		return sum;
	}
	
	static int reverse(Node[] list,int head,int k,int sum,int now) {  //now从1开始，记录当前操作到的第几个结点
		int newp=head;
		int old=list[newp].nextaddr;
		
		if(k>1) {	
			int count=1;
			while(count<k) {
				int temp=list[old].nextaddr;
				list[old].nextaddr=newp;
				newp=old;
				old=temp;
				count++;now++;
			}
			if(sum-now>=k)
				list[head].nextaddr=reverse(list,old,k,sum,now);
			else
				list[head].nextaddr=old;
		}
		return newp;
	}
	
	static void print(Node[] list,int head) {  //因为地址输出前序0不可省略，应转换为字符串型，再打印
		int now=head;
		while(now!=-1) {
			String adr=String.format("%05d", now);
			String nextadr;
			if(list[now].nextaddr!=-1)
				nextadr=String.format("%05d", list[now].nextaddr);
			else
				nextadr=String.format("%d", list[now].nextaddr);
			System.out.println(adr+" "+list[now].value+" "+nextadr);
			now=list[now].nextaddr;
		}
	}
	
}

03-树1 树的同构（25 分）
给定两棵树T1和T2。如果T1可以通过若干次左右孩子互换就变成T2，则我们称两棵树是“同构”的。例如图1给出的两棵树就是同构的，因为我们把其中一棵树的结点A、B、G的左右孩子互换后，就得到另外一棵树。而图2就不是同构的。
输入格式:
输入给出2棵二叉树树的信息。对于每棵树，首先在一行中给出一个非负整数N (≤10)，即该树的结点数（此时假设结点从0到N−1编号）；随后N行，第i行对应编号第i个结点，给出该结点中存储的1个英文大写字母、其左孩子结点的编号、右孩子结点的编号。如果孩子结点为空，则在相应位置上给出“-”。给出的数据间用一个空格分隔。注意：题目保证每个结点中存储的字母是不同的。
输出格式:
如果两棵树是同构的，输出“Yes”，否则输出“No”。
@思路：建立树节点，值，左儿子，右儿子；采用数组存放，用数组下标代替指针；注意空树的情况，需要特殊判断，以及当只有一个节点时，如何确定根结点下边，也需要特殊处理。
import java.util.Scanner;

class BNode{
	char value;
	int leftchild;
	int rightchild;
	
	BNode(char value,int l,int r){
		this.value=value;
		this.leftchild=l;
		this.rightchild=r;
	}
	
}

public class Main{	
	static BNode[] BT1;
	static BNode[] BT2;
	static Scanner in=new Scanner(System.in);
	
	public static void main(String[] args) {
		BT1=buildBT();
		BT2=buildBT();
		if(BT1[0]==null&&BT2[0]==null)
			System.out.print("Yes");
		else if(BT1[0]==null||BT2[0]==null)
			System.out.print("No");
		else {
			int r1=BT1[BT1.length-1].leftchild;
			int r2=BT2[BT2.length-1].leftchild;
			if(IsHomo(r1,r2))
				System.out.print("Yes");
			else
				System.out.print("No");
		}
	} 	
	
	static BNode[] buildBT() {				
		int num=in.nextInt();
		in.nextLine();  //吸收第一行后的换行符
		boolean[] Notroot=new boolean[num];  //Boolean型默认初值为false;
		BNode[] BT=new BNode[num+1];  
		for(int i=0;i<num;i++) {
			String[] t=in.nextLine().split(" ");
			int l,r;
			if(!t[1].equals("-")) {
				l=Integer.valueOf(t[1]);
				 Notroot[l]=true;
			}
			else
				l=-1;
			if(!t[2].equals("-")) {
				r=Integer.valueOf(t[2]);
				 Notroot[r]=true;
			}
			else
				r=-1;
			BT[i]=new BNode(t[0].charAt(0),l,r);
		}
		if(num==1)
			BT[num]=new BNode('@',0,0);
		else {
			for(int i=0;i<num;i++) {
				if(!Notroot[i]) {
					BT[num]=new BNode('@',i,i);
					break;
				}
			}
		}
		return BT;
	}
	
	static Boolean IsHomo(int root1,int root2) {
		if(BT1.length!=BT2.length)
			return false;
		if(root1!=-1&&root2!=-1) {
			if(BT1[root1].value!=BT2[root2].value) {
				return false;
			}
			else {
				return ((IsHomo(BT1[root1].leftchild,BT2[root2].leftchild)&&IsHomo(BT1[root1].rightchild,BT2[root2].rightchild))
						||(IsHomo(BT1[root1].leftchild,BT2[root2].rightchild)&&IsHomo(BT1[root1].leftchild,BT2[root2].rightchild)));
			}
		}
		else if(root1==-1&&root2==-1)
			return true;
		else
			return false;
	}
}




03-树2 List Leaves（25 分）
Given a tree, you are supposed to list all the leaves in the order of top down, and left to right.
Input Specification:
Each input file contains one test case. For each case, the first line gives a positive integer N (≤10) which is the total number of nodes in the tree -- and hence the nodes are numbered from 0 to N−1. Then N lines follow, each corresponds to a node, and gives the indices of the left and right children of the node. If the child does not exist, a "-" will be put at the position. Any pair of children are separated by a space.

Output Specification:
For each test case, print in one line all the leaves indices in the order of top down, and left to right. There must be exactly one space between any adjacent numbers, and no extra space at the end of the line.

@思路：通过队列实现二叉树的层序遍历！
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;




public class Main{	
	
	private static class BNode{
		int value;
		int leftchild;
		int rightchild;
		
		BNode(int value,int l,int r){
			this.value=value;
			this.leftchild=l;
			this.rightchild=r;
		}
		
	}
	
	static BNode[] BT;

	static Scanner in=new Scanner(System.in);

	
	public static void main(String[] args) {  //二叉树的层序遍历
		BT=buildBT();
		int root=BT[BT.length-1].leftchild;
		ArrayList<Integer> res=LevelOrderTraversal(BT,root);
		for(int i=0;i<res.size()-1;i++) {
			System.out.print(res.get(i)+" ");
		}
		System.out.print(res.get(res.size()-1));
		
		
	} 	
	
	
	static ArrayList<Integer> LevelOrderTraversal(BNode[] BT,int root) {
		ArrayList<Integer> res=new ArrayList<Integer>();
		if(BT[root]==null) return res;
		Queue<BNode> queue=new LinkedList<BNode>();   //队列的实现
		queue.offer(BT[root]);
		while(!queue.isEmpty()) {
			BNode t=queue.poll();
			if(t.leftchild==-1&&t.rightchild==-1)
				res.add(t.value);
			if(t.leftchild!=-1) queue.offer(BT[t.leftchild]);
			if(t.rightchild!=-1) queue.offer(BT[t.rightchild]);
		}
		return res;
	}
	
	static BNode[] buildBT() {				
		int num=in.nextInt();
		in.nextLine();  //吸收第一行后的换行符
		boolean[] Notroot=new boolean[num];  //Boolean型默认初值为false;
		BNode[] BT=new BNode[num+1];  
		for(int i=0;i<num;i++) {
			String[] t=in.nextLine().split(" ");
//			System.out.print(t[0]+" "+t[1]);
			int l,r;
			if(!t[0].equals("-")) {
				l=Integer.valueOf(t[0]);
				 Notroot[l]=true;
			}
			else
				l=-1;
			if(!t[1].equals("-")) {
				r=Integer.valueOf(t[1]);
				 Notroot[r]=true;
			}
			else
				r=-1;
			BT[i]=new BNode(i,l,r);
		}
		if(num==1)
			BT[num]=new BNode('@',0,0);
		else {
			for(int i=0;i<num;i++) {
				if(!Notroot[i]) {
					BT[num]=new BNode('@',i,i);
					break;
				}
			}
		}
		return BT;
	}
	
	
	
}

03-树3 Tree Traversals Again（25 分）
An inorder binary tree traversal can be implemented in a non-recursive way with a stack. For example, suppose that when a 6-node binary tree (with the keys numbered from 1 to 6) is traversed, the stack operations are: push(1); push(2); push(3); pop(); pop(); push(4); pop(); pop(); push(5); push(6); pop(); pop(). Then a unique binary tree (shown in Figure 1) can be generated from this sequence of operations. Your task is to give the postorder traversal sequence of this tree.
Input Specification:
Each input file contains one test case. For each case, the first line contains a positive integer N (≤30) which is the total number of nodes in a tree (and hence the nodes are numbered from 1 to N). Then 2N lines follow, each describes a stack operation in the format: "Push X" where X is the index of the node being pushed onto the stack; or "Pop" meaning to pop one node from the stack.

Output Specification:
For each test case, print the postorder traversal sequence of the corresponding tree in one line. A solution is guaranteed to exist. All the numbers must be separated by exactly one space, and there must be no extra space at the end of the line.
@思路：push序列是第一次接触每个结点，相当于是先序遍历序列；pop出来以后是第二次接触每个结点，相当于是中序遍历。那么题目转变为已知先序遍历及中序遍历，确定后续遍历。
该思路同剑指offer中的树的重建那一题。在先序遍历中确定根结点的值，然后在中序序列中找到该根结点，并由该根结点将中序序列分为左子树和右子树两个部分，然后分别在右子树和左子树中递归上述过程。思路参考已知先序和中序求后序的过程。
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main{	
	
	static ArrayList<Integer> pre= new ArrayList<Integer>();
	static ArrayList<Integer> ino=new ArrayList<Integer>();
	static ArrayList<Integer> pos=new ArrayList<Integer>();
	
	
	public static void main(String[] args) {
		Stack<Integer> stack=new Stack<Integer>();
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
        in.nextLine();   //吸收第一行末尾的回车
		for(int i=0;i<2*n;i++) {
			String s=in.nextLine();
			if(s.startsWith("Push")) {
				int x=Integer.valueOf(s.split(" ")[1]);
				stack.push(x);
				pre.add(x);
			}
			else if(s.startsWith("Pop")) {
				ino.add(stack.pop());
			}
		}
		
//		System.out.print(pre);
//		System.out.print(ino);
		postorder(0,0,n-1);
		for(int i=pos.size()-1;i>0;i--) {
			System.out.print(pos.get(i)+" ");
		}
		System.out.print(pos.get(0));
		
		
	} 	
	
	
	 static void postorder(int rootindex,int inl,int inr){
	
	        if(inl==inr){
	             pos.add(ino.get(inl));
	        }
	        else if(inl<inr){
	            int r=ino.indexOf(pre.get(rootindex));
	            pos.add(ino.get(r));
	            postorder(rootindex+(r-inl)+1,r+1,inr);
	            postorder(rootindex+1,inl,r-1);
	         
	        }
	       
	    }

}

04-树4 是否同一棵二叉搜索树（25 分）
给定一个插入序列就可以唯一确定一棵二叉搜索树。然而，一棵给定的二叉搜索树却可以由多种不同的插入序列得到。例如分别按照序列{2, 1, 3}和{2, 3, 1}插入初始为空的二叉搜索树，都得到一样的结果。于是对于输入的各种插入序列，你需要判断它们是否能生成一样的二叉搜索树。

输入格式:
输入包含若干组测试数据。每组数据的第1行给出两个正整数N (≤10)和L，分别是每个序列插入元素的个数和需要检查的序列个数。第2行给出N个以空格分隔的正整数，作为初始插入序列。最后L行，每行给出N个插入的元素，属于L个需要检查的序列。

简单起见，我们保证每个插入序列都是1到N的一个排列。当读到N为0时，标志输入结束，这组数据不要处理。

输出格式:
对每一组需要检查的序列，如果其生成的二叉搜索树跟对应的初始序列生成的一样，输出“Yes”，否则输出“No”。
@法一：对于第一组数据，建立二叉搜索树；然后对后续测试数据，按顺序在已建树上进行查找，若查找路径上的数均已被查找，则返回true；否则不是同一棵二叉搜索树。（小白专场，法三）
import java.util.Scanner;

public class Main{	
	
	private static class BNode{
		int value;
		BNode leftchild=null;
		BNode rightchild=null;
		boolean flag=false;
		
		BNode(int value){
			this.value=value;
		}
		
	}

	static Scanner in=new Scanner(System.in);
	
	public static void main(String[] args) {  //二叉树的层序遍历
		int n=in.nextInt();
		BNode BT;
		while(n!=0) {
			int l=in.nextInt();
			BT=buildBST(n);
			for(int i=0;i<l;i++) {
				if(Judge(BT,n))
					System.out.println("Yes");
				else
					System.out.println("No");
				freeflag(BT);
			}
			
			n=in.nextInt();
			
		}
		
		
	} 	
	
	static boolean Judge(BNode BT,int n) {
		boolean flag=true;
		for(int i=0;i<n;i++) {
			int t=in.nextInt();
			if(flag&&!check(BT,t)) flag=false;
		}
		return flag;
	}
	
	static boolean check(BNode BT,int v) {
		if(v==BT.value) {
			BT.flag=true;
			return true;
		}
		else {
			if(!BT.flag) return false;
			if(v<BT.value) return check(BT.leftchild,v);
			else return check(BT.rightchild,v);
		}
		
	} 
	
	static void freeflag(BNode BT) {
		if(BT.flag) BT.flag=false;
		if(BT.leftchild!=null) freeflag(BT.leftchild);
		if(BT.rightchild!=null) freeflag(BT.rightchild);
	}

	

	
	static BNode insert(BNode t,int v) {
		if(t==null) t=new BNode(v);
		else if(v<t.value) t.leftchild=insert(t.leftchild,v);
		else if(v>t.value) t.rightchild=insert(t.rightchild,v);
		return t;
	}
	
	static BNode buildBST(int n) {				
		BNode BT=new BNode(in.nextInt());  
		for(int i=1;i<n;i++) {
			BT=insert(BT,in.nextInt());
		}
		return BT;
	}	
	
}	
@法二：不建树，直接根据序列递归判断（小白专场：法二）


04-树5 Root of AVL Tree
An AVL tree is a self-balancing binary search tree. In an AVL tree, the heights of the two child subtrees of any node differ by at most one; if at any time they differ by more than one, rebalancing is done to restore this property. 
@思路：考察AVL的调整。编写代码时，只要根据四种情况，分别画出调整图，即可直接写出程序。需要注意！！每对AVL进行一次插入或者调整操作时，一定要及时更新变化了的结点的高度！！！千万记得更新树高。并且求树高的迭代程序中，当结点为空，返回树高是-1，而不是0.
import java.util.Scanner;

public class Main{	
	
	private static class Node{
		int value;
		int height;
		Node leftchild;
		Node rightchild;
		
		Node(int value){
			this.value=value;
		}
		
	}

	static Scanner in=new Scanner(System.in);
	
	public static void main(String[] args) {  //二叉树的层序遍历
		int n=in.nextInt();
		Node AVL=null;
		for(int i=0;i<n;i++) {
			AVL=insert(AVL,in.nextInt());
		}
		System.out.print(AVL.value);
		
		
	} 	
	
	
	static Node insert(Node t,int v) {
		if(t==null) t=new Node(v);
		else if(v<t.value) {
			t.leftchild=insert(t.leftchild,v);
			if(getheight(t.leftchild)-getheight(t.rightchild)==2) {//如果平衡性被破坏，需要进行调整
				if(v<t.leftchild.value)
					t=LL(t);
				else
					t=LR(t);
			}
			
		}
		else if(v>t.value) { 
			t.rightchild=insert(t.rightchild,v);
			if(getheight(t.leftchild)-getheight(t.rightchild)==-2) {//如果平衡性被破坏，需要进行调整
				if(v<t.rightchild.value)
					t=RL(t);
				else
					t=RR(t);
			}
		}
		t.height=max(getheight(t.leftchild),getheight(t.rightchild))+1;
		return t;
	}
	
	static Node LL(Node A) {
		Node B=A.leftchild;
		Node C=B.rightchild;
		B.rightchild=A;
		A.leftchild=C;
		A.height=max(getheight(A.leftchild),getheight(A.rightchild))+1;
		B.height=max(getheight(B.leftchild),A.height)+1;
		return B;
	}
	
	static Node RR(Node A) {
		Node B=A.rightchild;
		Node C=B.leftchild;
		B.leftchild=A;
		A.rightchild=C;
		A.height=max(getheight(A.leftchild),getheight(A.rightchild))+1;
		B.height=max(getheight(B.rightchild),A.height)+1;
		return B;
	}
	
	static Node LR(Node A) {
		Node B=A.leftchild;
		Node C=B.rightchild;
		Node CL=C.leftchild,CR=C.rightchild;
		C.leftchild=B;
		C.rightchild=A;
		A.leftchild=CR;
		B.rightchild=CL;
		A.height=max(getheight(CR),getheight(A.rightchild))+1;
		B.height=max(getheight(B.leftchild),getheight(CL))+1;
		C.height=max(A.height,B.height)+1;
		return C;
	}
	
	static Node RL(Node A) {
		Node B=A.rightchild;
		Node C=B.leftchild;
		Node CL=C.leftchild,CR=C.rightchild;
		C.rightchild=B;
		C.leftchild=A;
		A.rightchild=CL;
		B.leftchild=CR;
		A.height=max(getheight(CL),getheight(A.leftchild))+1;
		B.height=max(getheight(B.rightchild),getheight(CR))+1;
		C.height=max(A.height,B.height)+1;
		return C;
	}
	
	static int getheight(Node T) {

		if(T==null) return -1;
		else return max(getheight(T.leftchild),getheight(T.rightchild))+1;
	}
	
	static int max(int a,int b) {
		if(a>b) return a;
		else return b;
	}
	
	
}

05-树7 堆中的路径（25 分）
将一系列给定数字插入一个初始为空的小顶堆H[]。随后对任意给定的下标i，打印从H[i]到根结点的路径。
输入格式:
每组测试第1行包含2个正整数N和M(≤1000)，分别是插入元素的个数、以及需要打印的路径条数。下一行给出区间[-10000, 10000]内的N个要被插入一个初始为空的小顶堆的整数。最后一行给出M个下标。
输出格式:
对输入中给出的每个下标i，在一行中输出从H[i]到根结点的路径上的数据。数字间以1个空格分隔，行末不得有多余空格。
@思路：给定已知序列建树，正常有下列两种方法。方法一：将N个元素依次插入到一个初始为空的堆中，时间复杂度O(log n)；方法二：先按顺序形成完全二叉树，再调整为堆，时间复杂度O（n）。但是本题指定按顺序插入，即方法一。方法二思路在数据结构.java中堆知识点部分可见。
import java.util.ArrayList;
import java.util.Scanner;

public class Main{	

	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int m=in.nextInt();
		ArrayList<Integer> minheap=new ArrayList<Integer>();
		minheap.add(Integer.MIN_VALUE);
//		int [] MinHeap=new int[n+1];
//		MinHeap[0]=Integer.MIN_VALUE;
		for(int i=1;i<=n;i++) {
			insert(minheap,in.nextInt());
		}
		int[] index=new int[m];
		for(int i=0;i<m;i++) {
			printroute(minheap,in.nextInt());
		}
		
	} 	
	
	static void printroute(ArrayList<Integer> h,int last) {
		
		for(int i=last;i>1;i/=2) {
			System.out.print(h.get(i)+" ");
		}
		System.out.println(h.get(1));
	}
	
	static void insert(ArrayList<Integer> h,int val) {
		h.add(val);
		int i=h.size()-1;
		for(;h.get(i/2)>val;i/=2) {
			h.set(i, h.get(i/2));
		}
		h.set(i, val);
	}

}

06-图1 列出连通集（25 分）
给定一个有N个顶点和E条边的无向图，请用DFS和BFS分别列出其所有的连通集。假设顶点从0到N−1编号。进行搜索时，假设我们总是从编号最小的顶点出发，按编号递增的顺序访问邻接点。
输入格式:
输入第1行给出2个整数N(0<N≤10)和E，分别是图的顶点数和边数。随后E行，每行给出一条边的两个端点。每行中的数字之间用1空格分隔。
输出格式:
按照"{ v​1​​  v​2​​  ... v​k​  }"的格式，每行输出一个连通集。先输出DFS的结果，再输出BFS的结果。

@思路：考察DFS(堆栈-递归，类似于先序遍历)，BFS(队列，类似于层序遍历)的基本思想。
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main{	
	static boolean[] visited;
	static int[] MGraph;
	static ArrayList<Integer> res=new ArrayList<Integer>();
	static int n;
	static Queue<Integer> q=new LinkedList<Integer>();
	
	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		n=in.nextInt();
		visited=new boolean[n];
		MGraph=new int [n*(n+1)/2];
		int e=in.nextInt();
		for(int i=0;i<e;i++) {
			int v=in.nextInt(),w=in.nextInt();
			if(v>=w)
				MGraph[v*(v+1)/2+w]=1;
			else
				MGraph[w*(w+1)/2+v]=1;
		}
		

		for(int i=0;i<n;i++) {
			if(!visited[i]) {
				DFS(i);
				print(res);
				res.clear();
			}
		}
		
		visited=new boolean[n];
		for(int i=0;i<n;i++) {
			if(!visited[i]) {
				BFS(i);
				print(res);
				res.clear();
			}
		}

		
	} 	
	
	static void print(ArrayList<Integer> res) {
		System.out.print("{ ");
		for(int i=0;i<res.size();i++) {
			System.out.print(res.get(i)+" ");
		}
		System.out.print("}");
		System.out.println();
	}
	
	static ArrayList<Integer> neighborhood(int v) {
		ArrayList<Integer> nb=new ArrayList<Integer>();
		for(int w=0;w<n;w++) {
			if(v>w) {
				if(MGraph[v*(v+1)/2+w]==1) nb.add(w);
			}
			else if(w>v) {
				if(MGraph[w*(w+1)/2+v]==1) nb.add(w);
			}
					
		}
		return nb;
	}
	
	static void DFS(int v) {
		visited[v]=true;
		res.add(v);
		for(int i:neighborhood(v)) {
			if(!visited[i]) {
				DFS(i);
			}
		}
	}
	
	static void BFS(int v) {
		q.offer(v);
		visited[v]=true;
		while(!q.isEmpty()) {
			int w=q.poll();
			res.add(w);
			for(int i:neighborhood(w)) {
				if(!visited[i]) {
					q.offer(i);  
					visited[i]=true;
				}
			}
			
		}
	}

}

04-树6 Complete Binary Search Tree（30 分）
A Binary Search Tree (BST) is recursively defined as a binary tree which has the following properties:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than or equal to the node's key.
Both the left and right subtrees must also be binary search trees.
A Complete Binary Tree (CBT) is a tree that is completely filled, with the possible exception of the bottom level, which is filled from left to right.

Now given a sequence of distinct non-negative integer keys, a unique BST can be constructed if it is required that the tree must also be a CBT. You are supposed to output the level order traversal sequence of this BST.

Input Specification:
Each input file contains one test case. For each case, the first line contains a positive integer N (≤1000). Then N distinct non-negative integer keys are given in the next line. All the numbers in a line are separated by a space and are no greater than 2000.

Output Specification:
For each test case, print in one line the level order traversal sequence of the corresponding complete binary search tree. All the numbers in a line must be separated by a space, and there must be no extra space at the end of the line.
@思路：完全二叉搜索树，采用数组形式存储，但是注意从下标为0开始存储，无哨兵（和堆的不同），i结点的左儿子为2*i+1,右儿子为2*i+2.注意区别。
数组排序库函数：Arrays.sort(int[] n)!排序结果为从小到大，可直接调用；对数函数：log2(n)=log n/log 2(java中log为以e为底对数函数，换底需要换底公式)，代码实现：
Math.log(n)/Math.log(2);

import java.util.Arrays;
import java.util.Scanner;
public class Main{	
	
	static int[] A;
	static int[] T;
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		A=new int[n];
		T=new int[n];
		for(int i=0;i<n;i++) {
			A[i]=in.nextInt();
		}
		Arrays.sort(A);
		solve(0,n-1,0);
		for(int i=0;i<A.length-1;i++) {
			System.out.print(T[i]+" ");
		}
		System.out.print(T[T.length-1]);
		
		
		
	} 	
	
	
	 static void solve(int Al,int Ar,int Troot){
//初始调用solve(0,n-1,0)	
	        int n=Ar-Al+1;  
	        if(n==0) return;
	        int lnum=getleftlength(n);
	        T[Troot]=A[Al+lnum];
	        int lroot=Troot*2+1;
	        int rroot=lroot+1;
	        solve(Al,Al+lnum-1,lroot);
	        solve(Al+lnum+1,Ar,rroot);
}


	private static int getleftlength(int n) {
		
		int h=(int) (Math.log(n)/Math.log(2));
		int num=(int)Math.pow(2, h)-1;
		int lnum=(num-1)/2;
		lnum+=n-num>(int)Math.pow(2, h-1)?(int)Math.pow(2, h-1):n-num;
		return lnum;
	}
}

PAT-end



——————————————————————去哪儿实习笔试——————————————————————————————————————
1.单词逆序问题
输入两行，第一行为一个单词，第二行为单词表，要求将单词变换为逆序，每次变换只能通过单词表中单词转换，每次只能变换一个字母。输出最短变换次数。
如单词：hot，单词表：doh got dot god tod dog lot log，变换最短路径为：hot-dot-doh-toh,即输出结果为4。

@暴力破解：
单词表中的单词进行分层，先找出所有与原始单词（记为str）差一个字母的所有可行单词，放入一个列表中，对列表中单词进行遍历，判断是否和最终结果只差一个字母，如果是，说明转换结束，则跳出循环，变换次数为2加上循环次数；若否，则将列表中每个单词作为str，重复上述步骤，把所有下一步单词放入新的列表中，再对列表遍历判断是否接近逆序结果，依次循环。

@注意点：输入数据的处理！！同一行的多个字符串变量如何放入数组？输入中有无效空行，如何处理？

输入：
hot

doh got dot god tod dog lot log
输出：
4

import java.util.ArrayList;
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		String str=in.nextLine();
		String temp=in.nextLine();
		while(temp.equals("")) {temp=in.nextLine();}  //除去输入中无效空行
		StringBuffer rev=new StringBuffer();
		rev=rev.append(str).reverse();
		String[] words=temp.split(" ");               //将同一行输入的多个单词放入字符串数组中
		ArrayList<String> ans=find(words,str);
		ArrayList<String> ans1=new ArrayList<String>();
		int t=3;
		while(!check_array(ans,rev.toString())) {
			ans1.clear();
			for(String x:ans) {
				ans1.addAll(find(words,x));
			}
			ans=ans1;
			
			t++;
		}
		System.out.println(t);

	} 
	
	static ArrayList<String> find(String[] s,String o) {
		ArrayList<String> ans=new ArrayList<String>();
		for(int i=0;i<s.length;i++) {
			if(s[i]==null) continue;
			else if(check(s[i],o)) {
			ans.add(s[i]);
			s[i]=null;
			}
		}
		return ans;
	}
	
	static boolean check(String s,String o) {
		int count=0;
		if(s==null||o==null) return false;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==o.charAt(i)) count++;
		}
		if(count==o.length()-1) return true; 
		else return false;
	}
	
	static boolean check_array(ArrayList<String> s,String o) {
		for(String x:s) {
			if(check(x,o)) {
				return true;
			}
		}
		return false;
	}

}

——————————————————————京东算法岗实习笔试——————————————————————————————————————
1.移除（0个或者部分）字母，使剩余的字母成为回文字符（回文字符如A,ABBA,ABCBA等），假设空字符串不是回文字符，其中移除0个字符也是一种移除方案
输入：输入一个字符串，其中包括多个大写字母
输出：可行的移除方案个数
例 （i）输入：XXY  输出：4（Y;X1Y;X2Y;X1X2）
	（ii）输入：ABA  输出：5（0;B;A1B;A1A2;BA）
思路：递归！每次只删除其中一个字符，判断当前是否为回文字符，若是+1，否则+0，然后递归判断在当前字符上删除一个字符的结果，，，直至字符被删除至只剩下两个，则停止递归（return 0）;因为一个字母一定是回文字符，可直接加上字符串长度，而无需判断。
import java.util.Scanner;
public class Main{	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		String s=in.next();
		int t=s.length();
		if(Ishuiwen(s)) t++;
		t+=cal(s);
		System.out.println(t);
		in.close();			
	} 	
	
	static int cal(String s) {
		int t=0;
		if(s.length()==2)
			return 0;
		else {

			for(int j=0;j<s.length();j++) {
				StringBuffer m=new StringBuffer();
				m=m.append(s);	
				String n=m.deleteCharAt(j).toString();
				if(Ishuiwen(n)) 
					t+=1+cal(n);
				else
					t+=cal(n);
			}
				
		}
		return t;
	}
	
	static boolean Ishuiwen(String s) {
		StringBuffer p=new StringBuffer();
		p=p.append(s);
		if(p.reverse().toString().equals(s)) {
			return true;
		}
		else
			return false;
	}
	
}


——————————————————————华为实习笔试——————————————————————————————————————
1.在GBK编码下，请编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串，但是要保证汉字不被截取半个，同时忽略字符串中的数组后输出最终结果
输入格式：一行字符串和待截取的字节数
输出：单独的一行截取后的字符串

@注意：GBK编码中，一个汉字占两个字节，而java采用unicode编码，String型变量中无论汉字与否，下标索引递增都是1.

（通过率：80%）
public class Main{	
	
	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		String tex=in.next();
		int n=in.nextInt();
		StringBuffer out=new StringBuffer();
		int[] b=new int[tex.length()];
		for(int i=0;i<tex.length();i++) {
			Character c=tex.charAt(i);
			if(Character.isDigit(c)) continue;
			if((c >= 0x4e00)&&(c <= 0x9fbb)) {

				n-=2;
				out.append(c);
				if(n<=0)
					break;
			}	
			else {

				n-=1;
				out.append(c);
				if(n<=0)
					break;
			}
		}
		System.out.print(out);		
	} 	
	
}

2.写一个程序计算13日出现在某个星期的次数（在给定的N年时间中），这个时间段位1900年1月1日到1900+N-1年12月31日。N为非负整数，不超过400.（1900年1月1日是星期一）
输入：1 0（第一个参数为years，表示距离1900年1月1日的年数；第二个参数为weeks，表示星期树，用0-6代表星期日到星期六）
输出：13日出现在星期数为weeks的次数，若异常失败则输出-1；
（通过率：80%）
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int w=in.nextInt();
		int days=13;
		int times=0;
		if(n==0||w<0|w>6) times=-1;
		else {
			for(int i=1900;i<1900+n;i++) {
				int[] month= {31,28,31,30,31,30,31,31,30,31,30,31};
				if(isrn(i)) {
					month[1]=29;
				}
				for(int j=0;j<12;j++) {
					if(days%7==w) times++;
					days+=month[j];
				}
			}
		}
		System.out.print(times);

		
	}

	static boolean isrn(int y) {
		if(y%400==0)
			return true;
		else if(y%100!=0&&y%4==0)
			return true;
		else
			return false;
	}
}

3.有N个骰子，同时投掷出去，向上面的数字之和为A，那么输入为N个骰子，请计算出A和他出现的概率。概率值，小数点保留5位。
输入：N（骰子个数）
输出：[[1,0.16667],[2,0.16667],[3,0.16667],[4,0.16667],[5,0.16667],[6,0.16667]]
@思路：动态规划！求出骰子个数为n,和为sum情况出现的次数，寻找n与n-1之间的递推关系
第一步，确定问题解的表达式。可将f(n, s) 表示n个骰子点数的和为s的排列情况总数。 
第二步，确定状态转移方程。n个骰子点数和为s的种类数只与n-1个骰子的和有关。因为一个骰子有六个点数，那么第n个骰子可能出现1到6的点数。所以第n个骰子点数为1的话，f(n,s)=f(n-1,s-1)，当第n个骰子点数为2的话，f(n,s)=f(n-1,s-2)，…，依次类推。在n-1个骰子的基础上，再增加一个骰子出现点数和为s的结果只有这6种情况！那么有：

f(n,s)=f(n-1,s-1)+f(n-1,s-2)+f(n-1,s-3)+f(n-1,s-4)+f(n-1,s-5)+f(n-1,s-6) ，0< n<=6n 
f(n,s)=0, s< n or s>6n
上面就是状态转移方程，已知初始阶段的解为： 
当n=1时, f(1,1)=f(1,2)=f(1,3)=f(1,4)=f(1,5)=f(1,6)=1。


import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		float r;
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		float sum=1;
		for(int i=0;i<n;i++) {
			sum*=6;
		}
		System.out.print("[");
		for(int i=n;i<6*n;i++) {
			float times=(float) get(n,i);
			r=times/sum;
			System.out.printf("[%d,%.5f],",i,r);
		}
		float times=(float) get(n,6*n);
		r=times/sum;
		System.out.printf("[%d,%.5f]]",6*n,r);	
		System.out.println((float) 1/36);	
	}	
	
	static int get(int n,int sum) {
		if(n<1||sum<n||sum>6*n) {
			return 0;
		}
		if(n==1) {
			return 1;
		}
		int rescount=0;
		rescount=get(n-1,sum-1)+get(n-1,sum-2)
				+get(n-1,sum-3)+get(n-1,sum-4)
				+get(n-1,sum-5)+get(n-1,sum-6);
		return rescount;
	}
	
}

——————————————————————爱奇艺java开发实习笔试——————————————————————————————————————
1.给定一个字符串，输出字典序最大的子串（从原字符串删除一些字符或者0个字符后得到其子串）
@思路：
a.对于这个题目我们要求字典序最大的子串，那么我们就应该让子串的第一个字母最大。
b.让这个子串形成一个最长递减子序列【包括等于】。这里我们逆向实现。
逆向实现的思路：从主串的最后一个字母出发，向前遍历，如果前边的字母大于等于后边串中最大的字母，那么保存下来，最后逆序输出即可。

2.给定X,Y,Z任意三个数字，通过若干次变换将三个数字变换至相同的值。其中变换规则如下：a)选取任意两个数字，分别加1；b)选取一个数字，加2；求最少变换次数（对任意三个数，一定可以通过若干次变换实现一致）
@思路：将最后可达到的一致值记为a，其中变换1进行了m次，变换2进行了n次，那么总和有下式成立：3*a=2m+2n+x+y+z;总变换次数k=m+n,那么有3*a=2*k+x+y+z;当k为最小时，对应的一致值a也一定是最小值。那么也就是从x,y，z中最大的那个数开始尝试，寻找满足等式的a，就可以对应求出k.
import java.util.Scanner;

public class Main{	
	static int[] num;
	static int res;
	static int n;
	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		int sum=0;
		int max=0;
		for(int i=0;i<3;i++) {
			int t=in.nextInt();
			if(t>max) max=t;
			sum+=t;
		}
		int a=max;
		int res=-1;
		while(true) {
			if((3*a-sum)%2==0) {
				res=(3*a-sum)/2;
				break;
			}
			a++;
		}
			System.out.print(res);
		
		
	} 	

}

3.给定n种糖果，每种糖果有无限多个，现在想组成一个糖果盒，该糖果盒共有m个糖果，且该糖果盒中第i种糖果个数numi,满足li<=numi<=ri.求一共有多少种组合方案。（任一种糖果数目不同就认为是不同方案）
@思路：res=m-(l1+l2+...+ln),那么问题就变成，共有n个框，每个框可选的数字范围为0<=x<=ri,n个框中的数字求和为res,求共有多少种序列可能性。
	从第0个框开始，有0~l[0]种情况，每种情况下，从第1个框开始，又有0~l[1]种情况，，，以此类推，相当于是n次迭代，需要用递归实现！！！
import java.util.Scanner;

public class Main{	
	static int[] num;
	static int res;
	static int n;
	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		n=in.nextInt();
		num=new int[n];
		int m=in.nextInt();
		int sum_l=0;
		for(int i=0;i<n;i++) {
			int l=in.nextInt();
			int r=in.nextInt();
			sum_l+=l;
			num[i]=r-l;
		}
		res=m-sum_l;
		
		System.out.print(get(0,res));
		
		
	} 	

	
	static int get(int i,int sum) {   //从第i种糖果开始到第n-1个糖果中，总和为sum的方案数
		if(i>n-1) {
			return 0;
		}
		if(i==n-1) {
			if(sum>=0&&num[i]>=sum)
				return 1;
			else
				return 0;
		}
		int rescount=0;
		for(int v=0;v<=num[i];v++) {
			rescount+=get(i+1,sum-v);
		}
		return rescount;
	}
}


——————————————————————美团数据挖掘实习笔试——————————————————————————————————————
题1暴力破解（60%）
import java.util.Scanner;

public class Main{	

	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		int N=in.nextInt();
		int n=in.nextInt();
		int m=in.nextInt();
		int p=in.nextInt();
		int[] a=new int[N+1];
		a[1]=p;
		for(int i=2;i<=N;i++) {
			a[i]=(a[i-1]+153)%p;
		}
		int sum=0;
		for(int i=1;i<=n;i++) {
			for(int j=1;j<=m;j++) {
				sum+=a[gcd(i,j)];
			}
		}
	
		System.out.print(sum);
		
		
	} 	
	
	static int gcd(int a,int b) {
		while(b!=0) {
			int c=a%b;
			a=b;
			b=c;
		}
		return a;
	}
	


}

题1优化（60%）
import java.util.Scanner;

public class Main{	

	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		int N=in.nextInt();
		int n=in.nextInt();
		int m=in.nextInt();
		int p=in.nextInt();
		int[] a=new int[N+1];
		a[1]=p;
		for(int i=2;i<=N;i++) {
			a[i]=(a[i-1]+153)%p;
		}
		int sum=(m+n-1)*a[1];
		int min=m>n?n:m;
		for(int i=2;i<=min;i++) {
			sum+=a[i];
			for(int j=i+1;j<=min;j++) {
				sum+=2*a[gcd(i,j)];
			}
		}
		int max=m>n?m:n;
		for(int i=min+1;i<=max;i++) {
			for(int j=2;j<=min;j++) {
				sum+=a[gcd(i,j)];
			}
		}
	
		System.out.print(sum);
		
		
	} 	
	
	static int gcd(int a,int b) {
		while(b!=0) {
			int c=a%b;
			a=b;
			b=c;
		}
		return a;
	}
	


}


题2（40%）
import java.util.Scanner;

public class Main{	

	public static void main(String[] args) {  
		Scanner in=new Scanner(System.in);
		int N=in.nextInt();
		for(int i=0;i<N;i++) {
			int m=in.nextInt();
			System.out.println(count(m));
		}
	
		
		
		
	} 	
	
	static long count(int a) {
		if(a<10) return a;
		String s=String.format("%d", a);
		int len=s.length();
		int base=9;
		long count=9;
		for(int i=2;i<len;i++) {
			base*=10;
			count+=base*i;
		}
		int base2=base*10/9;
		count+=(a-base2+1)*len;
		
		return count;
	}
	


}
