01-复杂度1.最大连续子序列和
给定K个整数组成的序列，求从第i项到j项的连续子列和的最大值
#输入：输入第1行给出正整数K（<100000）；第2行给出K个整数，其间以空格分隔
#输出：在一行中输出最大子列和，如果序列中所有整数都为负数，则输出零
/////////////////////////////////////////////////////////////////////////////
int main()
{
	int k,ThisSum,MaxSum;
	int i,a[100000];
	ThisSum=MaxSum=0;
	scanf("%d \r",&k);                //‘\r’回车的转义符，用于多行数据输入
	
	for(i=0;i<k;i++){
		scanf("%d",&a[i]);                   //单行多个数据的读入
	}
	for(i=0;i<k;i++){
		ThisSum+=a[i];
		if(ThisSum>MaxSum){
			MaxSum=ThisSum;	
		}
		else if(ThisSum<0){
			ThisSum=0;	
		}
	}
	printf("%d",MaxSum);
    return 0;
}

01-复杂度2.Maximum Subsequence MaxSum(最大连续子序列和的优化)
#输入：同上
#输出：输出最大和，且输出最大子列对应的首尾两个元素值（若有并列最大和，即出现零的情况，选择ij间隔最小的子列）
解析：考虑首尾元素的刷新时刻，与更新MaxSum值时一致，也就是每当更新一次最大值，同时记录此最大值对应的首项和末项，（末项为i，首项为距离末项最近的一个非负项，！temp记录最近一非负项下标），由于if第一个判断为包含最大值为0的情况，加上第三个if条件，然后全局最大和为零时，首尾项同指向一个零。
/////////////////////////////////////////////////////////////////////////////
int main()
{
	int k,ThisSum,MaxSum;
    int temp,firstnum,lastnum;
	ThisSum=MaxSum=0;
//多行数据读入
	scanf("%d \r",&k);
	for(i=0;i<k;i++){
		scanf("%d",&a[i]);
	}
//数据处理开始
	firstnum=temp=0;
	lastnum=k-1;
	for(i=0;i<k;i++){
		ThisSum+=a[i];
		if(ThisSum>MaxSum){
			MaxSum=ThisSum;	
			firstnum=temp;
			lastnum=i;
		}
		else if(ThisSum<0){
			ThisSum=0;
			temp=i+1;		
		}
		else if(ThisSum==0&&MaxSum==0){
			firstnum=temp;
			lastnum=i;
		}
	}
	printf("%d %d %d",MaxSum,a[firstnum],a[lastnum]);
    return 0;
}

02-数据结构2.一元多项式的乘法与加法运算
设计函数分别求两个一元多项式的乘积与和。
#输入：输入分2行，每行分别先给出多项式非零项的个数，再以指数递降方式输入一个多项式非零项系数和指数（绝对值均为不超过1000的整数）。数字间以空格分隔。
#输出：输出分2行，分别以指数递降方式输出乘积多项式以及和多项式非零项的系数和指数。数字间以空格分隔，但结尾不能有多余空格。零多项式应输出 0 0.
/////////////////////////////////////////////////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
typedef struct node
{
	int coef;
	int expn;
	struct node *next;
}polynimial,*polyn;

polyn creat(int i)  //i为非零项个数,创建一元多项式链表
{
	polyn head;
	head=(polyn)malloc(sizeof(polynimial));
	head->next=NULL;
	polyn r,s=NULL;
	r=head;
	int j;
	for(j=0;j<i;j++){
		s=(polyn)malloc(sizeof(polynimial));
		scanf(" %d %d",&s->coef,&s->expn);
		r->next=s;
		s->next=NULL;
		r=s;
	}
	return head;
}

void print(polyn p){      //多项式输出
	p=p->next;
	if(p!=0){
		while(p->next){
			printf("%d %d ",p->coef,p->expn);
			p=p->next;
		}
		printf("%d %d",p->coef,p->expn);
		printf("\n");
	}
	else{
		printf("%d %d\n",0,0);
	}
}

