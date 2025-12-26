#include <iostream>
#include <list>
using namespace std;

int main()
{
    list<int> numeros;

    numeros.push_back(10);
    numeros.push_back(20);
    numeros.push_back(30);

    numeros.push_front(5);

    cout << "Elementos da lista: " << endl;
    cout << "Lista: ";
    for (int n : numeros)
    {
        cout << n << " ";
    }
    cout << endl;

    numeros.remove(20);

    cout << "Após remover 20: ";
    for (int n : numeros)
    {
        cout << n << " ";
    }
    cout << endl;

    return 0;
}