#include <iostream>
using namespace std;

int main()
{
    float lado, areaq, base, altura, arear;

    cout << "Digite o lado do quadrado: entre 1 e 50" << endl;
    cin >> lado;
    // Coloque aqui os testes de programação defesiva
    if ((lado < 1) || (lado > 50))
    {
        cout << "Valor inválido" << endl;
        exit;
    }
    else
    {
        areaq = lado * lado;
        cout << "A área do quadrado é:" << areaq << endl;
        cout << "----------------------------------------------" << endl;
    }

    cout << "Digite a base do retangulo: entre 1 e 50" << endl;
    cin >> base;
    cout << "Digite a altura do retangulo entre 1 e 50" << endl;
    cin >> altura;
    // desafio - coloque aqui os testes de proogramação defensiva
    if ((base < 5) || (base > 50) || (altura < 5) || (altura > 50) || (base = altura))
    {
        cout << "Digite um valor válido" << endl;
        exit;
    }
    else
    {
        arear = (base * altura);
        cout << "A área do retangulo é:" << arear << endl;
    }
}