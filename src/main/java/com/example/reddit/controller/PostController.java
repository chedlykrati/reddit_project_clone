package com.example.reddit.controller;


import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){

        return status(HttpStatus.OK).body(postService.getAllPosts());

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){

        return status(HttpStatus.OK).body(postService.getPost(id));

    }

    @GetMapping("/sub-reddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id){

        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));

    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username){

        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));

    }
}
