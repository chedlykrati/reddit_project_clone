package com.example.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;


@Data
@AllArgsConstructor
@NotEmpty
public class CommentsDto {

    private Long id;
    private Long postId;
    private Instant createDate;
    private String text;
    private String username;
}
