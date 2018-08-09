###类型名：线性表
###数据对象集：线性表是n个元素构成的有序序列
###操作集：线性表L，i表示位置，元素X
应用：一元多项式polyn的表示
/********************************   目录索引  ************************************/
1.线性表的顺序存储实现（数组）
    .1 初始化 
    .2 查找 
    .3 插入 
    .4 删除 
2.线性表的链式存储实现（链表）
    .1 求表长
    .2 查找 
    .3 带头结点的插入 
    .4 带头结点的删除 
3.广义表结构体
/********************************************************************************/
1.线性表的顺序存储实现（数组）    *零项也需要全部完整表示
typedef int Position;
typedef struct LNode *List;
struct LNode {
    ElementType Data[MAXSIZE];
    Position Last;
};
 
1.1 初始化 
List MakeEmpty()
{
    List L;
 
    L = (List)malloc(sizeof(struct LNode));
    L->Last = -1;
 
    return L;
}

1.2 查找 
#define ERROR -1
 
Position Find( List L, ElementType X )
{
    Position i = 0;
 
    while( i <= L->Last && L->Data[i]!= X )
        i++;
    if ( i > L->Last )  return ERROR; /* 如果没找到，返回错误信息 */
    else  return i;  /* 找到后返回的是存储位置 */
}
 
1.3  插入 
/*注意:在插入位置参数P上与课程视频有所不同，课程视频中i是序列位序（从1开始），这里P是存储下标位置（从0开始），两者差1*/
bool Insert( List L, ElementType X, Position P ) 
{ /* 在L的指定位置P前插入一个新元素X */
    Position i;
 
    if ( L->Last == MAXSIZE-1) {
        /* 表空间已满，不能插入 */
        printf("表满"); 
        return false; 
    }  
    if ( P<0 || P>L->Last+1 ) { /* 检查插入位置的合法性 */
        printf("位置不合法");
        return false; 
    } 
    for( i=L->Last; i>=P; i-- )
        L->Data[i+1] = L->Data[i]; /* 将位置P及以后的元素顺序向后移动 */
    L->Data[P] = X;  /* 新元素插入 */
    L->Last++;       /* Last仍指向最后元素 */
    return true; 
} 
 
1.4 删除 
/*注意:在删除位置参数P上与课程视频有所不同，课程视频中i是序列位序（从1开始），这里P是存储下标位置（从0开始），两者差1*/
bool Delete( List L, Position P )
{ /* 从L中删除指定位置P的元素 */
    Position i;
 
    if( P<0 || P>L->Last ) { /* 检查空表及删除位置的合法性 */
        printf("位置%d不存在元素", P ); 
        return false; 
    }
    for( i=P+1; i<=L->Last; i++ )
        L->Data[i-1] = L->Data[i]; /* 将位置P+1及以后的元素顺序向前移动 */
    L->Last--; /* Last仍指向最后元素 */
    return true;   
}
///////////////////////////////////////////////////////////////////////////////////
2.线性表的链式存储实现（链表）     *为了省去零项空间占用的无意义内存,实现方式：date中存储非零项的指数coef及系数expn两项信息
typedef struct LNode *PtrToLNode;
struct LNode {
    ElementType Data;
    PtrToLNode Next;
};
typedef PtrToLNode Position;
typedef PtrToLNode List;

2.1 求表长
int Length ( List PtrL )
{ 
    List p = PtrL; /* p指向表的第一个结点*/
    int j = 0;
    while ( p ) {
        p = p->Next;
        j++; /* 当前p指向的是第 j 个结点*/
    }
    return j;
}

2.2  查找 
#define ERROR NULL
 
Position Find( List L, ElementType X )
{
    Position p = L; /* p指向L的第1个结点 */
 
    while ( p && p->Data!=X )
        p = p->Next;
 
    /* 下列语句可以用 return p; 替换 */
    if ( p )
        return p;
    else
        return ERROR;
}
 
2.3 带头结点的插入 
/*注意:在插入位置参数P上与课程视频有所不同，课程视频中i是序列位序（从1开始），这里P是链表结点指针，在P之前插入新结点 */
bool Insert( List L, ElementType X, Position P )
{ /* 这里默认L有头结点 */
    Position tmp, pre;
 
    /* 查找P的前一个结点 */        
    for ( pre=L; pre&&pre->Next!=P; pre=pre->Next ) ;            
    if ( pre==NULL ) { /* P所指的结点不在L中 */
        printf("插入位置参数错误\n");
        return false;
    }
    else { /* 找到了P的前一个结点pre */
        /* 在P前插入新结点 */
        tmp = (Position)malloc(sizeof(struct LNode)); /* 申请、填装结点 */
        tmp->Data = X; 
        tmp->Next = P;
        pre->Next = tmp;
        return true;
    }
}
 
2.4 带头结点的删除 
/*注意:在删除位置参数P上与课程视频有所不同，课程视频中i是序列位序（从1开始），这里P是拟删除结点指针 */
bool Delete( List L, Position P )
{ /* 这里默认L有头结点 */
    Position tmp, pre;
 
    /* 查找P的前一个结点 */        
    for ( pre=L; pre&&pre->Next!=P; pre=pre->Next ) ;            
    if ( pre==NULL || P==NULL) { /* P所指的结点不在L中 */
        printf("删除位置参数错误\n");
        return false;
    }
    else { /* 找到了P的前一个结点pre */
        /* 将P位置的结点删除 */
        pre->Next = P->Next;
        free(P);
        return true;
    }
}

3.广义表结构体
typedef struct GNode *GList;
struct GNode{
    int Tag; /*标志域： 0表示结点是单元素， 1表示结点是广义表 */
    union { /*子表指针域Sublist与单元素数据域Data复用，即共用存储空间*/
        ElementType Data;
        GList SubList;
    } URegion;
    GList Next; /* 指向后继结点 */
};