package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceProducaoTest {

    @InjectMocks
    private EmailServiceProducao service;

    @Mock
    private JavaMailSender mailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> emailCaptor;

    @Test
    void deveEnviarEmail() {
        String to = "exemplo@hotmail.com";
        String subject = "Assunto exemplo";
        String message = "Mensagem exemplo";

        service.enviarEmail(to, subject, message);

        BDDMockito.then(mailSender).should().send(emailCaptor.capture());
        SimpleMailMessage sendEmail = emailCaptor.getValue();

        Assertions.assertEquals(to, sendEmail.getTo()[0]);
        Assertions.assertEquals(subject, sendEmail.getSubject());
        Assertions.assertEquals(message, sendEmail.getText());


    }
}