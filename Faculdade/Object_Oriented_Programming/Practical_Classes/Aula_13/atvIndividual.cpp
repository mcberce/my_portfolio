#include <iostream>
#include <string>
using namespace std;

// Classe base
class Entidade
{
protected:
    int id;

public:
    void lerID()
    {
        cout << "Digite o ID: ";
        cin >> id;
        cin.ignore(); // limpar buffer
    }

    void mostrarID() const
    {
        cout << "ID: " << id << endl;
    }
};

// Classe derivada Cliente
class Cliente : public Entidade
{
private:
    string nome;
    string cpf;

public:
    void cadastrarCliente()
    {
        lerID();
        cout << "Digite o nome do cliente: ";
        getline(cin, nome);
        cout << "Digite o CPF do cliente: ";
        getline(cin, cpf);
    }

    void exibirCliente() const
    {
        mostrarID();
        cout << "Nome: " << nome << endl;
        cout << "CPF: " << cpf << endl;
    }
};

// Classe derivada Veículo
class Veiculo : public Entidade
{
private:
    string modelo;
    string placa;

public:
    void cadastrarVeiculo()
    {
        lerID();
        cout << "Digite o modelo do veiculo: ";
        getline(cin, modelo);
        cout << "Digite a placa do veiculo: ";
        getline(cin, placa);
    }

    void exibirVeiculo() const
    {
        mostrarID();
        cout << "Modelo: " << modelo << endl;
        cout << "Placa: " << placa << endl;
    }
};

int main()
{
    Cliente cliente;
    Veiculo veiculo;

    cout << "--- Cadastro de Cliente ---" << endl;
    cliente.cadastrarCliente();

    cout << "\n--- Cadastro de Veículo ---" << endl;
    veiculo.cadastrarVeiculo();

    cout << "\n--- Dados do Cliente ---" << endl;
    cliente.exibirCliente();

    cout << "\n--- Dados do Veículo ---" << endl;
    veiculo.exibirVeiculo();

    return 0;
}
