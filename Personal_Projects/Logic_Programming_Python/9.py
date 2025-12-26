numero = int(input("Digite um número inteiro positivo: "))

if numero < 1:
	print("Digite um valor maior ou igual a 1.")
else:
	soma = 0
	for i in range(1, numero + 1):
		soma += i
	print(f"A soma de 1 até {numero} é {soma}.")
8