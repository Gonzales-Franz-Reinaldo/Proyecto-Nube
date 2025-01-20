package com.example.proyectonube.model;

import java.io.Serializable;
public class Book implements Serializable{
    private String fotografia;
    private String titulo;
    private String autor;
    private String editorial;
    private String fecha_publicacion;
    private String descripcion;

    public Book() {
        // Constructor vacío requerido para Firestore
    }

    public Book(String fotografia, String titulo, String autor, String editorial, String fecha_publicacion, String descripcion) {
        this.fotografia = fotografia;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.fecha_publicacion = fecha_publicacion;
        this.descripcion = descripcion;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
