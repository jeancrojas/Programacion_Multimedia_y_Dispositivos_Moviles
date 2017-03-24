package com.example.david.parserxml;

/**
 * Created by david on 7/3/17.
 */

public class Parte {
    Long id;
    String nombre;
    String fabircante;
    String modelo;
    double precio;

    public  Parte(){

    }

    public Parte(Long id,String nombre, String fabircante, String modelo, double precio) {
        this.nombre = nombre;
        this.fabircante = fabircante;
        this.modelo = modelo;
        this.precio = precio;
        this.id=id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabircante() {
        return fabircante;
    }

    public void setFabircante(String fabircante) {
        this.fabircante = fabircante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
