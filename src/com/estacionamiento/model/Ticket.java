package com.estacionamiento.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private Vehiculo vehiculo;
    private Espacio espacio;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private double costo;

    public Ticket(Vehiculo vehiculo, Espacio espacio) {
        this.vehiculo = vehiculo;
        this.espacio = espacio;
        this.horaEntrada = LocalTime.now();
        this.costo = 0.0;
    }

    public void registrarSalida(double costo) {
        this.horaSalida = LocalTime.now();
        this.costo = costo;
    }

    public String imprimirTicket() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("       TICKET PARKING         \n");
        sb.append("==============================\n");
        sb.append("Placa:    ").append(vehiculo.getPlaca()).append("\n");
        sb.append("Tipo:     ").append(vehiculo.getTipo()).append("\n");
        sb.append("Espacio:  #").append(espacio.getNumero()).append("\n");
        sb.append("Entrada:  ").append(horaEntrada.format(formatter)).append("\n");
        if (horaSalida != null) {
            sb.append("Salida:   ").append(horaSalida.format(formatter)).append("\n");
            sb.append("Costo:    $").append(String.format("%.2f", costo)).append("\n");
        } else {
            sb.append("Estado:   ACTIVO\n");
        }
        sb.append("==============================\n");
        return sb.toString();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public double getCosto() {
        return costo;
    }
}
