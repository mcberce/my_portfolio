#include <iostream>
using namespace std;

int main()
{
    int i;
    #define TAM_MAX 10
    double vetreais[TAM_MAX], vetcopia[TAM_MAX];

    for (i = 0; i < TAM_MAX; i++)
    {
        vetreais[i] = i;
    }
    for (i = 0; i < TAM_MAX; i++)
    {
        vetcopia[i] = vetreais[i];
    }
    for (i = 0; i < TAM_MAX; i++)
    {
        cout << "O valor na posição " << i << " é: " << vetcopia[i] << endl;
    }

    return 0;
}