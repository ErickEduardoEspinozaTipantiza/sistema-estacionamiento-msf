# Pruebas

## Resumen

- Pruebas ejecutadas en Azure DevOps.
- Responsable: Nathaly Gaibor
- Fecha: 22-04-2026.

## Enlaces y evidencia

- Work Items (Azure DevOps): https://dev.azure.com/lizethhernandez/Sistema%20Estacionamiento/_backlogs/backlog/Sistema%20Estacionamiento%20Team/Issues
- Evidencias: las capturas estan adjuntas en cada Work Item.


## Casos de prueba

| ID | Caso | Entrada | Resultado esperado | Resultado obtenido | Estado | Evidencia |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Registro de entrada exitoso | Placa: ABC-123, Tipo: Carro | Asigna espacio y muestra ticket | | | |
| 2 | Registro de salida exitoso | Placa: ABC-123 | Calcula costo y libera espacio | | | |
| 3 | Estacionamiento lleno | 20 vehiculos ya registrados | Mensaje: No hay espacios disponibles | | | |
| 4 | Placa duplicada en entrada | Placa: ABC-123 ya dentro | Mensaje: vehiculo ya en estacionamiento | | | |
| 5 | Placa inexistente en salida | Placa: XYZ-999 | Mensaje: no se encontro el vehiculo | | | |
| 6 | Tarifa moto minima | Moto, 30 min | Cobra 1 hora = $0.25 | | | |
| 7 | Fraccion de hora | Carro, 1h 10min | Cobra 2 horas = $1.00 | | | |
| 8 | Estado con espacios libres | 5 vehiculos registrados | Muestra 5 ocupados y 15 libres | | | |
| 9 | Historial del dia vacio | Sin movimientos | Mensaje: No hay registros hoy | | | |
| 10 | Total recaudado correcto | Varios cobros | Suma correcta del dia | | | |
