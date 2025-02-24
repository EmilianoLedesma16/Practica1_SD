import os
import random

# Crear la carpeta 'archivos' si no existe
folder = "archivos"
os.makedirs(folder, exist_ok=True)

# Palabras para el contenido
palabras = ["error", "solución", "debug", "código", "fallo", "crash", "problema", "fix", "bug", "warning"]

for i in range(1, 11):
    filename = os.path.join(folder, f"archivo{i}.txt")
    
    with open(filename, "w", encoding="utf-8") as f:
        num_lineas = random.randint(50, 100)  # Cada archivo tendrá entre 50 y 100 líneas
        
        for _ in range(num_lineas):
            num_palabras = random.randint(5, 15)  # Entre 5 y 15 palabras por línea
            linea = " ".join(
                random.choice(palabras) if random.random() < 0.2 else random.choice(palabras)
                for _ in range(num_palabras)
            )
            f.write(linea + "\n")
    
    print(f"📄 Archivo generado: {filename}")

print("\n Se han generado 10 archivos con texto aleatorio.")
