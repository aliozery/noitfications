package com.notifications.noitfications.Models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @ToString
public class IndividualMessage {


    String email;
    String message;
    String subject;

    public IndividualMessage(String email, String message, String subject) {
        this.email = email;
        this.message = message;
        this.subject = subject;
    }
}
