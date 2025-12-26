#ifndef VEICULO_H
#define VEICULO_H
#include <iostream>
#include <string>

class Veiculo {
    private:
        std::string modelo;
        int ano;

    public:
        Veiculo(std::string modelo, int ano);
        void exibirDados();
};
#endif // VEICULO_H