package com.kstoi.social.services;

import com.kstoi.social.mappers.impl.PostMapper;
import com.kstoi.social.mappers.impl.SUserMapper;
import com.kstoi.social.models.dtos.SUserDto;
import com.kstoi.social.models.entities.Post;
import com.kstoi.social.models.entities.SUser;
import com.kstoi.social.repositories.PostRepository;
import com.kstoi.social.repositories.SUserRepository;
import com.kstoi.social.services.def.SService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SUserService implements UserDetailsService , SService<SUser,SUserDto,String> {
    private SUserRepository repository;
    private SUserMapper mapper;
    private PostRepository postRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SUser sUser = repository.findById(username).orElseThrow();
        return User.withUsername(username).password(sUser.getPassword()).roles("user").build();
    }

    @Override
    public Map<String, String> create(SUserDto dt) {
        if(repository.existsById(dt.getUsername())){
            return Map.of("result","user with name "+dt.getUsername()+" already exists");
        }else{
            repository.save(mapper.toEntity(dt,List.of()));
            return Map.of("result","successfully saved");
        }
    }

    @Override
    public Map<String, String> update(SUserDto dt, String i) {
        if(repository.existsById(dt.getUsername())){
            var posts = new ArrayList<Post>();
            for(Long post : dt.getPosts()){
                posts.add(postRepository.findById(post).orElseThrow());
            }
            repository.save(mapper.toEntity(dt,posts));
            return Map.of("result","successful");
        }
        return Map.of("result","user does not exist");
    }

    @Override
    public Map<String, String> delete(String i) {
        repository.deleteById(i);
        return Map.of("result",i+" deleted successfully");
    }

    @Override
    public SUserDto read(String i) {
        SUser sUser = repository.findById(i).orElseThrow();
        var posts = new ArrayList<Long>();
        for(Post p : sUser.getPosts()){
            posts.add(p.getId());
        }
        return mapper.toDto(sUser,posts);
    }

    @Override
    public List<SUserDto> read(PageRequest p) {
        var result = new ArrayList<SUserDto>();
        for(SUser user : repository.findAll(p)){
            var posts = new ArrayList<Long>();
            for(Post post : user.getPosts()){
                posts.add(post.getId());
            }
            result.add(mapper.toDto(user,posts));
        }
        return result;
    }
    public List<SUserDto> readByNotFriends(PageRequest p,String username)
    {
        SUser sUser = repository.findById(username).orElseThrow();
        sUser.getFriends().add(sUser.getUsername());
        List<SUser> users = repository.findByUsernameNotIn(sUser.getFriends());
        System.out.println(users);
        var result = new ArrayList<SUserDto>();

        for(SUser user : users){
            var posts = new ArrayList<Long>();
            for(Post post : user.getPosts()){
                posts.add(post.getId());
            }
            result.add(mapper.toDto(user,posts));
        }
        return result;
    }

    @Override
    public Map<String, String> pages(Integer size) {
        Long count = repository.count();
        return Map.of("result",""+(count/size),"count",""+count);
    }
}
