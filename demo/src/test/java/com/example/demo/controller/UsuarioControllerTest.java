package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServiceImpl;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioServiceMock;

    public void obtenerTodosTest() throws Exception {
        //ARRANGE
        //CREACION DE USUARIOS
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setNombre("Juan Perez");
        usuario1.setUsuario("jperez");
        usuario1.setPassword("Asd_123");
        usuario1.setDireccion("Calle 123");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setNombre("Maria Lopez");
        usuario2.setUsuario("mlopez");
        usuario2.setPassword("Qwe_123");
        usuario2.setDireccion("Calle 456");

        //CREACION DE LISTA DE USUARIOS
        List<Usuario> usuarios = Arrays.asList(usuario1,usuario2);
        when(usuarioServiceMock.getAllUsuarios()).thenReturn(usuarios);

        //ACT & ASSERT
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[0].nombre", Matchers.is("Juan Perez")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[0].usuario", Matchers.is("jperez")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[0].password", Matchers.is("Asd_123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[0].direccion", Matchers.is("Calle 123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[1].nombre", Matchers.is("Maria Lopez")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[1].usuario", Matchers.is("mlopez")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[1].password", Matchers.is("Qwe_123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.usuarioList[1].direccion", Matchers.is("Calle 456")));
                
    }

    
}
