#include "Pessoa.h"
#include "Veiculo.h"
#include <iostream>
#include <string>

int main() {
    // Criando um objeto da classe Pessoa
    Pessoa p("João", 30);
    p.apresentar();

    // Criando um objeto da classe Veiculo
    Veiculo v("Fusca", 1975);
    v.exibirDados(); // Corrigido para chamar o método do objeto correto

    return 0;
}