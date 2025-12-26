#include <iostream>
#include <string>
using namespace std;

int main() {
    string nome = "célia";
    string letra = "a";
    string sobrenome = " kouth cabral";

    cout << "meu nome é: " << nome << endl;

    // append para juntar strings
    cout << "nome completo " << (nome.append(sobrenome)) << endl;

    // length para contar tamanho da string
    cout << "tamanho " << (nome.length()) << endl;
    // também da certo o nome.size()

    cout << "\nPrimeira letra do nome: " << nome[0]; // mostrando a primeira letra

    nome[0] = 'T'; // alterando a primeira letra do nome

    cout << "\nmeu nome agora é: " << nome << endl;

    return 0;
}
