#include <iostream>
using namespace std;

class Funcionario {
public:
    virtual double calcularSalario() = 0; // Método virtual puro
    virtual ~Funcionario() {}
};

class Gerente : public Funcionario {
private:
    double salarioBase;
    double bonus;
public:
    Gerente(double salarioBase, double bonus) : salarioBase(salarioBase), bonus(bonus) {}
    
    double calcularSalario() override {
        return salarioBase + bonus;
    }
};

class Vendedor : public Funcionario {
private:
    double salarioBase;
    double comissao;
    int vendas;
public:
    Vendedor(double salarioBase, double comissao, int vendas)
        : salarioBase(salarioBase), comissao(comissao), vendas(vendas) {}

    double calcularSalario() override {
        return salarioBase + (comissao * vendas);
    }
};

// Exemplo de uso
int main() {
    Gerente gerente(5000, 1500);
    Vendedor vendedor(2000, 100, 10);

    cout << "Salario do Gerente: R$ " << gerente.calcularSalario() << endl;
    cout << "Salario do Vendedor: R$ " << vendedor.calcularSalario() << endl;

    return 0;
}
