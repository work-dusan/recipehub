package com.dusan.recipehub.repository;

import com.dusan.recipehub.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TagRepository {

    private final Map<String, Tag> tagMap = new ConcurrentHashMap<>();

    public TagRepository() {
        addTag(new Tag("1", "Brzo"));
        addTag(new Tag("2", "Zdravo"));
        addTag(new Tag("3", "Popularno"));
        addTag(new Tag("4", "Vegetarijansko"));
    }

    public void addTag(Tag tag) {
        tagMap.put(tag.getId(), tag);
    }

    public List<Tag> findAll() {
        return List.copyOf(tagMap.values());
    }

    public Tag findById(String id) {
        return tagMap.get(id);
    }
}