polyn plus(polyn p1,polyn p2){
	int i,j,coef,expn;
	polyn ans=NULL;
	polyn r=NULL,s=NULL;
	
	ans=(polyn)malloc(sizeof(polynimial));
	ans->next=NULL;
	r=ans;
	p1=p1->next;
	p2=p2->next;
	while(p1&&p2){

		if(p1->expn==p2->expn){
			
			coef=p1->coef+p2->coef;
			expn=p1->expn;
			p1=p1->next;
			p2=p2->next;
		}
		else if(p1->expn>p2->expn){
			coef=p1->coef;
			expn=p1->expn;
			p1=p1->next;
		}
		else{
			coef=p2->coef;
			expn=p2->expn;
			p2=p2->next;
		}
		if(coef!=0){
			s=(polyn)malloc(sizeof(polynimial));
			s->next=NULL;
			s->coef=coef;
			s->expn=expn;
			r->next=s;
			r=s;
		}	
	}
	while(p1){
		s=(polyn)malloc(sizeof(polynimial));
		s->next=NULL;
		s->coef=p1->coef;
		s->expn=p1->expn;
		p1=p1->next;
		r->next=s;
		r=s;
	}
	while(p2){
		s=(polyn)malloc(sizeof(polynimial));
		s->next=NULL;
		s->coef=p2->coef;
		s->expn=p2->expn;
		p2=p2->next;
		r->next=s;
		r=s;
	}
	return ans;
}

polyn Mul(polyn m1,polyn m2){
	int i=0;
	polyn h[100],s,r,ans,p1,p2;
	ans=(polyn)malloc(sizeof(polynimial));
	ans->next=NULL;
	p1=m1->next;
	p2=m2->next;
	while(p1){
		h[i]=(polyn)malloc(sizeof(polynimial));
		r=h[i];
		while(p2){
			s=(polyn)malloc(sizeof(polynimial));
			s->coef=p1->coef*p2->coef;
			s->expn=p1->expn+p2->expn;
			s->next=NULL;
			p2=p2->next;
			r->next=s;
			r=s;
		}
		p1=p1->next;
		p2=m2->next;
		ans=plus(ans,h[i]);
		free(h[i]);	
		i++;
	}
	return ans;
	
}

int main( )
{
	int n1,n2;
	polyn p1=NULL,p2=NULL,plus_ans=NULL,mul_ans=NULL;
	scanf("%d",&n1);
	p1=creat(n1);
	scanf("%d",&n2);
	p2=creat(n2);
	mul_ans=Mul(p1,p2);
	print(mul_ans);
	plus_ans=plus(p1,p2);
	print(plus_ans);

	free(p1);
	free(p2);
	free(plus_ans);
	free(mul_ans);	
	return 0;
}

02-数据结构3.Reversing Linked List（小白专场）
Given a constant K and a singly linked list L, you are supposed to reverse the links of every K elements on L. For example, given L being 1→2→3→4→5→6, if K=3, then you must output 3→2→1→6→5→4; if K=4, you must output 4→3→2→1→5→6.
#Input Specification: Each input file contains one test case. For each case, the first line contains the address of the first node, a positive N(<100000) which is the total number of nodes, and a positive K(<N) which is the length of the sublist to be reversed. The address of a node is a 5-digit nonnegative integer, and NULL is represented by -1.Then N lines follow, each describes a node in the format: Address Date Next.
#Output Specification:For each case, output the resulting ordered linked list. Each node occupies a line, and is printed in the same format as in the input.
思路：(取巧方式：顺序存储伪数组)  1.定义一个长10000结构体数组，下标表示每行的Address值，每个结构体内两个信息，date和next。 //采用顺序存储即数组形式表示链表
		2.根据结点的顺序，将地址依次加入到地址数组一个中。
		3.对地址数组中的每个K元素进行反转(反转：首尾元素交换，依次向中间靠拢)，且最后更新结构体数组中Next的值
		4.输出最终结果
	（链表反转操作的解题方法？？？）
/////////////////////////////////////////////////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
#define MAXSIZE 100000

