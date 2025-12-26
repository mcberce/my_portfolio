#include <iostream>
using namespace std;

int main(){
    int n1, n2, n3;
    int valorproduzido;
    cout << "Digite a nota 1";
    cin >> n1;
    cout << "Digite a nota 2";
    cin >> n2;
    n1++;
    n2--;
    cout << "O valor de nota1 foi alterado para " << n1 << "\n";
    cout << "O valor de nota2 foi alterado para " << n2 << "\n";
    n3 = 10;
    cout << "O valor de n3 = " << n3 << "\n";
    valorproduzido = 2 * (n3++); //2 * 10 depois soma 1 e vira 11
    cout << "O valor de n3++ " << n3 << "\n";
    cout << "O valor produzido ++n " << valorproduzido << "\n";
    valorproduzido = 2 * (++n3); //2 * (11+1)
    cout << "O valor de n3++ " << n3 << "\n";
    cout << "O valor produzido ++n " << valorproduzido << "\n";

    return 0;
}