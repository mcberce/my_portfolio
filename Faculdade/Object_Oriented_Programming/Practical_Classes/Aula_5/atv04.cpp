#include <iostream>
using namespace std;

int main()
{
    int num;
    int i;
    int resultado;

    cout << "Escreva o número que deseja calcular: " << endl;
    cin >> num;

    for (i = 0; i <= 10; i++)
    {
        resultado = num * i;
        cout << resultado << endl;
    }
    return 0;
}