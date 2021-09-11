package com.notifications.noitfications.Models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class BroadcastMessage {

    List<String> emails;
    String message;

    public BroadcastMessage(List<String> emails, String message) {
        this.emails = emails;
        this.message = message;
    }
}