struct Node
{
	int Date;
	int Next;
};
int	rev(int a[],int start,int end){
	int atemp;
	while(start<end){
		atemp=a[start];
		a[start]=a[end];
		a[end]=atemp;
		start++;
		end--;		
	}
}
int print(struct Node List[],int head){
	int addr;
	addr=head;
	while(List[addr].Next!=-1){
		printf("%05d %d %05d\n",addr,List[addr].Date,List[addr].Next);
		addr=List[addr].Next;
	}
	printf("%05d %d %d",addr,List[addr].Date,List[addr].Next);
	
}
int main( )
{
	int str,n,k;
	int i=0,j,addr,date,next;

	int Addr[MAXSIZE];
	struct Node L[MAXSIZE];
    //构建链表 
	scanf("%d %d %d",&str,&n,&k);
    while(n--){
    	scanf("%d %d %d",&addr,&date,&next);
   		L[addr].Date=date;
   		L[addr].Next=next;
   	}
	//构建地址数组 
	n=0;
   	addr=str;
    while(addr!=-1){
    	Addr[n]=addr;
    	addr=L[addr].Next;
    	n++;
	}
	//片段逆序	
	while(k>1&&k+i<=n){
		rev(Addr,i,i+k-1);
		i=i+k;
	}
	for(i=0;i<n-1;i++){
		L[Addr[i]].Next=Addr[i+1];
	}
	L[Addr[n-1]].Next=-1;
	//打印 
	print(L,Addr[0]);
	return 0;
}

03-树1 树的同构
给定两棵树T1和T2。如果T1可以通过若干次左右孩子互换就变成T2，则我们称两棵树是“同构”的。例如图1给出的两棵树就是同构的，因为我们把其中一棵树的结点A、B、G的左右孩子互换后，就得到另外一棵树。而图2就不是同构的。现给定两棵树，请你判断它们是否是同构的。
#输入：输入给出2棵二叉树树的信息。对于每棵树，首先在一行中给出一个非负整数N (≤10)，即该树的结点数（此时假设结点从0到N−1编号）；随后N行，第i行对应编号第i个结点，给出该结点中存储的1个英文大写字母、其左孩子结点的编号、右孩子结点的编号。如果孩子结点为空，则在相应位置上给出“-”。给出的数据间用一个空格分隔。注意：题目保证每个结点中存储的字母是不同的。
#输出：如果两棵树是同构的，输出“Yes”，否则输出“No”
注意点：scanf("%c\n"，&d)在读取换行数据时，尽量不要使用\n,而采用scanf("%c",&d);getchar();两句语句来代替，否则\n会吸收输入末尾所有空白字符，从而导致scanf函数不能结束，直至遇到下一个非空字符
/////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#include <stdlib.h>
#define Maxtree 10
#define Null -1

struct TreeNode{
	char Element;
	int Left;
	int Right;
}T1[Maxtree],T2[Maxtree];

int BuildTree(struct TreeNode T[]){
	int i,N,check[Maxtree]={0},Root=Null;
	char cl,cr;
	scanf("%d",&N);
	getchar();
	if (N)
	{
		for (i=0;i<N;i++)
		{
			scanf("%c %c %c",&T[i].Element,&cl,&cr);
			getchar();
			if(cl!='-'){
				T[i].Left=cl-'0';
				check[T[i].Left]=1;
			}
			else 
				T[i].Left=Null;
			if(cr!='-'){
				T[i].Right=cr-'0';
				check[T[i].Right]=1;
			}
			else 
				T[i].Right=Null;
		}
		for(i=0;i<N;i++)
			if(!check[i]) break;
		Root=i;
	}
	return Root;
}
int Isomorphic(int N1,int N2){
	if ((N1==Null)&&(N2==Null))
		return 1;
	else if(((N1==Null)&&(N2!=Null))||((N2==Null)&&(N1!=Null)))
		return 0;
	else {
		if(T1[N1].Element!=T2[N2].Element)
			return 0;
		if(T1[T1[N1].Left].Element==T2[T2[N2].Left].Element)
			return Isomorphic(T1[N1].Left,T2[N2].Left)&&Isomorphic(T1[N1].Right,T2[N2].Right);
		else if(T1[T1[N1].Left].Element==T2[T2[N2].Right].Element)
			return Isomorphic(T1[N1].Left,T2[N2].Right)&&Isomorphic(T1[N1].Right,T2[N2].Left);
		else
			return 0;
	}
}
int main()
{
	int R1,R2;
	R1=BuildTree(T1);
	R2=BuildTree(T2);
	if(Isomorphic(R1,R2)) printf("Yes\n");
	else printf("No\n");
	
    return 0;
}

