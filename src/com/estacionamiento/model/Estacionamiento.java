package com.estacionamiento.model;

import java.util.ArrayList;
import java.util.List;

public class Estacionamiento {
    private Espacio[] espacios;
    private List<Ticket> ticketsActivos;
    private List<Ticket> historialTickets;

    public Estacionamiento() {
        espacios = new Espacio[20];
        // 15 Carros (1-15)
        for (int i = 0; i < 15; i++) {
            espacios[i] = new Espacio(i + 1, "Carro");
        }
        // 5 Motos (16-20)
        for (int i = 15; i < 20; i++) {
            espacios[i] = new Espacio(i + 1, "Moto");
        }
        ticketsActivos = new ArrayList<>();
        historialTickets = new ArrayList<>();
    }

    public Ticket registrarEntrada(Vehiculo v) throws Exception {
        if (v.getPlaca() == null || v.getPlaca().trim().isEmpty()) {
            throw new Exception("La placa no puede estar vacía.");
        }

        for (Ticket t : ticketsActivos) {
            if (t.getVehiculo().getPlaca().equalsIgnoreCase(v.getPlaca())) {
                throw new Exception("El vehículo con placa " + v.getPlaca() + " ya está estacionado.");
            }
        }

        Espacio disponible = null;
        for (Espacio e : espacios) {
            if (e.getTipo().equalsIgnoreCase(v.getTipo()) && e.isDisponible()) {
                disponible = e;
                break;
            }
        }

        if (disponible == null) {
            throw new Exception("No hay espacio disponible para: " + v.getTipo());
        }

        disponible.ocupar(v);
        Ticket nuevoTicket = new Ticket(v, disponible);
        ticketsActivos.add(nuevoTicket);
        return nuevoTicket;
    }

    public Ticket registrarSalida(String placa, double costo) throws Exception {
        Ticket ticketEncontrado = null;
        for (Ticket t : ticketsActivos) {
            if (t.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                ticketEncontrado = t;
                break;
            }
        }

        if (ticketEncontrado == null) {
            throw new Exception("Vehículo no encontrado en el estacionamiento.");
        }

        ticketEncontrado.registrarSalida(costo);
        ticketEncontrado.getEspacio().liberar();
        ticketsActivos.remove(ticketEncontrado);
        historialTickets.add(ticketEncontrado);
        return ticketEncontrado;
    }

    public Espacio[] getEspacios() {
        return espacios;
    }

    public List<Ticket> getTicketsActivos() {
        return ticketsActivos;
    }

    public List<Ticket> getHistorialTickets() {
        return historialTickets;
    }
}
