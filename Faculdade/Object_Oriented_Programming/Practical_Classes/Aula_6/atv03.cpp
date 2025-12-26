#include <iostream>
using namespace std;

int main()
{
    int i;
    #define TAM_MAX 10
    double vetreais[TAM_MAX];

    for (i = 0; i < TAM_MAX; i++)
    {
        vetreais[i] = TAM_MAX - i;
    }

    for (i = 0; i < TAM_MAX; i++)
    {
        cout << "O valor alocado na posição " << i << " é: " << vetreais[i] << endl;
    }

    return 0;
}