03-树2 List Leaves
Given a tree, you are supposed to list all the leaves in the order of top down, and left to right.
#Input:Each input file contains one test case. For each case, the first line gives a positive integer N (≤10) which is the total number of nodes in the tree -- and hence the nodes are numbered from 0 to N−1. Then N lines follow, each corresponds to a node, and gives the indices of the left and right children of the node. If the child does not exist, a "-" will be put at the position. Any pair of children are separated by a space.
#Output:For each test case, print in one line all the leaves indices in the order of top down, and left to right. There must be exactly one space between any adjacent numbers, and no extra space at the end of the line.
/////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#include <stdlib.h>
#include <string.h>
#define Maxtree 10
#define Null -1
#define ElementType int

typedef int bool;
#define TRUE 1
#define FALSE 0

struct TreeNode{
	int Left;
	int Right;
}T[Maxtree];

typedef int Position;
struct QNode {
	ElementType *Data;     /* 存储元素的数组 */
	Position Front, Rear;  /* 队列的头、尾指针 */
	int MaxSize;           /* 队列最大容量 */
};
typedef struct QNode *Queue;

Queue CreateQueue( int MaxSize )
{
	Queue Q = (Queue)malloc(sizeof(struct QNode));
	Q->Data = (ElementType *)malloc(MaxSize * sizeof(ElementType));
	Q->Front = Q->Rear = 0;
	Q->MaxSize = MaxSize;
	return Q;
}

bool IsFull( Queue Q )
{
	return ((Q->Rear+1)%Q->MaxSize == Q->Front);
}

int AddQ( Queue Q, ElementType X )
{
	if ( IsFull(Q) ) {
		printf("队列满");
		return -1;
	}
	else {
		Q->Rear = (Q->Rear+1)%Q->MaxSize;
		Q->Data[Q->Rear] = X;
		return 1;
	}
}

bool IsEmpty( Queue Q )
{
	return (Q->Front == Q->Rear);
}

ElementType DeleteQ( Queue Q )
{
	if ( IsEmpty(Q) ) { 
		printf("队列空");
		return Null;
	}
	else  {
		Q->Front =(Q->Front+1)%Q->MaxSize;
		return  Q->Data[Q->Front];
	}
}
int BuildTree(struct TreeNode T[]){
	int i,N,check[Maxtree]={0},Root=Null;
	char cl,cr;
	scanf("%d",&N);
	getchar();
	if (N)
	{
		for (i=0;i<N;i++)
		{
			scanf("%c %c",&cl,&cr);
			getchar();
			if(cl!='-'){
				T[i].Left=cl-'0';
				check[T[i].Left]=1;
			}
			else 
				T[i].Left=Null;
			if(cr!='-'){
				T[i].Right=cr-'0';
				check[T[i].Right]=1;
			}
			else 
				T[i].Right=Null;
		}
		for(i=0;i<N;i++)
			if(!check[i]) break;
		Root=i;
	}
	return Root;
}
//先序遍历搜索叶结点
//int * SearchLeaves(int N){
//	static int res[Maxtree];
//	static int i;
//	if(N!=Null){
//		if(T[N].Left==Null&&T[N].Right==Null){
//			res[i++]=N;
//		}
//		SearchLeaves(T[N].Left);
//		SearchLeaves(T[N].Right);
//	}
//	res[i]=Null;
//	return res;
//}
//层序遍历搜索叶节点
int * LevelorderSearchLeaves(int R){
	static int res[Maxtree];
	Queue Q;
	int t,i=0;
	if(R==Null) return NULL;
	Q=CreateQueue(Maxtree);
	AddQ(Q,R);
	while(!IsEmpty(Q)){
		t=DeleteQ(Q);
		if(T[t].Left==Null&&T[t].Right==Null)	res[i++]=t;
		else{
			if(T[t].Left!=Null) AddQ(Q,T[t].Left);
			if(T[t].Right!=Null) AddQ(Q,T[t].Right);
		}
	}
	res[i]=Null;
	return res;
}
int main()
{
	int R,j=0;
	int *p;
	R=BuildTree(T);
	p= LevelorderSearchLeaves(R);
	if(p==0) return 0;
	while(*(p+j+1)!=Null){
		printf("%d ",*(p+j));
		j++;
	}
	printf("%d",*(p+j));
	return 0;
}

