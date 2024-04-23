package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.PedidoRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Override
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Integer id){
        return usuarioRepository.findById(id);
    }

    //logica CRUD
    @Override
    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario){
        if(usuarioRepository.existsById(id)){
           usuario.setId(id);
           return usuarioRepository.save(usuario);
        } else {
         return null;
        }
    }

    @Override
    public boolean deleteUsuario(Integer id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Usuario login(String usuario, String password){
      return usuarioRepository.findByUsuarioAndPassword(usuario, password);
    }

    @Override
    public List<Pedido> getAllPedidoByUsuarioId(Integer idUsuario){
        // Obtener el usuario por su ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if(usuarioOptional.isPresent()){
            // Si el usuario existe, obtener los pedidos asociados
            Usuario usuario = usuarioOptional.get();
            List<Pedido> pedidos = usuario.getPedidos();

            // Verificar si hay pedidos asociados
            if(pedidos != null && !pedidos.isEmpty()) {
                return pedidos;
            } else {
                // Devolver una lista vacía si no hay pedidos asociados
                return new ArrayList<>();
            }
        } else {
            // Devolver null si el usuario no existe
            return null;
        }
    }

    @Override
    public Pedido createPedido(Integer usuarioId, Pedido pedido) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            pedido.setUsuario(usuario); // Establecer la relación con el usuario
            
            return pedidoRepository.save(pedido); // Guardar el pedido
        } else {
            return null; // Devolver null si el usuario no existe
        }
    }

    @Override
    public Pedido updatePedido(Integer idPedido, Pedido pedido) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
        if (pedidoOptional.isPresent()) {
            Pedido existingPedido = pedidoOptional.get();
            existingPedido.setDescripcion(pedido.getDescripcion());
            existingPedido.setValor(pedido.getValor());
            // Actualizar otros campos según sea necesario

            return pedidoRepository.save(existingPedido);
        } else {
            return null; // o puedes lanzar una excepción indicando que el pedido no se encontró
        }
}

@Override
public boolean deletePedido(Integer idPedido) {
    // Verificar si existe el pedido
    Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
    if (pedidoOptional.isPresent()) {
        // Si existe, eliminar el pedido
        pedidoRepository.deleteById(idPedido);
        return true;
    } else {
        // Si no existe, devolver false
        return false;
    }
}



   
}
