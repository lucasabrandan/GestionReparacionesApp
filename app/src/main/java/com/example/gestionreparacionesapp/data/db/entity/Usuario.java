package com.example.gestionreparacionesapp.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombreCompleto;
    private String usuario;
    private String email;
    private String password;
    private String telefono;
    private boolean recordarme;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String usuario, String email, String password, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.recordarme = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isRecordarme() {
        return recordarme;
    }

    public void setRecordarme(boolean recordarme) {
        this.recordarme = recordarme;
    }
}