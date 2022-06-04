package com.example.reddit.service;


import com.example.reddit.exception.springRedditException;
import com.example.reddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j      //create an slf4j logger object
public class MailService {


    private final JavaMailSender mailSender;  //Elle aide à envoyer MimeMessage et SimpleMailMessage. Est une interface qui représente pour un message simple. Elle comprend des informations basiques d'un courrier tels que l'expéditeur, le destinataire, l'objet (subject) et le contenu du message.
    private final MailContentBuilder mailContentBuilder;

    //create method sendmail this method take the object of type notification email as input
    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("chedlykrati2021@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation mail sent !");
        }catch (MailException e){
            log.error("Exception occured when sending mail" , e);
            throw new springRedditException("Exception occured when sending mail to " + notificationEmail.getRecipient(),e);

        }

    }
}
