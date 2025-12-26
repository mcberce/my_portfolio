#ifndef BVEICULO_H
#define BVEICULO_H
#include<iostream>
#include<string>
using namespace std;

class Veiculo{
private:
    string marca;
    string modelo;
    int ano;

public:
    Veiculo(string marca, string modelo, int ano);

    void exibirInfo() const {
        cout << "Marca: " << marca << ", Modelo: " << modelo << ", Ano: " << ano << endl;
    }
};

class Aquatico : public Veiculo {
private:
    int capacidadePassageiros;

public:
    Aquatico(string marca, string modelo, int ano, int capacidadePassageiros)
        : Veiculo(marca, modelo, ano), capacidadePassageiros(capacidadePassageiros) {}

    void exibirInfo() const {
        Veiculo::exibirInfo();
        cout << "Capacidade de Passageiros: " << capacidadePassageiros << endl;
    }
};

class Terrestre : public Veiculo {
private:
    int numeroRodas;

public:
    Terrestre(string marca, string modelo, int ano, int numeroRodas)
        : Veiculo(marca, modelo, ano), numeroRodas(numeroRodas) {}

    void exibirInfo() const {
        Veiculo::exibirInfo();
        cout << "Número de Rodas: " << numeroRodas << endl;
    }
};

class Anfibio : public Veiculo {
private:
    int capacidadeCarga;

public:
    Anfibio(string marca, string modelo, int ano, int capacidadeCarga)
        : Veiculo(marca, modelo, ano), capacidadeCarga(capacidadeCarga) {}

    void exibirInfo() const {
        Veiculo::exibirInfo();
        cout << "Capacidade de Carga: " << capacidadeCarga << endl;
    }
};
#endif // BVEICULO_H