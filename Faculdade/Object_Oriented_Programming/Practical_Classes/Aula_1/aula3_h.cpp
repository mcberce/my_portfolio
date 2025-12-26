#include <iostream>
using namespace std;

int main()
{
    int dia_semana;

    cout << "Digite o dia da semana (1 a 7): ";
    cin >> dia_semana;

    switch (dia_semana)
    {
    case 1:
        cout << "domingo";
        break;
    case 2:
        cout << "segunda";
        break;
    case 3:
        cout << "terça";
        break;
    case 4:
        cout << "quarta";
        break;
    case 5:
        cout << "quinta";
        break;
    case 6:
        cout << "sexta";
        break;
    case 7:
        cout << "sábado";
        break;
    default:
        cout << "este dia nao existe";
    }

    return 0;
}
