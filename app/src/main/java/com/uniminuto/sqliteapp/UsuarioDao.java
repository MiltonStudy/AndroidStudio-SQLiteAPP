package com.uniminuto.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UsuarioDao {

    private GestionDB gestionDB;
    Context context;
    View view;
    Usuario usuario;

    public UsuarioDao(Context context, View view) {
        this.context = context;
        this.view = view;
        gestionDB = new GestionDB(this.context);
    }

    public void insertarUsuario(Usuario usuario) {
        try {
            SQLiteDatabase db = gestionDB.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("USU_DOCUMENTO", usuario.getDocumento());
                values.put("USU_USUARIO", usuario.getUsuario());
                values.put("USU_NOMBRES", usuario.getNombres());
                values.put("USU_APELLIDOS", usuario.getApellidos());
                values.put("USU_CONTRASENA", usuario.getContrasena());

                long response = db.insert("usuarios", null, values);

                Snackbar.make(this.view, "Se registro el usuario correctamente", Snackbar.LENGTH_LONG).show();

                db.close();
            } else {
                Snackbar.make(this.view, "No se puedo registrar el usuario", Snackbar.LENGTH_LONG).show();
            }
        } catch (SQLException sqlException) {
            Log.i("DB", "Error: " + sqlException);
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            SQLiteDatabase db = gestionDB.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("USU_DOCUMENTO", usuario.getDocumento());
                values.put("USU_USUARIO", usuario.getUsuario());
                values.put("USU_NOMBRES", usuario.getNombres());
                values.put("USU_APELLIDOS", usuario.getApellidos());
                values.put("USU_CONTRASENA", usuario.getContrasena());

                long response = db.update("usuarios", values, "USU_DOCUMENTO == " + usuario.documento, null);

                Snackbar.make(this.view, "Se actualizo el usuario correctamente", Snackbar.LENGTH_LONG).show();

                db.close();
            } else {
                Snackbar.make(this.view, "No se puedo actualizar el usuario", Snackbar.LENGTH_LONG).show();
            }
        } catch (SQLException sqlException) {
            Log.i("DB", "Error: " + sqlException);
        }
    }

    public ArrayList<Usuario> buscarByDocumento(int documento) {
        SQLiteDatabase db = gestionDB.getReadableDatabase();
        String query = "SELECT * FROM usuarios where USU_DOCUMENTO == " + documento;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        usuario = new Usuario();
        usuario.setDocumento(cursor.getInt(0));
        usuario.setUsuario(cursor.getString(1));
        usuario.setNombres(cursor.getString(2));
        usuario.setApellidos(cursor.getString(3));
        usuario.setContrasena(cursor.getString(4));
        usuarios.add(usuario);

        cursor.close();
        db.close();
        return usuarios;
    }

    public ArrayList<Usuario> listUsuarios() {
        SQLiteDatabase db = gestionDB.getReadableDatabase();
        String query = "SELECT * FROM usuarios";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setDocumento(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContrasena(cursor.getString(4));

                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarios;
    }
}
