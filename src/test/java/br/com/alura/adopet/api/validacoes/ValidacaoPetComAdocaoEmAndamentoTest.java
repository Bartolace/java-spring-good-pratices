package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private StatusAdocao statusAdocao;

    @Test
    void deveGarantirPetStatusAdocaoLivre() {

        given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), statusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    void deveGarantirPetStatusAdocaoEmAndamento() {

        given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), statusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}