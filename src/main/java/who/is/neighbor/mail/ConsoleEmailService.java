package who.is.neighbor.mail;

public class ConsoleEmailService implements EmailService{
    @Override
    public void send(EmailMessage emailMessage) {
        System.out.println("to = " + emailMessage.getTo());
        System.out.println("subject = " + emailMessage.getSubject());
        System.out.println("message = " + emailMessage.getMessage());
    }
}
