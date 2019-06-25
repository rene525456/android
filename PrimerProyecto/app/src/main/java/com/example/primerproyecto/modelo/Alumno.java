package com.example.primerproyecto.modelo;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private String direccion;

    public Alumno() { }

    public Alumno(int idAlumno, String nombre, String direccion) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}