03-树3 Tree Traversals Again
An inorder binary tree traversal can be implemented in a non-recursive way with a stack. For example, suppose that when a 6-node binary tree (with the keys numbered from 1 to 6) is traversed, the stack operations are: push(1); push(2); push(3); pop(); pop(); push(4); pop(); pop(); push(5); push(6); pop(); pop(). Then a unique binary tree (shown in Figure 1) can be generated from this sequence of operations. Your task is to give the postorder traversal sequence of this tree.
#Input:Each input file contains one test case. For each case, the first line contains a positive integer N (≤30) which is the total number of nodes in a tree (and hence the nodes are numbered from 1 to N). Then 2N lines follow, each describes a stack operation in the format: "Push X" where X is the index of the node being pushed onto the stack; or "Pop" meaning to pop one node from the stack.
#Output:For each test case, print the postorder traversal sequence of the corresponding tree in one line. A solution is guaranteed to exist. All the numbers must be separated by exactly one space, and there must be no extra space at the end of the line.
思路：1.Push是第一次碰到结点，则push的顺序是先序遍历的顺序
	  2.Pop是第二次碰到结点，则pop的顺序是中序遍历的顺序
	  题目转化为，根据push、pop得到先序遍历及中序遍历，求后序遍历的顺序
	  3.已知先序，中序遍历，求后序遍历：采用递归
	  	3.1 确定根,确定左子树，确定右子树。
		3.2 在左子树中递归。
		3.3 在右子树中递归。
		3.4 打印当前根。
////////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#include <stdlib.h>
#include <string.h>

#define Max 30
#define Null -1
#define ElementType int
#define ERROR -1
/*vs中bool编译不通过，为增加对堆栈基本操作函数的兼容性，宏定义伪bool*/		
typedef int bool;
#define true 0
#define false 0

int postorder[Max];
/* 堆栈操作基本函数*/
此处省略，具体见堆栈部分
/* 自定义函数 */
int postorder[Max];
int *  PostorderTraversal(int *pre,int *in,int length){
	static int index=0;
	int rootIndex;
	if(length==0) 
		return 0;
	for(rootIndex=0;rootIndex<length;rootIndex++){   //寻找当前子树的根
		if(*(in+rootIndex)==*pre)
			break;  
	}
	PostorderTraversal(pre+1,in,rootIndex);   //左子树
	PostorderTraversal(pre+rootIndex+1,in+rootIndex+1,length-(rootIndex+1));//右子树
	postorder[index++]=*(in+rootIndex);
}

int main()
{
	int i,N,preorder[Max],inorder[Max],num,m=0,n=0;
	char opr[5];
	Stack s;
	s=CreateStack(Max);
	scanf("%d",&N);
	getchar();
	for(i=0;i<2*N;i++){
		scanf("%s",opr);
		if(opr[1]=='u'){
			scanf("%d",&num);
			preorder[m++]=num;
			Push(s,num);
		}
		else {
			inorder[n++]=Pop(s);
		}
		getchar();
	}
	PostorderTraversal(preorder,inorder,N);

	for(i=0;i<N-1;i++)
		printf("%d ",postorder[i]);
	printf("%d",postorder[N-1]);
	return 0; 
}

