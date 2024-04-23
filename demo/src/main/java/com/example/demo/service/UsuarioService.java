package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;

public interface UsuarioService {
    //USUARIO
    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Integer id);

    //CRUD
    Usuario createUsuario(Usuario usuario);
    Usuario updateUsuario(Integer id, Usuario usuario);
    boolean deleteUsuario(Integer id);
    Usuario login(String usuario, String password);
    //FIN USUARIO

    //PEDIDO
    List<Pedido> getAllPedidoByUsuarioId(Integer idUsuario);

    //CRUD
    Pedido createPedido(Integer usuarioId,Pedido pedido);
    Pedido updatePedido(Integer idPedido, Pedido pedido);
    boolean deletePedido(Integer idPedido);
    //FIN PEDIDO




}
