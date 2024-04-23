package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Usuario;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void guardarUsuarioTest() {
        //ARRANGE (CREAR UN OBJETO USUARIO)
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setUsuario("jperez");
        usuario.setPassword("Asd_123");
        usuario.setDireccion("Calle 123");

        //ACT (GUARDAR EL USUARIO)
        Usuario resultado = usuarioRepository.save(usuario);

        //ASSERT (VERIFICAR QUE SE GUARDO EL USUARIO)
        assertNotNull(resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("jperez", resultado.getUsuario());
        assertEquals("Asd_123", resultado.getPassword());
        assertEquals("Calle 123", resultado.getDireccion());
    }

    @Test
    public void modificarUsuarioTest(){
        //ARRANGE (CREAR UN OBJETO USUARIO)
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setUsuario("jperez");
        usuario.setPassword("Asd_123");
        usuario.setDireccion("Calle 123");

        //ACT (GUARDAR EL USUARIO)
        Usuario resultado = usuarioRepository.save(usuario);

        //ACT (MODIFICAR EL USUARIO)
        resultado.setNombre("Juan Perez Modificado");
        resultado.setUsuario("jperez_mod");
        resultado.setPassword("Asd_123_mod");
        resultado.setDireccion("Calle 123 Modificada");

        Usuario resultadoModificado = usuarioRepository.save(resultado);

        //ASSERT (VERIFICAR QUE SE MODIFICO EL USUARIO)
        assertNotNull(resultadoModificado.getId());
        assertEquals("Juan Perez Modificado", resultadoModificado.getNombre());
        assertEquals("jperez_mod", resultadoModificado.getUsuario());
        assertEquals("Asd_123_mod", resultadoModificado.getPassword());
        assertEquals("Calle 123 Modificada", resultadoModificado.getDireccion());
    }

    @Test
    public void eliminarUsuarioTest(){
        //ARRANGE (CREAR UN OBJETO USUARIO)
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setUsuario("jperez");
        usuario.setPassword("Asd_123");
        usuario.setDireccion("Calle 123");

        //ACT (GUARDAR EL USUARIO)
        Usuario resultado = usuarioRepository.save(usuario);

        //ACT (ELIMINAR EL USUARIO)
        usuarioRepository.delete(resultado);

        //ASSERT (VERIFICAR QUE SE ELIMINO EL USUARIO)
        Optional<Usuario> resultado2 = usuarioRepository.findById(resultado.getId());
        assertFalse(resultado2.isPresent(), "El usuario no se eliminó correctamente");
    }


    
}
