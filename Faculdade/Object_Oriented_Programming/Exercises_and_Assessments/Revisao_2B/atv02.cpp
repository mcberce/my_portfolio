#include <iostream>
#include <stack>
using namespace std;

int main()
{
    stack<int> pontos;
    int valor = 0;

    cout << "Digite os pontos obtidos em 5 partidas: " << endl;
    for (int i = 0; i < 5; i++)
    {
        cout << "Rodada " << i + 1 << ": ";
        cin >> valor;

        pontos.push(valor);
    }

    cout << "Exibindo do mais recente para o mais antigo: " << endl;
    while (!pontos.empty()) // roda enquanto a pilha nao estiver vazia
    {
        cout << pontos.top() << endl;
        pontos.pop(); // deleta o elemento do topo da pilha
    }

    return 0;
}