04-树4 是否同一棵二叉搜索树（小白专场）
给定一个插入序列就可以唯一确定一棵二叉搜索树。然而，一棵给定的二叉搜索树却可以由多种不同的插入序列得到。例如分别按照序列{2, 1, 3}和{2, 3, 1}插入初始为空的二叉搜索树，都得到一样的结果。于是对于输入的各种插入序列，你需要判断它们是否能生成一样的二叉搜索树。
###输入：输入包含若干组测试数据。每组数据的第1行给出两个正整数N (≤10)和L，分别是每个序列插入元素的个数和需要检查的序列个数。第2行给出N个以空格分隔的正整数，作为初始插入序列。最后L行，每行给出N个插入的元素，属于L个需要检查的序列。
简单起见，我们保证每个插入序列都是1到N的一个排列。当读到N为0时，标志输入结束，这组数据不要处理。
###输出：对每一组需要检查的序列，如果其生成的二叉搜索树跟对应的初始序列生成的一样，输出“Yes”，否则输出“No”。
思路：1 搜索树表示
	  2 建搜索树
	  3 判别一序列是否与搜索树T一致
///////////////////////////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#include <stdlib.h>
/*自定义结构体*/
typedef struct BinTree *BT;
struct BinTree{
	int date;
	BT leftchild;
	BT rightchild;
	int flag;
};
/* 自定义函数 */
BT NewNode(int value){
	BT T;
	T=(BT)malloc(sizeof(struct BinTree));
	T->date=value;
	T->leftchild=T->rightchild=NULL;
	T->flag=0;
	return T;
}
BT Insert(BT T,int value){
	if(!T) T=NewNode(value);
	else{
		if(value<T->date) T->leftchild=Insert(T->leftchild,value);
		else if(value>T->date) T->rightchild=Insert(T->rightchild,value);
	}
	return T;
}
BT MakeTree(int N){
	int v,i;
	BT T;
	scanf("%d",&v);
	T=NewNode(v);
	for(i=1;i<N;i++){
		scanf("%d",&v);
		T=Insert(T,v);
	}
	return T;
}
int check(BT T,int value){
	if(T->flag){
		if(value<T->date) return check(T->leftchild,value);
		else if(value>T->date) return check(T->rightchild,value);
		else return 0;
	}
	else{
		if(value==T->date) {
			T->flag=1;
			return 1;
		}
		else return 0;
	}
}
int Judge(BT T,int N){
	int v,i;
	int tag=1;                  //tag=1代表目前还一致，tag=0代表目前已经不一致
	scanf("%d",&v);
	if(v!=T->date) tag=0;
	else T->flag=1;
	for(i=1;i<N;i++){
		scanf("%d",&v);
		if((tag)&&(!check(T,v))) tag=0;
	}
	return tag;
}
void ResetFlag(BT T){

	T->flag=0;
	if(T->leftchild) ResetFlag(T->leftchild);
	if(T->rightchild) ResetFlag(T->rightchild);
}
void FreeTree(BT T){
	if(T->leftchild) FreeTree(T->leftchild);
	if(T->rightchild) FreeTree(T->rightchild);
	free(T);
}
int main()
{
	int i,N,L;
	BT T;
	scanf("%d",&N);
	while(N){
		scanf("%d",&L);
		T=MakeTree(N);
		for(i=0;i<L;i++){
			if(Judge(T,N)) printf("Yes\n");
			else printf("No\n");
			ResetFlag(T);
		}
		FreeTree(T);
		scanf("%d",&N);
	}
	return 0; 
}

04-树5 Root of AVL Tree
Now given a sequence of insertions, you are supposed to tell the root of the resulting AVL tree.考查平衡树的调整。
###Input:Each input file contains one test case. For each case, the first line contains a positive integer N (≤20) which is the total number of keys to be inserted. Then N distinct integer keys are given in the next line. All the numbers in a line are separated by a space.
###Output:For each test case, print the root of the resulting AVL tree in one line.
思路：二叉树文档中，平衡二叉树的实现即解决这个问题
//////////////////////////////////////////////////////////////////////////////////////////
#include<stdio.h>
#include <stdlib.h>
#define ElementType int
/*自定义结构体*/
typedef struct AVLNode *Position;
typedef Position AVLTree; /* AVL树类型 */
struct AVLNode{
	int Data; /* 结点数据 */
	AVLTree Left;     /* 指向左子树 */
	AVLTree Right;    /* 指向右子树 */
	int Height;       /* 树高 */
};
/* 自定义函数 */
int Max ( int a, int b )
{
	return a > b ? a : b;
}

