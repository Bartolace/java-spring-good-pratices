package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoArgumentCaptor;

    @Mock
    private Abrigo abrigo;

    private CadastroAbrigoDto dto;


    @Test
    void deveCadastrarAbrigo() {
        //arrange
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");

        //act
        service.cadastrar(dto);
        //assert
        BDDMockito.then(repository).should().save(abrigoArgumentCaptor.capture());
        Abrigo abrigoSalvo = abrigoArgumentCaptor.getValue();
        assertEquals(dto.nome(), abrigoSalvo.getNome());
        assertEquals(dto.telefone(), abrigoSalvo.getTelefone());
        assertEquals(dto.email(), abrigoSalvo.getEmail());
    }

    @Test
    void deveRetornarErroJaCadastradoAoCadastrarAbrigo(){
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");
        BDDMockito.given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, ()-> service.cadastrar(dto));
    }

    @Test
    void naoDeveRetornarErroJaCadastradoAoCadastrarAbrigo(){
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");
        BDDMockito.given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(false);

        assertDoesNotThrow(()-> service.cadastrar(dto));
    }

}