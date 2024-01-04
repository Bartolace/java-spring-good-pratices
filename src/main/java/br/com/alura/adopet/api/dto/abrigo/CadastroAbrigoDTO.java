package br.com.alura.adopet.api.dto.abrigo;

import jakarta.validation.constraints.NotNull;

public record CadastroAbrigoDTO(
        @NotNull
        String nome,
        @NotNull
        String telefone,
        @NotNull
        String email
) {

        @Override
        public String nome() {
                return nome;
        }

        @Override
        public String telefone() {
                return telefone;
        }

        @Override
        public String email() {
                return email;
        }
}
