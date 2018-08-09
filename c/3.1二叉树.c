/*   目录索引  */
 1.数组元素静态查找 
 2.二叉树存储
 3.二叉树遍历
 4.二叉搜索树
    4.1 搜索（尾递归可以用迭代代替）
    4.2 插入（递归）
    4.3 删除
5.平衡二叉树
/***************/
1.静态查找:根据某个给定关键字K，从集合R中找出关键字与K相同的记录
1.1 顺序查找  o(N)
typedef struct LNode 
{
	int Element[MAXSIZE];
	int Length;
}* List;

int SequentialSearch(List Tb1,int K){
	int i;
	Tb1->Element[0]=K;          //设立“哨兵”：Element[0];
	For(i=Tb1->Length;Tb1->Element[i]!=k;i--);   
	return i;  //查找成功返回所在单元下标，否则返回0
}
1.2	二分查找 o(log2N）
int BinarySearch(List Tbl, int K){
	int left,right,mid,NotFound=-1;

	left=1;
	right=Tbl->Length;
	while(left<=right){
		mid=(left<=right)/2;
		if(K<Tbl->Element[mid])	Right=mid-1;
		else if(K>Tbl->Element[mid])	Left=mid+1;
		else return mid;
	}
	return NotFound;
}

2.二叉树存储
2.1 顺序存储（完全二叉树）
按从上到下，从左到右顺序存储
#非根结点（序号 i > 1）的父结点的序号是 i / 2;
#结点（序号为 i ）的左孩子结点的序号是 2i，（若2 i <= n，否则没有左孩子） ;
#结点（序号为 i ）的右孩子结点的序号是 2i+1，（若2 i +1<= n，否则没有右孩子） ;

2.2 链表存储（一般二叉树）
typedef struct TNode *Position;
typedef Position BinTree; /* 二叉树类型 */
struct TNode{ /* 树结点定义 */
    ElementType Data; /* 结点数据 */
    BinTree Left;     /* 指向左子树 */
    BinTree Right;    /* 指向右子树 */
};


3.二叉树的遍历（先、中、后序实质是针对访问根节点/打印结点时间而言）
3.1 先序遍历
遍历过程为：① 访问根结点；② 先序遍历其左子树；③ 先序遍历其右子树。
void PreorderTraversal( BinTree BT )
{
    if( BT ) {
        printf("%d ", BT->Data );
        PreorderTraversal( BT->Left );
        PreorderTraversal( BT->Right );
    }
}

3.2 中序遍历
遍历过程为：① 中序遍历其左子树；② 访问根结点；③ 中序遍历其右子树。
void InorderTraversal( BinTree BT )
{
    if( BT ) {
        InorderTraversal( BT->Left );
        /* 此处假设对BT结点的访问就是打印数据 */
        printf("%d ", BT->Data); /* 假设数据为整型 */
        InorderTraversal( BT->Right );
    }
}
 
3.3 后序遍历
 遍历过程为：
① 后序遍历其左子树；② 后序遍历其右子树；③ 访问根结点。
void PostorderTraversal( BinTree BT )
{
    if( BT ) {
        PostorderTraversal( BT->Left );
        PostorderTraversal( BT->Right );
        printf("%d ", BT->Data);
    }
}

3.4 非递归遍历（堆栈）
对于每个结点，程序中一共碰到两次，第一次：push;第二次：pop
中序遍历—先左再根后右，即在第二次碰到结点时访问(打印)
先序遍历—先根再左后右，即在第一次碰到结点时访问(打印)
3.4.1 中序遍历非递归遍历算法（堆栈）
堆栈保存根结点，访问完左儿子之后，弹出根结点访问，按其右指针访问右儿子
#遇到一个结点，就把它压栈，并去遍历它的左子树；
#当左子树遍历结束后，从栈顶弹出这个结点并访问它；
#然后按其右指针再去中序遍历该结点的右子树。
void InOrderTraversal( BinTree BT )
{ BinTree T=BT;
	Stack S = CreatStack( MaxSize ); /*创建并初始化堆栈S*/
	while( T || !IsEmpty(S) ){
		while(T){ /*一直向左并将沿途结点压入堆栈*/
			Push(S,T);
			T = T->Left;
		}
		if(!IsEmpty(S)){
			T = Pop(S); /*结点弹出堆栈*/
			printf(“%5d”, T->Data); /*（访问） 打印结点*/
			T = T->Right; /*转向右子树*/
		}
	}
}
3.4.2 先序遍历非递归遍历算法（堆栈）
void InOrderTraversal( BinTree BT )
{ BinTree T=BT;
    Stack S = CreatStack( MaxSize ); /*创建并初始化堆栈S*/
    while( T || !IsEmpty(S) ){
        while(T){ /*一直向左并将沿途结点压入堆栈*/
            Push(S,T);
            printf(“%5d”, T->Data); /*（访问） 打印结点*/
            T = T->Left;
        }
        if(!IsEmpty(S)){
            T = Pop(S); /*结点弹出堆栈*/
            T = T->Right; /*转向右子树*/
        }
    }
}

3.5 层序遍历（队列）
队列保存根结点，根结点出队访问，往队列放入根的左右儿子
队列实现： 队列实现： 遍历从根结点开始，首先将根结点入队，然后开始执
行循环：结点出队、访问该结点、其左右儿子入队
void LevelorderTraversal ( BinTree BT )
{ 
    Queue Q; 
    BinTree T;
 
    if ( !BT ) return; /* 若是空树则直接返回 */
     
    Q = CreatQueue(); /* 创建空队列Q */
    AddQ( Q, BT );
    while ( !IsEmpty(Q) ) {
        T = DeleteQ( Q );
        printf("%d ", T->Data); /* 访问取出队列的结点 */
        if ( T->Left )   AddQ( Q, T->Left );
        if ( T->Right )  AddQ( Q, T->Right );
    }
}



4.二叉搜索树
4.1 搜索（尾递归可以用迭代代替）
Position IterFind( ElementType X, BinTree BST )
{
    while( BST ) {
    if( X > BST->Data )
        BST = BST->Right; /*向右子树中移动， 继续查找*/
    else if( X < BST->Data )
        BST = BST->Left; /*向左子树中移动， 继续查找*/
    else /* X == BST->Data */
        return BST; /*查找成功， 返回结点的找到结点的地址*/
    }
    return NULL; /*查找失败*/
}
//搜索最小元素（最左叶结点）
Position FindMin( BinTree BST )
{
    if( !BST ) return NULL; /*空的二叉搜索树，返回NULL*/
    else if( !BST->Left )
        return BST; /*找到最左叶结点并返回*/
    else
        return FindMin( BST->Left ); /*沿左分支继续查找*/
}
//搜索最大元素（最右叶结点）
Position FindMax( BinTree BST )
{
if(BST )
while( BST->Right ) BST = BST->Right;
/*沿右分支继续查找，直到最右叶结点*/
return BST;
}

4.2 插入（递归）
BinTree Insert( BinTree BST, ElementType X )
{
    if( !BST ){ /* 若原树为空，生成并返回一个结点的二叉搜索树 */
        BST = (BinTree)malloc(sizeof(struct TNode));
        BST->Data = X;
        BST->Left = BST->Right = NULL;
    }
    else { /* 开始找要插入元素的位置 */
        if( X < BST->Data )
            BST->Left = Insert( BST->Left, X );   /*递归插入左子树*/
        else  if( X > BST->Data )
            BST->Right = Insert( BST->Right, X ); /*递归插入右子树*/
        /* else X已经存在，什么都不做 */
    }
    return BST;
}

4.3 删除
情况讨论：a.叶结点 b.只有一个孩子结点 c.有左右两颗子树（转换成a：取右子树的最小元素替换；转换成b：取左子树的最大元素替换）
BinTree Delete( BinTree BST, ElementType X ) 
{ 
    Position Tmp; 
 
    if( !BST ) 
        printf("要删除的元素未找到"); 
    else {
        if( X < BST->Data ) 
            BST->Left = Delete( BST->Left, X );   /* 从左子树递归删除 */
        else if( X > BST->Data ) 
            BST->Right = Delete( BST->Right, X ); /* 从右子树递归删除 */
        else { /* BST就是要删除的结点 */
            /* 如果被删除结点有左右两个子结点 */ 
            if( BST->Left && BST->Right ) {
                /* 从右子树中找最小的元素填充删除结点 */
                Tmp = FindMin( BST->Right );
                BST->Data = Tmp->Data;
                /* 从右子树中删除最小元素 */
                BST->Right = Delete( BST->Right, BST->Data );
            }
            else { /* 被删除结点有一个或无子结点 */
                Tmp = BST; 
                if( !BST->Left )       /* 只有右孩子或无子结点 */
                    BST = BST->Right; 
                else                   /* 只有左孩子 */
                    BST = BST->Left;
                free( Tmp );
            }
        }
    }
    return BST;
}

5.平衡二叉树
typedef struct AVLNode *Position;
typedef Position AVLTree; /* AVL树类型 */
struct AVLNode{
    ElementType Data; /* 结点数据 */
    AVLTree Left;     /* 指向左子树 */
    AVLTree Right;    /* 指向右子树 */
    int Height;       /* 树高 */
};
 
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
 
AVLTree DoubleLeftRightRotation ( AVLTree A )
{ /* 注意：A必须有一个左子结点B，且B必须有一个右子结点C */
  /* 将A、B与C做两次单旋，返回新的根结点C */
     
    /* 将B与C做右单旋，C被返回 */
    A->Left = SingleRightRotation(A->Left);
    /* 将A与C做左单旋，C被返回 */
    return SingleLeftRotation(A);
}
 
/*************************************/
/* 对称的右单旋与右-左双旋请自己实现 */
AVLTree SingRightRotation ( AVLTree A )
{ /* 注意：A必须有一个右子结点C */
  /* 将A与C做右单旋，更新A与B的高度，返回新的根结点C */     
 
    AVLTree C = A->Right;
    A->Right = C->Left;
    C->Left = A;
    A->Height = Max( GetHeight(A->Left), GetHeight(A->Right) ) + 1;
    C->Height = Max( A->Height, GetHeight(B->Right) ) + 1;
  
    return B;
}

AVLTree DoubleRightLeftRotation ( AVLTree A )
{ /* 注意：A必须有一个右子结点B，且B必须有一个左子结点C */
  /* 将A、B与C做两次单旋，返回新的根结点C */
     
    /* 将B与C做左单旋，C被返回 */
    A->Right = SingleLeftRotation(A->Right);
    /* 将A与C做右单旋，C被返回 */
    return SingleRightRotation(A);
}
/*************************************/
 
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

int GetHeight(AVLTree T){
    if (!T)
        return -1;
    else        
        return Max( GetHeight(T->Left), GetHeight(T->Right) ) + 1;
}