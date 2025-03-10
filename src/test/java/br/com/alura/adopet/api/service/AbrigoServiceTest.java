package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Spy
    private List<Pet> petList = new ArrayList<>();

    @Mock
    private Pet pet1;

    @Mock
    private  Pet pet2;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoArgumentCaptor;

    @Mock
    private Abrigo abrigo;

    @Mock
    private  Abrigo abrigo2;

    private CadastroAbrigoDto dto;

    private List<AbrigoDto> abrigoDtoList;


    @Test
    public void deveListarAbrigos() {
        abrigoDtoList = new ArrayList<>();
        abrigoDtoList.add(new AbrigoDto(abrigo));
        abrigoDtoList.add(new AbrigoDto(abrigo2));
        BDDMockito.given(repository.findAll()).willReturn(List.of(abrigo, abrigo2));

        List<AbrigoDto> result = service.listar();

        BDDMockito.then(repository).should().findAll();
        assertEquals(abrigoDtoList.size(), result.size());
    }


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

    @Test
    public void deveEncontrarAbrigoPorName() {
        String nome = "exemplo abrigo";
        BDDMockito.given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));
        BDDMockito.given(abrigo.getNome()).willReturn(nome);

        Abrigo abrigoEncontrado = service.carregarAbrigo(nome);

        BDDMockito.then(repository).should().findByNome(nome);
        assertEquals(nome, abrigoEncontrado.getNome());
        assertNotNull(abrigoEncontrado);
    }

    @Test
    public void deveRetornarErroAbrigoNaoEncontradoPorId() {
        String id = "1";
        Long idLong = Long.valueOf(id);
        BDDMockito.given(repository.findById(idLong)).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> service.carregarAbrigo(id));
    }

    @Test
    public void deveRetornarErroAbrigoNaoEncontradoPorNome() {
        String nome = "exemplo abrigo";
        BDDMockito.given(repository.findByNome(nome)).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> service.carregarAbrigo(nome));
    }

    @Test
    public void deveListarPetsPorAbrigo(){
        String nome = "Abrigo1";
        petList.add(pet1);
        petList.add(pet2);
        BDDMockito.given(repository.findByNome(nome)).willReturn(Optional.of(abrigo));
        BDDMockito.given(petRepository.findByAbrigo(abrigo)).willReturn(petList);

        List<PetDTO> result = service.listarPetsDoAbrigo(nome);

        BDDMockito.then(petRepository).should().findByAbrigo(abrigo);
        assertEquals(false, result.isEmpty());
        assertEquals(2, result.size());
    }





}