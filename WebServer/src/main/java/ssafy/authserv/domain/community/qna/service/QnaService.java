package ssafy.authserv.domain.community.qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class QnaService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(String email, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("algofin24@gmail.com");
        message.setReplyTo(email);
        message.setText(content);
        message.setSubject("KICKBACK 회원 문의");

        mailSender.send(message);
    }
}
