package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository repository;


    public List<DadosDetalhesPetDTO> listar(){
        return repository.findAllByAdotadoFalse()
                .stream()
                .map(DadosDetalhesPetDTO :: new)
                .toList();
    }


    public void cadastrasPet(Abrigo abrigo, CadastroPetDTO dto) {
        repository.save(new Pet(dto, abrigo));
    }
}
