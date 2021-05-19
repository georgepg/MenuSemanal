package com.example.menusemanal.Objeto;

public class Comida {
    String Nombre;

    public Comida(String nombre) {
        Nombre = nombre;
    }

    public Comida() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
