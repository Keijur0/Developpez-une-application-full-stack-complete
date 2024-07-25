package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.PostDto;

public interface IPostService {

    PostDto getPost(Long id);

    PostDto createPost(PostDto postDto);

    List<PostDto> getSubscribedPosts(Long userId)
}
