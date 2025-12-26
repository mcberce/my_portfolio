num1 = float(input("Digite um número: "))
num2 = float(input("Digite outro número: "))

if num1 < num2:
    print(f"O número {num2} é maior que {num1}!")
elif num1 > num2:
    print(f"O número {num1} é maior que {num2}!")
else:
    print("Os números são iguais.")