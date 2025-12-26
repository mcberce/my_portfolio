#include <iostream>
using namespace std;

int main()
{
    float num1, num2; //recebendo dados

    cout << "Digite o primeiro numero:"; //mostra mensagem
    cin >> num1; //aloca valores na variavel escolhida

    cout << "Segundo numero:";
    cin >> num2;

    //Exibindo as operações
    cout<<"soma         :"<<num1+num2<<endl;
    cout<<"subtração        :"<<num1-num2<<endl;
    cout<<"multiplicação        :"<<num1*num2<<endl;
    cout<<"divisão          :"<<num1/num2<<endl;
    cout<<"modulo           :"<<(int)num1%(int)num2<<endl;
    cout<<"porcentagem          :"<<100.0*(num1/num2)<<endl;
    cout<<"media            :"<<(num1+num2)/2<<endl;
    
    return 0;
}