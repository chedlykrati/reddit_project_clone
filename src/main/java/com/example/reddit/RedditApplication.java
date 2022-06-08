package com.example.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync    //asynchronous processing make response api less time
public class RedditApplication {

	public static void main(String[] args) {
		//SpringApplication.run(RedditApplication.class, args);
		SpringApplication.run(com.example.reddit.RedditApplication.class,args);
	}

}
