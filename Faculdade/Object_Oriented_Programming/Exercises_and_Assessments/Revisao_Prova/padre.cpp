#include <iostream>
using namespace std;

int main()
{
    int numalunos, qtd1, qtd2, i;
    float media;

    cout << "Digite a qtd de alunos:" << endl;
    cin >> numalunos;

    for (i = 1; i <= numalunos; i++)
    {
        cout << "Digite a qtd1:" << endl;
        cin >> qtd1;
        cout << "Digite a qtd2:" << endl;
        cin >> qtd2;

        media = qtd1 + qtd2 / 2;

        cout << "A media é:" << media << endl;
    }

    return 0;
}