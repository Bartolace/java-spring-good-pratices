package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDTO;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDTO;

public interface ValidacaoCadastraAbrigo {
    
    void validar(CadastroAbrigoDTO dto);
}
