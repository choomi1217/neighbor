package who.is.neighbor.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class JavaEmailServiceTest {
    @Test
    public void sendTest() throws Exception {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        JavaEmailService emailService = new JavaEmailService(javaMailSender);
        EmailMessage emailMessage = EmailMessage.of("to@example.com", "test message");

        emailService.send(emailMessage);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

}