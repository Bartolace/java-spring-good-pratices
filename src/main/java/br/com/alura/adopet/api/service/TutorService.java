package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.TutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public List<TutorDTO> listar(){
        return repository.findAll()
                .stream()
                .map(TutorDTO::new)
                .toList();
    }

    public void cadastrar(TutorDTO tutor){
        boolean jaCadastrado = repository.existsByTelefoneOrEmail(tutor.telefone(), tutor.email());
        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(new Tutor(tutor));
        }
    }

    public void atualizar(TutorDTO dto){
        Tutor tutor = repository.getReferenceById(dto.Id());
        tutor.atualizarDados(dto);
    }

}
