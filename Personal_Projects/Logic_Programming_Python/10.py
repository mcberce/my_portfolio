soma = 0.0

while True:
	valor = float(input("Digite um número (0 para parar): "))
	if valor == 0:
		break
	soma += valor

print(f"A soma dos números é {soma}")
