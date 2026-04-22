package com.estacionamiento.model;

public class Espacio {
    private int numero;
    private String tipo;
    private boolean disponible;
    private Vehiculo vehiculo;

    public Espacio(int numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.disponible = true;
        this.vehiculo = null;
    }

    public void ocupar(Vehiculo v) {
        this.vehiculo = v;
        this.disponible = false;
    }

    public void liberar() {
        this.vehiculo = null;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }
}
