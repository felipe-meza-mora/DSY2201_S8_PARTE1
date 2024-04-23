package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public CollectionModel<EntityModel<Usuario>>getAllUsuarios(){
        List<Usuario> usuarios= usuarioService.getAllUsuarios();

        List<EntityModel<Usuario>> usuariosResources = usuarios.stream()
            .map( usuario->{
                Integer id = usuario.getId();
                    return EntityModel.of(usuario, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).
                    getUsuarioById(id)).withSelfRel()
                );
            })
        .collect(Collectors.toList());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios());
        CollectionModel<EntityModel<Usuario>> resources = CollectionModel.of(usuariosResources, linkTo.withRel("usuarios"));
        
        log.info("GET /usuarios");
        log.info("Retornado todos los usuarios");
        return resources;
        
    }

    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable("id") Integer id){
       Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
       if(usuario.isPresent()){
            return EntityModel.of(usuario.get(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPedidoByUsuarioId(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsuarios()).withRel("usuarios")
            );
       } else {
         throw new UsuarioNotFoundException("Este ID, no existe en nuestros registros.");
       } 
    }

     //CONTROLADORES NUEVO CRUD

     @PostMapping 
     public ResponseEntity<Object> createUsuario(@RequestBody Usuario usuario){
        Usuario createUsuario = usuarioService.createUsuario(usuario);
        if(createUsuario == null){
          log.error("Error al crear el usuario {}",usuario);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el Usuario"));
        }
        return ResponseEntity.ok("El usuario fue creado correctamente");
     }
 
     @PutMapping("/{id}")
     public ResponseEntity<Object> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario){
         Usuario updateUsuario = usuarioService.updateUsuario(id, usuario);
         if(updateUsuario == null){
            log.error("Error al modificar el usuario {}",usuario);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al modificar el Usuario"));
         }
         log.info("Usuario modificado con exito");
         //return ResponseEntity.ok(updateUsuario);
         return ResponseEntity.ok("Usuario con ID " + id + " fue modificado correctamente");
     }
 
     @DeleteMapping("/{id}")
     public  ResponseEntity<Object> deleteUsuario(@PathVariable("id") Integer id){
        boolean deleted = usuarioService.deleteUsuario(id);
        if(deleted){
            log.info("Usuario eliminado con éxito");
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
        } else {
            log.error("Error al eliminar el usuario con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se puede eliminar el Usuario con el ID: " + id));
        }
     }

     @PostMapping("/login")
     public ResponseEntity<String> login(@RequestBody Usuario usuario){
        Usuario usuarioAutenticado = usuarioService.login(usuario.getUsuario(), usuario.getPassword());
        if (usuarioAutenticado != null) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
     }

     @GetMapping("/{idUsuario}/pedidos")
    public CollectionModel<EntityModel<Pedido>> getAllPedidoByUsuarioId(@PathVariable("idUsuario") Integer idUsuario) {
        List<Pedido> pedidos = usuarioService.getAllPedidoByUsuarioId(idUsuario);

        List<EntityModel<Pedido>> pedidosResources = pedidos.stream()
                .map(pedido -> EntityModel.of(pedido,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                                .getAllPedidoByUsuarioId(idUsuario)).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                                .getAllUsuarios()).withRel("usuarios")))
                .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                .getAllPedidoByUsuarioId(idUsuario));

        CollectionModel<EntityModel<Pedido>> resources = CollectionModel.of(pedidosResources,
                linkTo.withRel("pedidos"));

        log.info("GET /usuarios/{}/pedidos", idUsuario);
        log.info("Retornados todos los pedidos del usuario con ID: {}", idUsuario);
        return resources;
    }

    @PostMapping("/{idUsuario}/pedidos")
    public ResponseEntity<Object> createPedido(@PathVariable("idUsuario") Integer idUsuario, @RequestBody Pedido pedido) {
        // Intenta crear el pedido utilizando el servicio
        Pedido createdPedido = usuarioService.createPedido(idUsuario, pedido);
        
        if (createdPedido != null) {
            // Si el pedido se crea con éxito, devuelve un mensaje de éxito
            return ResponseEntity.ok("Pedido creado correctamente");
        } else {
            // Si el usuario no existe, devuelve un mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado. No se pudo crear el pedido.");
        }
    }

    @PutMapping("/{idUsuario}/pedidos/{idPedido}")
    public ResponseEntity<Object> updatePedido(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("idPedido") Integer idPedido,@RequestBody Pedido pedido) {
        Pedido updatedPedido = usuarioService.updatePedido(idPedido, pedido);
        if (updatedPedido != null) {
            return ResponseEntity.ok("Pedido con ID " + idPedido + " actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el pedido con ID: " + idPedido);
        }
    }


    @DeleteMapping("/{idUsuario}/pedidos/{idPedido}")
    public ResponseEntity<Object> deletePedido(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("idPedido") Integer idPedido) {
        // Verificar si el usuario existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()) {
            // Si el usuario existe, intentar eliminar el pedido
            boolean deleted = usuarioService.deletePedido(idPedido);
            if (deleted) {
                // Si se eliminó correctamente, devolver respuesta exitosa
                log.info("Pedido con ID {} eliminado con éxito para el usuario con ID {}", idPedido, idUsuario);
                return ResponseEntity.ok("Pedido con ID " + idPedido + " eliminado correctamente.");
            } else {
                // Si el pedido no se pudo eliminar, devolver error
                log.error("Error al eliminar el pedido con ID {} para el usuario con ID {}", idPedido, idUsuario);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el pedido con ID " + idPedido + ".");
            }
        } else {
            // Si el usuario no existe, devolver error
            log.error("Usuario con ID {} no encontrado.", idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + idUsuario + " no encontrado.");
        }
    }



     static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
     }
     
    
}
