package com.uniminuto.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
    private ListView lvLista;

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

    // Regex
    public int regexNoNulls(String valueView) {
        Pattern patronNoNull = Pattern.compile(".+");
        Matcher matcherNoNull = patronNoNull.matcher(valueView);

        return (matcherNoNull.matches()) ? 0 : -1;
    }

    public int regexNoNumbers(String valueView) {
        Pattern patronNumeros = Pattern.compile("\\d+");
        Matcher matcherNumeros = patronNumeros.matcher(valueView);

        return (matcherNumeros.matches()) ? 0 : -2;
    }

    public String setDatos() {
        // nombreCampo, respuestaRegex
        HashMap<String, Integer> responseRegex = new HashMap<String, Integer>();

        // Validar que no hayan campos vacios
        responseRegex.put("DOCUMENTO", regexNoNulls(etDocumento.getText().toString()));
        responseRegex.put("USUARIO", regexNoNulls(etUsuario.getText().toString()));
        responseRegex.put("NOMBRES", regexNoNulls(etNombres.getText().toString()));
        responseRegex.put("APELLIDOS", regexNoNulls(etApellidos.getText().toString()));
        responseRegex.put("CONTRASEÑA", regexNoNulls(etContrasena.getText().toString()));

        // Validar que el documento contenga solo números
        responseRegex.put("DOCUMENTO", regexNoNumbers(etDocumento.getText().toString()));

        Iterator<Map.Entry<String, Integer>> iterator = responseRegex.entrySet().iterator();
        Map.Entry<String, Integer> value;

        while (iterator.hasNext()) {
            value = iterator.next();
            if (value.getValue() != 0) {
                switch (value.getValue()) {
                    case -1:
                        return "El campo " + value.getKey() + " No puede estar vacio";
                    case -2:
                        return "El campo " + value.getKey() + " Solo debe contener números";
                }
            }
        }

        if (!iterator.hasNext()) {
            this.documento = Integer.parseInt(etDocumento.getText().toString());
            this.usuario = etUsuario.getText().toString();
            this.nombres = etNombres.getText().toString();
            this.apellidos = etApellidos.getText().toString();
            this.contrasena = etContrasena.getText().toString();
            return "OK VALIDATIONS";
        }
        return null;
    }

    public void registrarUsuario(View view) {
        if (setDatos().equals("OK VALIDATIONS")) {
            Usuario usuario1 = new Usuario();
            usuario1.setDocumento(this.documento);
            usuario1.setUsuario(this.usuario);
            usuario1.setNombres(this.nombres);
            usuario1.setApellidos(this.apellidos);
            usuario1.setContrasena(this.contrasena);
            UsuarioDao usuarioDao = new UsuarioDao(this, view);
            usuarioDao.insertarUsuario(usuario1);
            this.listarUsuarios(lvLista);
        } else {
            Toast.makeText(this, setDatos(), Toast.LENGTH_SHORT).show();
        }

    }

    public void actualizarUsuario(View view) {
        if (setDatos().equals("OK VALIDATIONS")) {
            Usuario usuario1 = new Usuario();
            usuario1.setDocumento(this.documento);
            usuario1.setUsuario(this.usuario);
            usuario1.setNombres(this.nombres);
            usuario1.setApellidos(this.apellidos);
            usuario1.setContrasena(this.contrasena);
            UsuarioDao usuarioDao = new UsuarioDao(this, view);
            usuarioDao.actualizarUsuario(usuario1);
            this.buscarUsuario(view);
        } else {
            Toast.makeText(this, setDatos(), Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarUsuario(View view) {
        if (regexNoNulls(etDocumento.getText().toString()) == 0) {
            this.documento = Integer.parseInt(etDocumento.getText().toString());
            UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.lvLista));
            ArrayList<Usuario> user = usuarioDao.buscarByDocumento(this.documento);

            lvLista.setAdapter(null);
            etUsuario.setText(user.get(0).getUsuario());
            etNombres.setText(user.get(0).getNombres());
            etApellidos.setText(user.get(0).getApellidos());
            etContrasena.setText(user.get(0).getContrasena());

            ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user);
            lvLista.setAdapter(adapter);
        } else {
            Toast.makeText(this, "El campo DOCUMENTO No puede estar vacio", Toast.LENGTH_SHORT).show();
        }

    }

    public void listarUsuarios(View view) {
        UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.lvLista));
        ArrayList<Usuario> userList = usuarioDao.listUsuarios();

        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        lvLista.setAdapter(adapter);
    }
}