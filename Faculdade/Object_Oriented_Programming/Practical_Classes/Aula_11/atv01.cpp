#include <iostream>
#include <string>
using namespace std;

class Carro
{
private: // encapsulamento
    string modelo;
    string cor;
    int velocidade;

public:
    Carro(string modelo, string cor, int velocidade) // objeto construtor
    {
        this->modelo = modelo;
        this->cor = cor;
        this->velocidade = velocidade;
    }
    void exibirInfo()
    {
        cout << "modelo " << this->modelo << endl;
        cout << "cor " << this->cor << endl;
        cout << "velocidade " << this->velocidade << endl;
    }

    void aumentaVelo(int incremento)
    {
        this->velocidade += incremento;
        cout << "Acelerando... velocidade atual:  " << this->velocidade << endl;
    }

    void reduzVelo(int decremento)
    {
        this->velocidade -= decremento;
        cout << "Freando... velocidade atual:  " << this->velocidade << endl;
    }
};

int main()
{
    int incremento;
    int decremento;

    // uso dos objetos
    Carro carro1("Fusca", "Azul", 0);
    Carro carro2("Gol", "Vermaleho", 10);
    Carro carro3("Civic", "Preto", 50);
    Carro bike("Caloi", "Vermelha", 5);

    carro1.exibirInfo();
    carro2.exibirInfo();
    carro3.exibirInfo();
    bike.exibirInfo();

    return 0;
}