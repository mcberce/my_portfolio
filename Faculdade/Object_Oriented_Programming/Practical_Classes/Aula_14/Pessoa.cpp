#include"Pessoa.h"
#include<iostream>
#include<string>

Pessoa::Pessoa(std::string nome, int idade){
    this->nome = nome;
    this->idade = idade;
};

void Pessoa::apresentar(){
    std::cout << "Nome: " << nome << std::endl;
    std::cout << "Idade: " << idade << std::endl;
}