package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository repository;

    @Mock
    private Abrigo abrigo;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    @Captor
    private ArgumentCaptor<PetDto> petDtoListCaptor;

//    @Spy
//    private

    private CadastroPetDto dto;

    @Test
    void cadastrarPet() {

        this.dto = new CadastroPetDto(
                TipoPet.CACHORRO, "Cachorro", "Pastor Alemão", 2, "Caramelo", 8F);

        service.cadastrarPet(abrigo,this.dto);

        BDDMockito.then(repository).should().save(petCaptor.capture());
        Pet petSalvo = petCaptor.getValue();
        assertEquals(dto.tipo(), petSalvo.getTipo());
        assertEquals(dto.nome(), petSalvo.getNome());
        assertEquals(dto.raca(), petSalvo.getRaca());
        assertEquals(dto.idade(), petSalvo.getIdade());
        assertEquals(dto.cor(), petSalvo.getCor());
        assertEquals(dto.peso(), petSalvo.getPeso());
        assertEquals(abrigo,    petSalvo.getAbrigo());
    }

    @Test
    void deveListarPetsDisponiveis(){
        //arrange
        BDDMockito.given(repository.findAllByAdotadoFalse()).willReturn((List<Pet>) petDtoListCaptor);
        //act
        service.buscarPetsDisponiveis();

        //assert
        BDDMockito.then(repository).should().findAllByAdotadoFalse(petDtoListCaptor.capture());

        List<PetDto> result = petDtoListCaptor.getAllValues();
        Assertions.assertEquals();

    }

}