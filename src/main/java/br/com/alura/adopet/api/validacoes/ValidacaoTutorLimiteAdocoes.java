package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorLimiteAdocoes implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDTO dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        List<Adocao> adocoes = adocaoRepository.findAll();

        boolean tutorTemAdocaoAprovada = adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(),
                StatusAdocao.APROVADO);

        int contador = 0;
        if (tutorTemAdocaoAprovada) {
            contador = contador + 1;
        }
        if (contador == 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }

    }
}
