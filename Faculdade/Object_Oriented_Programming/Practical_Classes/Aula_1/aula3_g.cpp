#include <iostream>
using namespace std;

int main()
{
    int n1, n2, media = 0, faltas, idade;
    bool aprova, emancipado;

    cout << "Digite qtd faltas: ";
    cin >> faltas;

    cout << "Digite a Nota 1: ";
    cin >> n1;

    cout << "Digite a Nota 2: ";
    cin >> n2;

    media = (n1 + n2) / 2;

    // if obrigatório parênteses
    if ((faltas < 180) && (media >= 6))
    { // usando AND
        cout << "aprovado";
    }
    else
    {
        cout << "aprova conselho (0 nao 1 sim) ";
        cin >> aprova;

        if (!(aprova))
        { // usando DIFERENTE ou NOT negando o valor e usando boolean
            cout << "reprovado por conselho de classe";
        }
        else
        {
            cout << "Aluno aprovado";
        }
    }

    cout << "Digite a idade";
    cin >> idade;
    cout << "Digite se for emancipado (1 sim, 0 não)";
    cin >> emancipado;

    if ((idade >= 18) || (emancipado))
    {
        cout << "Você pode retirar o boletim";
    }
    else
    {
        cout << "Os responsaveis devem retirar o boletim";
    }
    if (idade >= 18)
    {
        cout << "a partir deste mes o boleto segue em seu nome...";
    }

    return 0;
}