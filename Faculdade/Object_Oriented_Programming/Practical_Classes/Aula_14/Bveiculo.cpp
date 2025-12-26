#include"Bveiculo.h"
#include<iostream>
#include<string>
using namespace std;

Veiculo::Veiculo(string marca, string modelo, int ano) {
    this->marca = marca;
    this->modelo = modelo;
    this->ano = ano;
}
void Veiculo::exibirInfo() const {
    cout << "Marca: " << marca << ", Modelo: " << modelo << ", Ano: " << ano << endl;
}

Aquatico::Aquatico(string marca, string modelo, int ano, int capacidadePassageiros)
    : Veiculo(marca, modelo, ano), capacidadePassageiros(capacidadePassageiros) {

}
void Aquatico::exibirInfo() const {
    Veiculo::exibirInfo();
    cout << "Capacidade de Passageiros: " << capacidadePassageiros << endl;
}

Terrestre::Terrestre(string marca, string modelo, int ano, int numeroRodas)
    : Veiculo(marca, modelo, ano), numeroRodas(numeroRodas) {

}
void Terrestre::exibirInfo() const {
    Veiculo::exibirInfo();
    cout << "Número de Rodas: " << numeroRodas << endl;
}

Anfibio::Anfibio(string marca, string modelo, int ano, int capacidadeCarga)
    : Veiculo(marca, modelo, ano), capacidadeCarga(capacidadeCarga) {

}
void Anfibio::exibirInfo() const {
    Veiculo::exibirInfo();
    cout << "Capacidade de Carga: " << capacidadeCarga << endl;
}