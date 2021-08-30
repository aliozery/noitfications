package com.notifications.noitfications.Models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @NoArgsConstructor @ToString
public class BroadcastMessage {

    List<String> emails;
    String message;

    public BroadcastMessage(List<String> emails, String message) {
        this.emails = emails;
        this.message = message;
    }
}
