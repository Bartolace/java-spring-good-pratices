package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Captor
    private ArgumentCaptor<Tutor> tutorCaptor;

    private CadastroTutorDto dto;

    @Test
    public void deveSalvarTutor() {
        this.dto = new CadastroTutorDto("Tutor Teste", "679103-1805", "teste@hotmail.com");

        service.cadastrar(dto);

        BDDMockito.then(repository).should().save(tutorCaptor.capture());
        Tutor tutorSalvo = tutorCaptor.getValue();
        assertEquals(dto.nome(), tutorSalvo.getNome());
        assertEquals(dto.telefone(), tutorSalvo.getTelefone());
        assertEquals(dto.email(), tutorSalvo.getEmail());

        //outra maneira ----
        //given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(false);
        //assertDoesNotThrow(() -> service.cadastrar(dto));
        //then(repository).should().save(new Tutor(dto));
    }

    @Test
    public void deveRetornarErroJaCadastradoAoCadastrarTutor() {
        this.dto = new CadastroTutorDto("Tutor Teste", "679103-1805", "teste@hotmail.com");
        BDDMockito.given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
    }

    @Test
    public void deveNaoRetornarErroJaCadastradoAoCadastrarTutor() {
        this.dto = new CadastroTutorDto("Tutor Teste", "679103-1805", "teste@hotmail.com");
        BDDMockito.given(repository.existsByTelefoneOrEmail(dto.telefone(),dto.email())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> service.cadastrar(dto));
    }
}