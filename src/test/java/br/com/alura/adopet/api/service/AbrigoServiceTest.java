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

import java.util.Optional;

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
    public void deveCadastrarAbrigo() {
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");

        service.cadastrar(dto);

        BDDMockito.then(repository).should().save(abrigoArgumentCaptor.capture());
        Abrigo abrigoSalvo = abrigoArgumentCaptor.getValue();
        assertEquals(dto.nome(), abrigoSalvo.getNome());
        assertEquals(dto.telefone(), abrigoSalvo.getTelefone());
        assertEquals(dto.email(), abrigoSalvo.getEmail());
    }

    @Test
    public void deveRetornarErroJaCadastradoAoCadastrarAbrigo(){
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");
        BDDMockito.given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, ()-> service.cadastrar(dto));
    }

    @Test
    public void naoDeveRetornarErroJaCadastradoAoCadastrarAbrigo(){
        this.dto = new CadastroAbrigoDto("Abrigo1", "6791031805", "exemplo@hotmai.com");
        BDDMockito.given(repository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(), dto.email())).willReturn(false);

        assertDoesNotThrow(()-> service.cadastrar(dto));
    }

    @Test
    public void deveEncontrarAbrigoPorId() {
        String id = "1";
        Long idLong = Long.valueOf(id);
        BDDMockito.given(repository.findById(idLong)).willReturn(Optional.of(abrigo));
        BDDMockito.given(abrigo.getId()).willReturn(idLong);

        Abrigo abrigoEncontrado = service.carregarAbrigo(id);

        BDDMockito.then(repository).should().findById(idLong);
        assertEquals(idLong, abrigoEncontrado.getId());
        assertNotNull(abrigoEncontrado);
    }



}