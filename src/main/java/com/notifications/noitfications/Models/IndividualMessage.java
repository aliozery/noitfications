package com.notifications.noitfications.Models;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividualMessage {


    String email;
    String message;
    String subject;

}
