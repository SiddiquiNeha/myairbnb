package com.blogapp.service;

import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public PostDto createPost( PostDto postDto);

    public void deletePost(long id);

    public ListPostDto fetchAllData(int pageNo , int pageSize , String sortBy, String sortDir);

  public PostDto getPostById(long id);
}
