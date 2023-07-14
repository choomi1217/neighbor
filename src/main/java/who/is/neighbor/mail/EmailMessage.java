package who.is.neighbor.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import who.is.neighbor.account.domain.Account;

@Getter
public class EmailMessage {

    private static final String SUBJECT = "웃찾사 이메일 인증 요청";

    private String to;
    private String subject;
    private String message;


    public static EmailMessage of(String to, String message) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.to = to;
        emailMessage.subject = SUBJECT;
        emailMessage.message = message;
        return emailMessage;
    }

    public static String defaultMessage(String email) {
        return "localhost:8080//accounts/" + email + "/email-verification";
    }
}
