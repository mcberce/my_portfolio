#include <iostream>
#include <string>
using namespace std;

int main(){
    string str = "Leticia Favero";
    str.insert(8, "Linda ");
    cout << str << endl;
    str.append("Linda 2 ");
    str.append("Linda 3");
    cout << str << endl;
}