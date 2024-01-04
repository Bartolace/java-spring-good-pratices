package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    public List<Pet> listarPetsPorId(String idOuNome){
        Long id = Long.parseLong(idOuNome);
        List<Pet> pets = repository.getReferenceById(id).getPets();
        return pets;
    }

    public List<Pet> listarPetsPorNome(String idOuNome){
        List<Pet> pets = repository.findByNome(idOuNome).getPets();
        return pets;
    }

    public void cadastrarPetNoAbrigoById(String idOuNome, Pet pet){
        Long id = Long.parseLong(idOuNome);
        Abrigo abrigo = repository.getReferenceById(id);
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
        repository.save(abrigo);
    }

    public void cadastrarPetNoAbrigoByNome(String idOuNome, Pet pet){
        Abrigo abrigo = repository.findByNome(idOuNome);
        pet.setAbrigo(abrigo);
        pet.setAdotado(false);
        abrigo.getPets().add(pet);
        repository.save(abrigo);
    }

}
