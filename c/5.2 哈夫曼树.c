1 haffmanTree的构造
typedef struct TreeNode *HuffmanTree;
struct TreeNode{
	int Weight;
	HuffmanTree Left, Right;
}

HuffmanTree Huffman( MinHeap H )
{ /* 假设H->Size个权值已经存在H->Elements[]->Weight里 */
	int i; HuffmanTree T;
	BuildMinHeap(H); /*将H->Elements[]按权值调整为最小堆*/
	for (i = 1; i < H->Size; i++) { /*做H->Size-1次合并*/
		T = malloc( sizeof( struct TreeNode) ); /*建立新结点*/
		T->Left = DeleteMin(H);
		/*从最小堆中删除一个结点， 作为新T的左子结点*/
		T->Right = DeleteMin(H);
		/*从最小堆中删除一个结点， 作为新T的右子结点*/
		T->Weight = T->Left->Weight+T->Right->Weight;
		/*计算新权值*/
		Insert( H, T ); /*将新T插入最小堆*/
	} 
	T= DeleteMin(H);
	return T;
}

2 集合及运算
#define MAXN 1000                  /* 集合最大元素个数 */
typedef int ElementType;           /* 默认元素可以用非负整数表示 */
typedef int SetName;               /* 默认用根结点的下标作为集合名称 */
typedef ElementType SetType[MAXN]; /* 假设集合元素下标从0开始 */
2.1 集合的树结构表示（树用数组存储，数组中每个元素的类型描述）
typedef struct {
	ElementType Data;
	int Parent;
} SetType;
2.1 （1） 查找某个元素所在的集合（用根结点表示）
int Find( SetType S[ ], ElementType X )
{ 	/* 在数组S中查找值为X的元素所属的集合 */
	/* MaxSize是全局变量， 为数组S的最大长度 */
	int i;
	for ( i=0; i < MaxSize && S[i].Data != X; i++) ;
	if( i >= MaxSize ) return -1; /* 未找到X， 返回-1 */
	for( ; S[i].Parent >= 0; i = S[i].Parent ) ;
	return i; /* 找到X所属集合， 返回树根结点在数组S中的下标 */
}
2.1 （2）集合的并运算
 @分别找到X1和X2两个元素所在集合树的根结点
 @如果它们不同根，则将其中一个根结点的父结点指针设置成另一个根结点的数组下标。
void Union( SetType S[ ], ElementType X1, ElementType X2 )
{
	int Root1, Root2;
	Root1 = Find(S, X1);
	Root2 = Find(S, X2);
	if（ Root1 != Root2 ） S[Root2].Parent = Root1;
}
2.1 （2）集合并运算的优化——小集合并入大集合
void Union( SetType S, SetName Root1, SetName Root2 )
{ /* 这里默认Root1和Root2是不同集合的根结点 */
    /* 保证小集合并入大集合 */
    if ( S[Root2] < S[Root1] ) { /* 如果集合2比较大 */
        S[Root2] += S[Root1];     /* 集合1并入集合2  */
        S[Root1] = Root2;
    }
    else {                         /* 如果集合1比较大 */
        S[Root1] += S[Root2];     /* 集合2并入集合1  */
        S[Root2] = Root1;
    }
}
 /*
SetName Find( SetType S, ElementType X )
{ /* 默认集合元素全部初始化为-1 */
    if ( S[X] < 0 ) /* 找到集合的根 */
        return X;
    else
        return S[X] = Find( S, S[X] ); /* 路径压缩 */
}
*/