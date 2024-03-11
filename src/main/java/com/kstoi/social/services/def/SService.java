package com.kstoi.social.services.def;

import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface SService<Entity,Dto,Id> {
    Map<String,String> create(Dto dt);
    Map<String,String> update(Dto dt,Id i);
    Map<String,String> delete(Id i);
    Dto read(Id i);
    List<Dto> read(PageRequest p);
    Map<String,String> pages(Integer size);
}
