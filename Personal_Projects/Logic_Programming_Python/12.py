numeros = []

for i in range(1, 6):
	valor = float(input(f"Digite o {i}º número: "))
	numeros.append(valor)

menor = min(numeros)
maior = max(numeros)

print(f"Menor número: {menor}")
print(f"Maior número: {maior}")
