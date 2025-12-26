#include <iostream>
#include <queue>
using namespace std;

int main()
{
    queue<string> fila;

    fila.push("João");
    fila.push("Maria");
    fila.push("Carlos");

    cout << "Primeiro da fila: " << fila.front() << endl;

    fila.pop();

    cout << "Agora o primeiro da fila: " << fila.front() << endl;

    return 0;
}