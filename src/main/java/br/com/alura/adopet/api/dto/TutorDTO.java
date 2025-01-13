package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Tutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TutorDTO(
        Long Id,
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,
        @NotBlank
        @Email
        String email
) {
        public TutorDTO(Tutor tutor) {
                this(tutor.getId(), tutor.getNome(), tutor.getEmail(), tutor.getTelefone());
        }
}
