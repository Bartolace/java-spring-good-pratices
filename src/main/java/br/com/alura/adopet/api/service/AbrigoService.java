package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.PetDTO;
import br.com.alura.adopet.api.dto.abrigo.AbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    public List<AbrigoDTO> listar(){
        return abrigoRepository
                .findAll()
                .stream()
                .map(AbrigoDTO::new)
                .toList();
    }

    public List<PetDTO> listarPetsAbrigo(String idOuNome){
        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository
                .findByAbrigo(abrigo)
                .stream()
                .map(PetDTO::new)
                .toList();
    }

    public Abrigo carregarAbrigo(String idOuNome){
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        }catch (NumberFormatException exception){
            optional = abrigoRepository.findByNome(idOuNome);
        }

        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }

    public void cadastrarPetNoAbrigo(String idOuNome, CadastroPetDTO dto){
        Abrigo abrigo = carregarAbrigo(idOuNome);
        petService.cadastrasPet(abrigo, dto);
    }

}
