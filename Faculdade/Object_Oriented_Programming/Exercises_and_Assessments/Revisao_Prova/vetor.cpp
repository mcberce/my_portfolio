#include <iostream>
using namespace std;

int main()
{
    int vetor[30], raio, i;

    for (i = 1; i <= 30; i++)
    {
        cout << "Digite o raio do peneu:" << endl;
        cin >> raio;

        if ((raio > 13) && (raio < 24))
        {
            vetor[i] = raio;
        }
        else
        {
            cout << "Numero invalido" << endl;
        }
    }

    return 0;
}