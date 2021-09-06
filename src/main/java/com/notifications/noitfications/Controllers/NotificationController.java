package com.notifications.noitfications.Controllers;


//import com.example.websocketexample.Services.EmailNotificationService;
import com.notifications.noitfications.Config.QueueConfig;
import com.notifications.noitfications.Models.BroadcastMessage;
import com.notifications.noitfications.Models.IndividualMessage;
import com.notifications.noitfications.Services.EmailNotificationService;
import freemarker.cache.StrongCacheStorage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;

@RestController
@RequestMapping("/api/pushNotification")
public class NotificationController {


    private final EmailNotificationService emailNotificationService;

    private final RabbitTemplate template;

    @Autowired
    public NotificationController(EmailNotificationService emailNotificationService, RabbitTemplate template) {
        this.emailNotificationService = emailNotificationService;
        this.template = template;
    }


    @PostMapping(value = "/individualEmail",consumes = "application/json")
    public String sendIndividualEmail(@RequestBody IndividualMessage individualMessage){      //After RabbitMQ integration
//        String email= individualMessage.getEmail();
//        String message= individualMessage.getMessage();
//        String subject= individualMessage.getSubject();
//        emailNotificationService.userNotification(email, message, subject);

        template.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.ROUTING_KEY, individualMessage);
        return "Notification Sent Successfully";
    }

//    @PostMapping(value = "/individualEmail",consumes = "application/json")
//    public void sendIndividualEmail(@RequestBody IndividualMessage individualMessage){        //Without RabbitMQ (Communicate directly with service)
//        String email= individualMessage.getEmail();
//        String message= individualMessage.getMessage();
//        String subject= individualMessage.getSubject();
//        emailNotificationService.userNotification(email, message, subject);
//    }


    @PostMapping (value = "/broadcastEmail", consumes = "application/json")
    public String sendBroadcastEmail(@RequestBody BroadcastMessage req){
//        List <String> emails= req.getEmails();
//        String message= req.getMessage();
//        emailNotificationService.broadcastEmail(emails, message);

        template.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.ROUTING_KEY_BC, req);
        return "BC message Sent Successfully";
    }


//    @RequestMapping(value = "/broadcastEmail", method = RequestMethod.POST)
//    public void sendBroadcastEmail(@RequestBody BroadcastMessage req){
//        List <String> emails= req.getEmails();
//        String message= req.getMessage();
//        emailNotificationService.broadcastEmail(emails, message);
//    }
}
