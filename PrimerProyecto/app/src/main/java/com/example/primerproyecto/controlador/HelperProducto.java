package com.example.primerproyecto.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.primerproyecto.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class HelperProducto extends SQLiteOpenHelper {
    public HelperProducto(Context context) {
        super(context, "bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE producto(id INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT UNIQUE, nombre TEXT, " +
                "descripcion TEXT, precio_unitario DOUBLE, existencia INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertar(Producto producto){
        ContentValues values = new ContentValues();
        values.put("codigo", producto.getCodigo());
        values.put("nombre", producto.getNombre());
        values.put("descripcion", producto.getDescripcion());
        values.put("precio_unitario", producto.getPrecio());
        values.put("existencia", producto.getExistencia());
        this.getWritableDatabase().insert("producto", null, values);
    }

    public void modificar(Producto producto){
        ContentValues values = new ContentValues();
        values.put("nombre",producto.getNombre());
        values.put("descripcion", producto.getDescripcion());
        values.put("precio_unitario", producto.getPrecio());
        values.put("existencia", producto.getExistencia());
        this.getWritableDatabase().update("producto",values,"codigo='" + producto.getCodigo() +"'",null);
    }

    public String leerTodos(){
        String consulta = "";
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM producto",null);
        if (cursor.moveToFirst()){
            do{
                String cod = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                int existencia = cursor.getInt(cursor.getColumnIndex("existencia"));
                double precio  = cursor.getDouble(cursor.getColumnIndex("precio_unitario"));
                //consulta += cod + " " + nombre + " " + descripcion + " " + existencia + " " + precio + "\n";
                consulta += cod + " " + nombre + " " + descripcion + " " + existencia + " " + precio +"\n";
            }while (cursor.moveToNext());
        }
        cursor.close();
        return consulta;
    }


    public String leerPorCodigo(String codigo){
        String consulta = null;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM producto where codigo = '" + codigo + "'",null);
        if (cursor.moveToFirst()){
            do{
                String cod = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                int existencia = cursor.getInt(cursor.getColumnIndex("existencia"));
                double precio  = cursor.getDouble(cursor.getColumnIndex("precio_unitario"));
                consulta += cod + " " + nombre + " " + descripcion + " " + existencia + " " + precio + "\n";
            }while (cursor.moveToNext());
        }
        cursor.close();
        return consulta;
    }

    public List<Producto> getAllProductos(){
        List <Producto> lista = new ArrayList<Producto>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM producto",null);
        if (cursor.moveToFirst()){
            do{
                Producto producto = new Producto();
                producto.setCodigo(cursor.getString(cursor.getColumnIndex("nombre")));
                producto.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                producto.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
                producto.setExistencia(cursor.getInt(cursor.getColumnIndex("existencia")));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex("precio_unitario")));
                lista.add(producto);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}






