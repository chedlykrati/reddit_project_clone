package com.example.reddit.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data    // replace Getter and  Setter generated automatically with lombok library
@Entity
@Builder  //generated builder method uses builder design pattern to create projects for the class with data
@AllArgsConstructor  //generated constructors for the class
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId ;
    @NotBlank(message = "Post Name can not be empty or null")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob //is datatype for storing large object data  //CLOB – Character Large Object will store large text data  //BLOB – Binary Large Object is for storing binary data like image, audio, or video
    private String description;
    private Integer voteCount;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "userId" , referencedColumnName = "userId")
    private User user;
    private Instant createdDate;    // createdDate with Instant type
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id" , referencedColumnName = "id")
    private Subreddit subreddit;

}
