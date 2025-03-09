package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    @Spy
    private List<Pet> petList = new ArrayList<>();

    @Mock
    private Pet pet1;

    @Mock
    private Pet pet2;



    private CadastroPetDTO dto;

    @Test
    void cadastrarPet() {

        this.dto = new CadastroPetDTO(
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
        assertEquals(abrigo, petSalvo.getAbrigo());
    }

    @Test
    void deveListarPetsDisponiveis(){
        petList.add(pet1);
        petList.add(pet2);
        BDDMockito.given(repository.findAllByAdotadoFalse()).willReturn(petList);

        List<PetDTO> result = service.buscarPetsDisponiveis();

        BDDMockito.then(repository).should().findAllByAdotadoFalse();
        assertEquals(false, result.isEmpty());
        assertEquals(2,result.size());
    }

}