int GetHeight(AVLTree T){
	if (!T)
		return -1;
	else        
		return Max( GetHeight(T->Left), GetHeight(T->Right) ) + 1;
}

AVLTree SingleLeftRotation ( AVLTree A )
{ /* 注意：A必须有一个左子结点B */
	/* 将A与B做左单旋，更新A与B的高度，返回新的根结点B */     

	AVLTree B = A->Left;
	A->Left = B->Right;
	B->Right = A;
	A->Height = Max( GetHeight(A->Left), GetHeight(A->Right) ) + 1;
	B->Height = Max( GetHeight(B->Left), A->Height ) + 1;

	return B;
}

AVLTree SingleRightRotation ( AVLTree A )
{ /* 注意：A必须有一个右子结点C */
	/* 将A与C做右单旋，更新A与B的高度，返回新的根结点C */     

	AVLTree C = A->Right;
	A->Right = C->Left;
	C->Left = A;
	A->Height = Max( GetHeight(A->Left), GetHeight(A->Right) ) + 1;
	C->Height = Max( A->Height, GetHeight(C->Right) ) + 1;

	return C;
}
AVLTree DoubleLeftRightRotation ( AVLTree A )
{ /* 注意：A必须有一个左子结点B，且B必须有一个右子结点C */
	/* 将A、B与C做两次单旋，返回新的根结点C */

	/* 将B与C做右单旋，C被返回 */
	A->Left = SingleRightRotation(A->Left);
	/* 将A与C做左单旋，C被返回 */
	return SingleLeftRotation(A);
}

AVLTree DoubleRightLeftRotation ( AVLTree A )
{ /* 注意：A必须有一个右子结点B，且B必须有一个左子结点C */
	/* 将A、B与C做两次单旋，返回新的根结点C */

	/* 将B与C做左单旋，C被返回 */
	A->Right = SingleLeftRotation(A->Right);
	/* 将A与C做右单旋，C被返回 */
	return SingleRightRotation(A);
}

AVLTree Insert( AVLTree T, ElementType X )
{ /* 将X插入AVL树T中，并且返回调整后的AVL树 */
	if ( !T ) { /* 若插入空树，则新建包含一个结点的树 */
		T = (AVLTree)malloc(sizeof(struct AVLNode));
		T->Data = X;
		T->Height = 0;
		T->Left = T->Right = NULL;
	} /* if (插入空树) 结束 */

	else if ( X < T->Data ) {
		/* 插入T的左子树 */
		T->Left = Insert( T->Left, X);
		/* 如果需要左旋 */
		if ( GetHeight(T->Left)-GetHeight(T->Right) == 2 )
			if ( X < T->Left->Data ) 
				T = SingleLeftRotation(T);      /* 左单旋 */
			else 
				T = DoubleLeftRightRotation(T); /* 左-右双旋 */
	} /* else if (插入左子树) 结束 */

	else if ( X > T->Data ) {
		/* 插入T的右子树 */
		T->Right = Insert( T->Right, X );
		/* 如果需要右旋 */
		if ( GetHeight(T->Left)-GetHeight(T->Right) == -2 )
			if ( X > T->Right->Data ) 
				T = SingleRightRotation(T);     /* 右单旋 */
			else 
				T = DoubleRightLeftRotation(T); /* 右-左双旋 */
	} /* else if (插入右子树) 结束 */

	/* else X == T->Data，无须插入 */

	/* 别忘了更新树高 */
	T->Height = Max( GetHeight(T->Left), GetHeight(T->Right) ) + 1;

	return T;
}
int main()
{
	int i,N,num;
	AVLTree Root=NULL;
	scanf("%d",&N);
	for(i=0;i<N;i++){
		scanf("%d",&num);
		Root=Insert(Root,num);
	}
	printf("%d",Root->Data);
	free(Root);
	return 0;
}