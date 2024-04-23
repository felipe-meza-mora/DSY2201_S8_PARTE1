package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="pedidos")
public class Pedido {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="descripcion")
    @Size(max = 100)
    private String descripcion;

    @Column(name="valor")
    private Integer valor;
    
     
    // RELACIÓN CON USUARIO
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public Integer getValor(){
        return valor;
    }

    public void setValor(Integer valor){
        this.valor=valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
