#include <iostream>
#include <string>
using namespace std;

class Carro {
private:
    string modelo;
    string cor;
    int velocidade;

public:
    // Construtor
    Carro(string modelo, string cor, int velocidade) {
        this->modelo = modelo;
        this->cor = cor;
        this->velocidade = velocidade;
    }

    // Exibir informações do carro
    void exibirInfo() const {
        cout << "Modelo: " << modelo << endl;
        cout << "Cor: " << cor << endl;
        cout << "Velocidade atual: " << velocidade << " km/h" << endl;
    }

    // Função genérica para modificar a velocidade (acelerar ou frear)
    void modificarVelocidade(int ajuste) {
        velocidade += ajuste;
        if (ajuste > 0) {
            cout << "Acelerando... velocidade atual: " << velocidade << " km/h" << endl;
        } else {
            cout << "Freando... velocidade atual: " << velocidade << " km/h" << endl;
        }
    }
};

int main() {
    // Criação de objetos Carro
    Carro carro1("Fusca", "Azul", 0);
    Carro carro2("Gol", "Vermelho", 10);
    Carro carro3("Civic", "Preto", 50);
    Carro bike("Caloi", "Vermelha", 5);

    // Exibindo informações iniciais
    carro1.exibirInfo();
    carro2.exibirInfo();
    carro3.exibirInfo();
    bike.exibirInfo();

    // Menu de interação
    int opcao, ajuste;
    while (true) {
        cout << "\nEscolha uma opção (0 para sair):" << endl;
        cout << "1 - Aumentar velocidade do Fusca" << endl;
        cout << "2 - Diminuir velocidade do Gol" << endl;
        cout << "3 - Aumentar velocidade do Civic" << endl;
        cout << "4 - Diminuir velocidade da Caloi" << endl;
        cout << "Opção: ";
        cin >> opcao;

        if (opcao == 0) break;

        cout << "Digite o valor do ajuste de velocidade: ";
        cin >> ajuste;

        switch (opcao) {
            case 1:
                carro1.modificarVelocidade(ajuste);
                break;
            case 2:
                carro2.modificarVelocidade(-ajuste);
                break;
            case 3:
                carro3.modificarVelocidade(ajuste);
                break;
            case 4:
                bike.modificarVelocidade(-ajuste);
                break;
            default:
                cout << "Opção inválida!" << endl;
        }

        // Exibindo informações atualizadas
        cout << "\nStatus atualizado:" << endl;
        carro1.exibirInfo();
        carro2.exibirInfo();
        carro3.exibirInfo();
        bike.exibirInfo();
    }

    return 0;
}
