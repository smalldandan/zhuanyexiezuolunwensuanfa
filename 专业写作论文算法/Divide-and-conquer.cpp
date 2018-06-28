#include<iostream>
#include<stdio.h>
#include<stdlib.h>
#include<string>
#include<string.h>
#include<math.h>
#include<algorithm>
using namespace std;
//算法3.12 使用分治法的序列对准
char s[12]={'A','A','C','A','G','T','T','A','C','C'};
char t[10]={'T','A','A','G','G','T','C','A'};
int m=0,n=0;
int opt(int i,int j)
{
    int penalty;
    if(i==m)
        return 2*(n-j);
    else if(j==n)
        return 2*(m-i);
    else{
        if(s[i]==t[j])
            penalty=0;
        else
            penalty=1;
        return min(opt(i+1,j+1)+penalty,min(opt(i+1,j)+2,opt(i,j+1)+2));
    }
}
int main()
{
    m=strlen(s);
    n=strlen(t);
    cout<<m<<" "<<n<<endl;
    int op=opt(0,0);
    cout<<op<<endl;
}
