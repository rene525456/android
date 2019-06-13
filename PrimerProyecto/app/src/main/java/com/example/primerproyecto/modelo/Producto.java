package com.example.primerproyecto.modelo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="producto")
public class Producto extends Model {
    @Column(name = "codigo", unique = true)
    private String codigo;
    @Column(name = "nombre", notNull = true)
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "existencia", notNull = true)
    private int existencia;
    @Column(name = "precio_unitario", notNull = true)
    private double precio;

    public Producto() {
        super();
    }

    public Producto(String codigo, String nombre, String descripcion, int existencia, double precio) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.precio = precio;
    }

    public static List<Producto> getAll(){
        return new Select().from(Producto.class).execute();
    }

    public static Producto getProducto(String cod){
        return new Select().from(Producto.class).where("codigo=?",cod).executeSingle();
    }



    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + "," + codigo + "," + existencia + "," + precio + ";";
    }
}
