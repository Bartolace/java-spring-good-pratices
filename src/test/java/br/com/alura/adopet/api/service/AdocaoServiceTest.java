package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoPetDisponivel;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoPetDisponivel validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Spy
    private Adocao adocaoAprovada = new Adocao();

    @Spy
    private Adocao adocaoReprovada = new Adocao();

    @Mock
    private AprovacaoAdocaoDTO aprovacaoAdocaoDto;

    @Mock
    private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

    private SolicitacaoAdocaoDTO dto = new SolicitacaoAdocaoDTO(10l, 20l, "motivo qualquer");
    @Test
    void deveSalvarAdocaoAoSolitar() {
        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        service.solicitar(dto);

        BDDMockito.then(repository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    void deveChamarValidadoresDeAdocaoSolicitar() {
        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        validacoes.add(validador1);
        validacoes.add(validador2);

        service.solicitar(dto);

        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }

    @Test
    void deveAprovarAdocao() {
        LocalDateTime data = LocalDateTime.now();
        BDDMockito.given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocaoAprovada);
        BDDMockito.given(adocaoAprovada.getPet()).willReturn(pet);
        BDDMockito.given(adocaoAprovada.getTutor()).willReturn(tutor);
        BDDMockito.given(adocaoAprovada.getPet().getAbrigo()).willReturn(abrigo);
        BDDMockito.given(adocaoAprovada.getData()).willReturn(data);
        BDDMockito.given(adocaoAprovada.getStatus()).willReturn(StatusAdocao.APROVADO);

        service.aprovar(aprovacaoAdocaoDto);

        BDDMockito.then(adocaoAprovada).should().marcarComoAprovada();
        Assertions.assertEquals(StatusAdocao.APROVADO, adocaoAprovada.getStatus());
    }

    @Test
    void deveReprovarAdocao() {
        LocalDateTime data = LocalDateTime.now();
        BDDMockito.given(repository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocaoReprovada);
        BDDMockito.given(adocaoReprovada.getPet()).willReturn(pet);
        BDDMockito.given(adocaoReprovada.getTutor()).willReturn(tutor);
        BDDMockito.given(adocaoReprovada.getPet().getAbrigo()).willReturn(abrigo);
        BDDMockito.given(adocaoReprovada.getData()).willReturn(data);
        BDDMockito.given(adocaoReprovada.getStatus()).willReturn(StatusAdocao.REPROVADO);

        service.reprovar(reprovacaoAdocaoDto);

        BDDMockito.then(adocaoReprovada).should().marcarComoReprovada(reprovacaoAdocaoDto.justificativa());
        Assertions.assertEquals(StatusAdocao.REPROVADO, adocaoReprovada.getStatus());
    }
}