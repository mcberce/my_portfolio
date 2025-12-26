#include"Bveiculo.h"
#include<iostream>
#include<string>
using namespace std;

int main() {
    // Criando objetos de cada tipo de veículo
    Aquatico barco("Yamaha", "242X", 2020, 12);
    Terrestre carro("Toyota", "Corolla", 2021, 4);
    Anfibio anfibio("Amphicar", "770", 1961, 500);

    // Exibindo informações dos veículos
    cout << "Informações do Barco:" << endl;
    barco.exibirInfo();
    
    cout << "\nInformações do Carro:" << endl;
    carro.exibirInfo();
    
    cout << "\nInformações do Anfíbio:" << endl;
    anfibio.exibirInfo();

    return 0;
}