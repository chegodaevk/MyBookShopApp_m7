package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.data.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    @Autowired
    public TagService(TagRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public List<TagEntity> getTagList(){
        List<TagEntity> tagList = tagRepository.findAll();
        for (TagEntity tag : tagList){
            Integer quantityTags = bookRepository.findBooksByTagEntity_Tag(tag.getTag()).size();
            tagRepository.updateQuantityTags(quantityTags,tag.getId());
            if (quantityTags < 3) {
                tagRepository.updateCssClass("Tag Tag_xs", tag.getId());
            } else if (3 <= quantityTags & quantityTags < 6){
                tagRepository.updateCssClass("Tag Tag_sm", tag.getId());
            } else if (6 <= quantityTags & quantityTags < 9){
                tagRepository.updateCssClass("Tag", tag.getId());
            } else if (9 <= quantityTags & quantityTags < 12){
                tagRepository.updateCssClass("Tag Tag_md", tag.getId());
            } else {
                tagRepository.updateCssClass("Tag Tag_lg", tag.getId());
            }

        }
        return tagRepository.findAll();
    }
}
