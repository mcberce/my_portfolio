#include <iostream>
using namespace std;

void menu()
{
    cout << "1-bm\n";
    cout << "2-mercedes\n";
    cout << "Digite sua opção\n";
}

float valores(int op)
{
    float valor;
    switch (op)
    {
    case 1:
        valor = 50.000;
        break;
    case 2:
        valor = 70.000;
        break;
    default:
        cout << "Digite um valor válido" << endl;
        break;
    }
    return valor;
}

int main()
{
    int op, qtd;
    float valor_individual, total;

    menu();
    cin >> op;
    cout << "Digite a quantidade";
    cin >> qtd;
    valor_individual = valores(op);
    total = valor_individual * qtd;
    cout << "Valor a pagar:" << total;

    return 0;
}