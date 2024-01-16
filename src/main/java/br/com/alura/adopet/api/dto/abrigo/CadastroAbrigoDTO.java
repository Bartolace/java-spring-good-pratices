package br.com.alura.adopet.api.dto.abrigo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroAbrigoDTO(
        @NotNull
        String nome,
        @NotNull
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,
        @NotNull
        @Email
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
