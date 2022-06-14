package com.example.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    public Long id;
    public String username;
    public String subredditName;
    public String postName;
    public String url;
    public String description;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;    //related time of post creation time with library call timeago https://github.com/marlonlom/timeago
    private boolean upVote;
    private boolean downVote;

}
