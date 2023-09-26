package com.uniminuto.sqliteapp;

public class Usuario {

    int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contrasena;

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return
                "Documento: " + documento +
                "\nUsuario: " + usuario +
                "\nNombres: " + nombres +
                "\nApellidos: " + apellidos +
                "\nContraseña: " + contrasena;
    }
}
