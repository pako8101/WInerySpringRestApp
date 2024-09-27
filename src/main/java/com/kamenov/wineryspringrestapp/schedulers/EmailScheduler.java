package com.kamenov.wineryspringrestapp.schedulers;

import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class EmailScheduler {
@Autowired
    private JavaMailSender mailSender;
    @Autowired
private final UserRepository userRepository;

    @Value("${email_username}") private String sender;

    public EmailScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Scheduled(fixedRate = 12 * 60 * 60 * 1000) // 12 часа в милисекунди
    public void sendScheduledEmails() {
        sendEmail(String.valueOf("pako810129@yahoo.co.uk"),
                "Scheduled Email",
                "This is a scheduled email sent every 12 hours.");

    }
    @Scheduled(cron = "0 * * * * *")
    public void sendSubscriptionEmails() {
        List<UserEntity> pendingUsers = userRepository.findUsersPendingEmails();
        for (UserEntity user : pendingUsers) {
            sendEmail(String.valueOf(user.getEmail()),
                    "Registration Confirmation",
                    "Thank you for registering in Kamenovi Winery Spring Rest App"
            );

            user.setRegistrationEmailSend(true);
            userRepository.save(user);
            System.out.println("Executing task every 1 minute");
        }

    }
    @Async
        public  String sendEmail(String to,String subject,String text){
            try {
                SimpleMailMessage message =
                        new SimpleMailMessage();
                message.setFrom(sender);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(text);
                mailSender.send(message);

                return "Email sent successfully";
            } catch (
                    Exception e) {


                return "Error while sending mail!!!";
            }
        }
    }
