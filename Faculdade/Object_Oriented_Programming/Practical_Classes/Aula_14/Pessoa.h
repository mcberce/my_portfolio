#ifndef PESSOA_H
#define PESSOA_H
#include<iostream>
#include<string>

class Pessoa{
    private:
        std::string nome;
        int idade;

    public:
        Pessoa(std::string nome, int idade);
        void apresentar();
};
#endif // PESSOA_H