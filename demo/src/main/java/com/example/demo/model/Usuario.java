package com.example.demo.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="nombre")
    @NotNull(message = "El NOMBRE, no puede ser nulo")
    @Size(max = 100)
    private String nombre;

    @Column(name="usuario")
    @NotBlank(message = "El USUARIO, no puede estar vacío")
    @Size(max = 100)
    private String usuario;
     
    @Column(name="password")
    @NotBlank(message = "La PASSWORD, no puede estar vacía")
    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_@#$%^&+=!])(?=\\S+$).{4,}$",
    message = "La contraseña debe contener al menos 4 caracteres, incluyendo al menos una mayúscula, una minúscula, un dígito y uno de los siguientes caracteres especiales: '-', '_', '@', '#', '$', '%', '^', '&', '+', '=' o '!'.")
    private String password;

    @Column(name="direccion")
    @NotBlank(message = "La DIRECCIÓN, no puede estar vacía")
    @Size(max = 100)
    private String direccion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    List<Pedido> pedidos;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario=usuario;
    }

    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password=password;
    }

    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    
    
}
