import random

n_int = random.randint(0, 10)

while True:
    num = int(input("digite um número inteiro: "))
    if n_int == num:
        print("Você acertou!")
        break
    elif num < n_int:
        print(f"o número {num} é menor que o alvo")
    else:
        print(f"o número {num} é maior que o alvo")
