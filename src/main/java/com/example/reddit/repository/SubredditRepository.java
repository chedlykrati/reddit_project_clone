package com.example.reddit.repository;


import com.example.reddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit , Long> {

    Optional<Object> findByname(String subredditName);
}
