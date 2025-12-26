#include <iostream>
#include <string>
using namespace std;

// classe base
class Pessoa
{
protected:
    string nome;
    int idade;

public:
    void lerDados()
    {
        cout << "Digite o nome: ";
        getline(cin, nome);
        cout << "Digite a idade: ";
        cin >> idade;
        cin.ignore(); // Limpar buffer
    }

    void mostrarDados()
    {
        cout << "Nome: " << nome << endl;
        cout << "Idade: " << idade << " anos" << endl;
    }
};

// classe derivada
class Aluno : public Pessoa
{
private:
    string matricula;

public:
    void lerAluno()
    {
        lerDados();
        cout << "Digite a matricula: ";
        getline(cin, matricula);
    }

    void mostrarAluno()
    {
        mostrarDados();
        cout << "Matricula " << matricula << endl;
    }
};

// classe derivada professor
class Professor : public Pessoa
{
private:
    float salario;

public:
    void lerProfessor()
    {
        lerDados();
        cout << "Digite o salario ";
        cin >> salario;
        cin.ignore(); // limpar buffer
    }

    void mostrarProfessor()
    {
        mostrarDados();
        cout << "Salario " << salario << endl;
    }
};

int main()
{
    Aluno a;
    Professor p;

    cout << "--- Cadastro de aluno ---" << endl;
    a.lerAluno();
    cout << "\n--- Dados do aluno ---" << endl;
    a.mostrarAluno();

    cout << "\n--- Cadastro de professor ---" << endl;
    p.lerProfessor();
    cout << "\n--- Dados do professor ---" << endl;
    p.mostrarProfessor();

    return 0;
}