#include <iostream>
using namespace std;

int main()
{
    int lado, areaq1;
    int i;
    for (i = 1; i <= 10; i++)
    {
        cout << "Digite o lado quadrado " << i << endl;
        cin >> lado;
        areaq1 = lado * lado;
        cout << "a area do quadrado" << i << "é: " << areaq1 << endl;
    }
    return 0;
}