#include"Veiculo.h"
#include<iostream>

Veiculo::Veiculo(std::string modelo, int ano) {
    this->modelo = modelo;
    this->ano = ano;
}

void Veiculo::exibirDados() {
    std::cout << "Modelo: " << modelo << std::endl;
    std::cout << "Ano: " << ano << std::endl;
}