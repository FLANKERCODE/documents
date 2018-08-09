类型名称：堆栈 Stack
操作集：长度为MaxSize的堆栈S，堆栈元素item
应用：表达式求值 （a.后缀表达式求值 b.中缀表达式转后缀表达式）

1. 栈的顺序存储实现（数组）
typedef int Position;
struct SNode {
    ElementType *Data; /* 存储元素的数组 */
    Position Top;      /* 栈顶指针 */
    int MaxSize;       /* 堆栈最大容量 */
};
typedef struct SNode *Stack;

 1.1 创建堆栈（申请整个数组空间）
Stack CreateStack( int MaxSize )
{
    Stack S = (Stack)malloc(sizeof(struct SNode));
    S->Data = (ElementType *)malloc(MaxSize * sizeof(ElementType)); //为数组申请空间
    S->Top = -1;
    S->MaxSize = MaxSize;
    return S;
}
 1.2 判断是否已满
bool IsFull( Stack S )
{
    return (S->Top == S->MaxSize-1);
}
 1.3 入栈
bool Push( Stack S, ElementType X )
{
    if ( IsFull(S) ) {
        printf("堆栈满");
        return false;
    }
    else {
        S->Data[++(S->Top)] = X;
        return true;
    }
}
 1.4 判断是否为空 
bool IsEmpty( Stack S )
{
    return (S->Top == -1);
}
 1.5 出栈
ElementType Pop( Stack S )
{
    if ( IsEmpty(S) ) {
        printf("堆栈空");
        return ERROR; /* ERROR是ElementType的特殊值，标志错误 */
    }
    else 
        return ( S->Data[(S->Top)--] );
}


 2.堆栈的链式存储实现（单链表）
typedef struct SNode *PtrToSNode;
struct SNode {
    ElementType Data;
    PtrToSNode Next;
};
typedef PtrToSNode Stack;

 2.1 创建堆栈头结点（链表是按节点单独申请空间）  //链表根据需要申请节点空间，不存在是否为满情况
Stack CreateStack( ) 
{ /* 构建一个堆栈的头结点，返回该结点指针 */
    Stack S;   //S为指向链表下一节点的指针，此处特别表示栈底位置
 
    S = (Stack)malloc(sizeof(struct SNode));
    S->Next = NULL;
    return S;
}
 2.2 判断是否为空 
bool IsEmpty ( Stack S )
{ /* 判断堆栈S是否为空，若是返回true；否则返回false */
    return ( S->Next == NULL );
}
 2.3 入栈
bool Push( Stack S, ElementType X )
{ /* 将元素X压入堆栈S */
    PtrToSNode TmpCell;
 
    TmpCell = (PtrToSNode)malloc(sizeof(struct SNode));
    TmpCell->Data = X;
    TmpCell->Next = S->Next;
    S->Next = TmpCell;
    return true;
}
 2.4 出栈
ElementType Pop( Stack S )  
{ /* 删除并返回堆栈S的栈顶元素 */
    PtrToSNode FirstCell;       //中间变量
    ElementType TopElem;
 
    if( IsEmpty(S) ) {
        printf("堆栈空"); 
        return ERROR;
    }
    else {
        FirstCell = S->Next; 
        TopElem = FirstCell->Data;
        S->Next = FirstCell->Next; //注意，栈顶元素删除，但链表结构仍存在，需要链接下一个链表元素（非栈内元素）
        free(FirstCell);       //释放栈顶元素空间
        return TopElem;
    }
}