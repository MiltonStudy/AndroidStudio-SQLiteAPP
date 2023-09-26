package com.uniminuto.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contrasena;

    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContrasena;
    private Button btnRegistrar;
    private Button btnBuscar;
    private ListView lvLista;
    private Button btnListarRegistros;
    private GestionDB gestionDB;
    private SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializador();
    }

    private void inicializador() {
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etContrasena = findViewById(R.id.etContrasena);
        lvLista = findViewById(R.id.lvLista);
        this.listarUsuarios(lvLista);
    }

    public void setDatos() {
        // Regex
        this.documento = Integer.parseInt(etDocumento.getText().toString());
        this.usuario = etUsuario.getText().toString();
        this.nombres = etNombres.getText().toString();
        this.apellidos = etApellidos.getText().toString();
        this.contrasena = etContrasena.getText().toString();
    }

    public void registrarUsuario(View view) {
        setDatos();
        UsuarioDao usuarioDao = new UsuarioDao(this, view);
        Usuario usuario1 = new Usuario();
        usuario1.setDocumento(this.documento);
        usuario1.setUsuario(this.usuario);
        usuario1.setNombres(this.nombres);
        usuario1.setApellidos(this.apellidos);
        usuario1.setContrasena(this.contrasena);
        usuarioDao.insertarUsuario(usuario1);
        this.listarUsuarios(lvLista);
    }

    public void listarUsuarios(View view) {
        UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.lvLista));
        ArrayList<Usuario> userList = usuarioDao.listUsuarios();

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        lvLista.setAdapter(adapter);
    }
}