#include <iostream>
#include <string>

int main(){
    std::string str = "Celia, Kouth!";
    
    //posição 7 altera 5 letras
    str.replace(7, 5, "Cabral");
    std::cout << str << std::endl; //saída: celia, cabral!

    //desafio 1
    str.replace(7, 6, "me ajuda");
    std::cout << str << std::endl;

    return 0;
}