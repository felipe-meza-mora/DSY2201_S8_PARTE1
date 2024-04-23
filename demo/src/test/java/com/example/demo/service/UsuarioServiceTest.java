package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepositoryMock;
    
    @Test
    public void guardarUsuarioTest() {
        //ARRANGE (CREAR UN OBJETO USUARIO)
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setUsuario("jperez");
        usuario.setPassword("Asd_123");
        usuario.setDireccion("Calle 123");
        
        when(usuarioRepositoryMock.save(any())).thenReturn(usuario);

        //ACT (GUARDAR EL USUARIO)
        Usuario resultado = usuarioService.createUsuario(usuario);

        //ASSERT (VERIFICAR QUE SE GUARDO EL USUARIO)
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("jperez", resultado.getUsuario());
        assertEquals("Asd_123", resultado.getPassword());
        assertEquals("Calle 123", resultado.getDireccion());
    }

    @Test
    public void listarUsuariosTest(){
        //ARRANGE (CREAR UN OBJETOS USUARIOS)
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan Perez");
        usuario1.setUsuario("jperez");
        usuario1.setPassword("Asd_123");
        usuario1.setDireccion("Calle 123");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Maria Lopez");
        usuario2.setUsuario("mlopez");  
        usuario2.setPassword("Qwe_123");
        usuario2.setDireccion("Calle 456");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        when(usuarioRepositoryMock.findAll()).thenReturn(usuarios);

        //ACT (LISTAR USUARIOS)
        List<Usuario> usuariosListadas = usuarioService.getAllUsuarios();

        //ASSERT (VERIFICAR QUE SE LISTARON LOS USUARIOS)
        assertNotNull(usuariosListadas,"La lista de usuarios no debe ser nula");
        assertEquals(2, usuariosListadas.size(),"La lista de usuarios debe tener 2 elementos");

        //VERIFICAR EL PRIMER USUARIO
        Usuario primeUsuario = usuariosListadas.get(0);
        assertEquals(usuario1.getId(),primeUsuario.getId());
        assertEquals("Juan Perez",primeUsuario.getNombre());
        assertEquals("jperez",primeUsuario.getUsuario());
        assertEquals("Asd_123",primeUsuario.getPassword());
        assertEquals("Calle 123",primeUsuario.getDireccion());

        //VERIFICAR EL SEGUNDO USUARIO
        Usuario segundoUsuario = usuariosListadas.get(1);
        assertEquals(usuario2.getId(), segundoUsuario.getId());
        assertEquals("Maria Lopez", segundoUsuario.getNombre());
        assertEquals("mlopez", segundoUsuario.getUsuario());
        assertEquals("Qwe_123", segundoUsuario.getPassword());
        assertEquals("Calle 456", segundoUsuario.getDireccion());
    }
    
}
