#include <iostream>
using namespace std;

int main()
{
    float n1, n2, media;
    cout << "Digite a nota 1";
    cin >> n1;
    cout << "Digite a nota 2";
    cin >> n2;

    media = (n1 + n2) / 2;

    if (media >= 6)
    {
        cout << "Aprovado";
    }
    else
    {
        cout << "Reprovado";
    }

    return 0;
}