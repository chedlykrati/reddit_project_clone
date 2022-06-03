package com.example.reddit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //generated constructors for the class
@NoArgsConstructor
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;

}
