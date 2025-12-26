#include <iostream>
#include <string>
using namespace std;

class pessoa{
    private:
        string nome;
        int idade;
    public:
        void setNome(const string& n){
            nome = n;
        }
        string getNome() const {
            return nome;
        }
        void setIdade(int i){
            idade = i;
        }
        int getIdade() {
            return idade;
        }
    
};

int main(){
    pessoa p;
    p.setNome("João");
    p.setIdade(30);
    
    cout<< "Nome: " << p.getNome() << endl;
    cout<< "Idade: " << p.getIdade() << endl;

    return 0;

}