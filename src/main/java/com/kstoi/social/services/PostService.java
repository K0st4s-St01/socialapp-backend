package com.kstoi.social.services;

import com.kstoi.social.mappers.impl.PostMapper;
import com.kstoi.social.models.dtos.PostDto;
import com.kstoi.social.models.entities.Post;
import com.kstoi.social.repositories.PostRepository;
import com.kstoi.social.repositories.SUserRepository;
import com.kstoi.social.services.def.SService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements SService<Post, PostDto,Long> {
    private PostRepository repository;
    private PostMapper mapper;
    private SUserRepository userRepository;


    public List<PostDto> readByFriends(List<String> friends, Integer page,Integer size){
        var result = new ArrayList<PostDto>();
        for(Post post : repository.findByUser_UsernameIn(friends,PageRequest.of
                (
                        page,
                        size,
                        Sort.by(Sort.Direction.DESC,"date")
                ))
        ){
            result.add(mapper.toDto(post,post.getUser().getUsername()));
        }
        return result;
    }

    @Override
    public Map<String, String> create(PostDto dt) {
        if(dt.getId() == null){
            repository.save(mapper.toEntity(dt
                    ,userRepository.findById(dt.getUser()).orElseThrow()));
            return Map.of("result","posted successfully");
        }else{
            return Map.of("result","posting failed");

        }

    }

    @Override
    public Map<String, String> update(PostDto dt, Long i) {
        if(repository.existsById(i)){
            dt.setId(i);
            repository.save(mapper.toEntity(dt,
                    userRepository.findById(dt.getUser()).orElseThrow()));
            return Map.of("result","Post updated successfully");
        }
        return Map.of("result","post failed updating");
    }

    @Override
    public Map<String, String> delete(Long i) {
        repository.deleteById(i);
        return Map.of("result","post deleted");
    }

    @Override
    public PostDto read(Long i) {
        Post post = repository.findById(i).orElseThrow();
        return mapper.toDto(post , post.getUser().getUsername());
    }

    @Override
    public List<PostDto> read(PageRequest p) {
        var result = new ArrayList<PostDto>();
        for(Post post : repository.findAll(p)){
            result.add(mapper.toDto(post,post.getUser().getUsername()));
        }
        return result;
    }

    @Override
    public Map<String, String> pages(Integer size) {
        Long count = repository.count();
        return Map.of("result",""+(count/size),"count",""+count);
    }
}
