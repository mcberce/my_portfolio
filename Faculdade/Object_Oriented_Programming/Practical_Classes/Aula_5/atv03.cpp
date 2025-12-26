#include <iostream>
using namespace std;

int main()
{
    int contador;
    char continua;
    continua = 's';
    contador = 0;

    while (continua == 's' || continua == 'S')
    {
        cout << "repetindo... ";
        contador = contador + 1;
        cout << "Tecle 's' se deseja continuar ou outra tecla para parar. Contagem: " << contador;
        cin >> continua;
    }
    return 0;
}