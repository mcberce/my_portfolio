#include <iostream>
using namespace std;

int main()
{
    #define NLIN 5
    #define NCOL 5

    int i, j, matriz[NLIN][NCOL];

    for (i = 0; i < NLIN; i++)
    {
        for (j = 0; j < NCOL; j++)
        {
            matriz[i][j] = i * j;
            cout << "Digite o número de linhas dps o número de colunas: " << i << j << endl;
            cin >> matriz[i][j];
        }
    }
    for (i = 0; i < NLIN; i++)
    {
        for (j = 0; j < NCOL; j++)
        {
            cout << "O valor guardado na linha " << i << " coluna " << j << " é: " << matriz[i][j] << endl;
        }
    }
}