package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Pet pet1;

    @Mock
    private Pet pet2;

    @Test
    public void deveRetornar200AoListarAbrigosSemErros() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void deveRetornar404AoListarAbrigosComErros() throws Exception {
        BDDMockito.given(abrigoService.listar()).willThrow(new ValidacaoException("Erro ao listar abrigos"));

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }



    @Test
    void deveRetornarStatus200AoCadastrarSemErros() throws Exception {
        String json = """
            {
            "nome": "Abrigo exemplo",
            "telefone": "(67)99103-1805",
            "email": "abrigoExemplo@hotmail.com"
            }
            """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    public void deveRetornarStatus400AoCadastrarComErros() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void deveRetornarStatus200AoListarPetsDoAbrigoSemErros() throws Exception {
        List<PetDTO> pets = List.of(new PetDTO(pet1), new PetDTO(pet2));
        BDDMockito.given(abrigoService.listarPetsDoAbrigo("1")).willReturn(pets);

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(2, pets.size());
    }

    @Test
    public void deveRetornarStatus400AoListarPetsDoAbrigoComErros() throws Exception {
        BDDMockito.given(abrigoService.listarPetsDoAbrigo("1")).willThrow(new ValidacaoException("Abrigo não encontrado"));

        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void deveRetornar200AoCadastraPetSemErros() throws Exception {
        String json = """
                {
                    "id": 1,
                    "tipo": "CACHORRO",
                    "nome": "Rex",
                    "raca": "Labrador",
                    "idade": 5,
                    "cor": "Marrom",
                    "peso": 5
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void deveRetornar400AoCadastraPetComErros() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }
}