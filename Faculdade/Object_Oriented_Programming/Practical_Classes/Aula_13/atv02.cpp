#include <iostream>
#include <string>
using namespace std;

// Classe base
class Animal
{
protected:
    string nome;
    int idade;

public:
    Animal()
    {
        nome = "";
        idade = 0;
    }

    virtual void lerDados()
    {
        cout << "Digite o nome do animal: ";
        getline(cin, nome);
        cout << "Digite a idade do animal: ";
        cin >> idade;
        cin.ignore(); // limpar buffer
    }

    virtual void mostrarDados() const
    {
        cout << "Nome: " << nome << endl;
        cout << "Idade: " << idade << " anos" << endl;
    }

    virtual ~Animal() {} // destrutor virtual
};

// Classe derivada Cachorro
class Cachorro : public Animal
{
private:
    string raca;

public:
    Cachorro() : Animal()
    {
        raca = "";
    }

    void lerDados() override
    {
        Animal::lerDados();
        cout << "Digite a raca do cachorro: ";
        getline(cin, raca);
    }

    void mostrarDados() const override
    {
        Animal::mostrarDados();
        cout << "Raca: " << raca << endl;
    }
};

// Classe derivada Gato
class Gato : public Animal
{
private:
    string cor;

public:
    Gato() : Animal()
    {
        cor = "";
    }

    void lerDados() override
    {
        Animal::lerDados();
        cout << "Digite a cor do gato: ";
        getline(cin, cor);
    }

    void mostrarDados() const override
    {
        Animal::mostrarDados();
        cout << "Cor: " << cor << endl;
    }
};

int main()
{
    Cachorro cachorro;
    Gato gato;

    cout << "--- Cadastro do Cachorro ---" << endl;
    cachorro.lerDados();

    cout << "\n--- Cadastro do Gato ---" << endl;
    gato.lerDados();

    cout << "\n--- Dados do Cachorro ---" << endl;
    cachorro.mostrarDados();

    cout << "\n--- Dados do Gato ---" << endl;
    gato.mostrarDados();

    return 0;
}
