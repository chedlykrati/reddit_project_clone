package com.example.reddit.service;


import com.example.reddit.dto.SubredditDto;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.mapper.SubredditMapper;
import com.example.reddit.model.Subreddit;
import com.example.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {


    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;


    @Transactional
    public SubredditDto save(SubredditDto subredditDto){

       Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
       subredditDto.setId(save.getId());
       return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getReddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(()-> new SpringRedditException("No Subreddit found with DI : " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }


/*
    private SubredditDto mapToDto(Subreddit subreddit) {

        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
*/
    /*
    private Subreddit mapSubredditDto(SubredditDto subredditDto) {

       return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();

    }
*/

}
