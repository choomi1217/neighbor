package who.is.neighbor.mail;

import jakarta.mail.SendFailedException;

public interface EmailService {
    void send(EmailMessage emailMessage) throws SendFailedException;
}
