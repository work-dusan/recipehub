package com.dusan.recipehub.converter;

import com.dusan.recipehub.model.Tag;
import com.dusan.recipehub.repository.TagRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTagConverter implements Converter<String, Tag> {

    private final TagRepository tagRepository;

    public StringToTagConverter(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return tagRepository.findById(source);
    }
}
