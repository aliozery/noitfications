package com.notifications.noitfications.Controllers;


//import com.example.websocketexample.Services.EmailNotificationService;
import com.notifications.noitfications.Models.BroadcastMessage;
import com.notifications.noitfications.Models.IndividualMessage;
import com.notifications.noitfications.Services.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pushNotification")
public class NotificationController {
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public NotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }


    @PostMapping(value = "/individualEmail",consumes = "application/json")
    public void sendIndividualEmail(@RequestBody IndividualMessage individualMessage){
        String email= individualMessage.getEmail();
        String message= individualMessage.getMessage();
        String subject= individualMessage.getSubject();
        emailNotificationService.userNotification(email, message, subject);
    }

    @RequestMapping(value = "/broadcastEmail", method = RequestMethod.POST)
    public void sendBroadcastEmail(@RequestBody BroadcastMessage req){
        List <String> emails= req.getEmails();
        String message= req.getMessage();
        emailNotificationService.broadcastEmail(emails, message);
    }
}
