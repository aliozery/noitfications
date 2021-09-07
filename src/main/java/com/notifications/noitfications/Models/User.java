package com.notifications.noitfications.Models;


import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "myuser")
@Data
@NoArgsConstructor
@ToString
public class User {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName="user_sequence",
            allocationSize =1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
