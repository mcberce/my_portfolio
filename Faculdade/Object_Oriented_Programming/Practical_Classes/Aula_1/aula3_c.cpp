#include <iostream>
using namespace std;

int main(){
    int x = 10, y = 2, z = 0;
    cout << "o valor de x = " << x << "\n";
    cout << "o valor de y = " << y << "\n";
    cout << "troquei de valores\n";
    z = y;
    y = x;
    x = z;
    cout << "o valor de x = " << x << "\n";
    cout << "o valor de y = " << y << "\n";
    x = x * -1; //trocando valores atraves de calculo
    cout << "o valor de x negativo = " << x << "\n";

    return 0;
}