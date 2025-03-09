package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService service;

    @Test
    void deveDevolverCodigo400ParaSolicitarAdocaoComErros() throws Exception {
        //arrange
        String json = "{}";

        //act
        MockHttpServletResponse response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        //assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveDevolverCodigo200ParaSolicitarAdocaoSemErros() throws Exception {
        //arrange
        String json = """
                {
                   "idPet": 1,
                   "idTutor": 1,
                   "motivo": "Motivo qualquer"
                }
                """;

        //act
        MockHttpServletResponse response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        //assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveDevolverCodigo200AoAprovarAdocaoSemErros() throws Exception {
        String json = """
                {
                   "idAdocao": 1
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        //assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveDevolverCodigo400AoAprovarAdocaoComErros() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());

    }

    @Test
    void deveDevolver400AoReprovarAdocaoComErros() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveDevolver200AoReprovarAdocaoSemErros() throws Exception {
        String json = """
                {
                   "idAdocao": 1,
                   "justificativa" : "Justificativa qualquer"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }


}