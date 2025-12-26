#include <iostream>
using namespace std;

int main()
{
    try
    {
        int idade = 15;
        if (idade >= 18)
        {
            cout << "Acesso liberado;";
        }
        else
        {
            throw(idade);
        }
    }
    catch (int myNum)
    {
        cout << "Acesso negado.\n";
        cout << "Sua idade é: " << myNum;
    }
}