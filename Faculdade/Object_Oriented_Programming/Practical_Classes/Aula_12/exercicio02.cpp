#include <iostream>
using namespace std;

class Forma {
public:
    virtual double area() = 0; // método virtual puro
    virtual ~Forma() {}
};

class Retangulo : public Forma {
private:
    double largura, altura;
public:
    Retangulo(double l, double a) : largura(l), altura(a) {}

    double area() override {
        return largura * altura;
    }
};

class Circulo : public Forma {
private:
    double raio;
    const double PI = 3.14159; // constante local
public:
    Circulo(double r) : raio(r) {}

    double area() override {
        return PI * raio * raio;
    }
};

int main() {
    Retangulo ret(4.0, 5.0);
    Circulo circ(3.0);

    cout << "Área do Retângulo: " << ret.area() << endl;
    cout << "Área do Círculo: " << circ.area() << endl;

    return 0;
}
