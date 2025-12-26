#include <iostream>
#include <stack>
using namespace std;

int main()
{
    stack<int> pilha;

    pilha.push(10);
    pilha.push(20);
    pilha.push(30);

    cout << "topo da fila: " << pilha.top() << endl;

    pilha.pop();

    cout << "Agora topo da fila: " << pilha.top() << endl;

    return 0;
}