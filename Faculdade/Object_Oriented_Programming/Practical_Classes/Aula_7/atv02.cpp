#include <iostream>
#include <queue>
#include <string>
using namespace std;

struct pessoa
{
    string nome;
    int idade;
    float altura;
};

int main()
{
    pessoa p;

    cout << ("Digite o nome: ") << endl;
    getline(cin, p.nome);

    cout << ("Digite a idade: ") << endl;
    cin >> p.idade;

    cout << ("Digite a aultua: ") << endl;
    cin >> p.altura;

    cout << ("\nDados da pessoa: \n");
    cout << ("Nome: ") << p.nome << endl;
    cout << ("Idade: ") << p.idade << endl;
    cout << ("Altura: ") << p.altura << endl;
}