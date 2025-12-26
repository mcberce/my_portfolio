#include <iostream>
#include <list>
using namespace std;

int main()
{
    list<int> pontos; // lista
    int entrada, total = 0;

    cout << "Digite os pontos obtidos em 5 partidas (0 para sair): ";
    for (int i = 0; i < 5; i++)
    {
        cout << "Rodada " << i + 1 << ": ";
        cin >> entrada;

        pontos.push_back(entrada); // adiciona o ponto à lista
        total += entrada;          // soma os pontos
    }

    cout << "Total de pontos: " << total << endl;
    cout << "Média de pontos: " << static_cast<float>(total) / pontos.size() << endl;

    return 0;
}