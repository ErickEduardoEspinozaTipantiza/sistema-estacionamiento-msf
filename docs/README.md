# Documentacion

## Estrategia de ramas (Release Manager)

Se adopto una estrategia de ramas simple para asegurar estabilidad del release y trazabilidad de cambios. La idea es aislar el desarrollo por modulo, integrar en un punto comun y liberar solo versiones estables.

- main: rama estable, solo recibe releases verificados.
- develop: integracion previa al release, permite validar el sistema completo.
- feature/entrada: cambios del modulo de entrada.
- feature/salida: cambios del modulo de salida.
- feature/historial: cambios del modulo de historial.

## Releases

El release se versiono y se empaqueto como ejecutable para garantizar instalacion y ejecucion consistentes.

- Tag: v1.0.0
- Ejecutable: /release/Estacionamiento-v1.0.0.jar
