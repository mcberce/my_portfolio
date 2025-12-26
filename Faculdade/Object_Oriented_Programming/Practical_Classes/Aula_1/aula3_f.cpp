#include <iostream>
using namespace std;

int main(){
    float n1, n2, media = 0;
    int faltas;
    cout << "Digite as faltas" << endl;
    cin >> faltas;
    cout << "Digite a nota 1" << endl;
    cin >> n1;
    cout << "Digite a nota 2" << endl;
    cin >> n2;

    media = (n1 + n2)/2;

    if (faltas > 180){
        cout << "Reprovado";
    }
    else{
        if (media >= 60){
            cout << "Aprovado";
        }
        else{
            cout << "Reprovado";
        }
    }

    return 0;
}