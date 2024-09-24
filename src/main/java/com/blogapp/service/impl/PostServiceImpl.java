package com.blogapp.service.impl;

import com.blogapp.entity.Post;
import com.blogapp.exception.ResourceNotFound;
import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper) {

        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {


        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post save = postRepository.save(post);

        PostDto postDto1 = new PostDto();
        postDto1.setId(save.getId());
        postDto1.setTitle(save.getTitle());
        postDto1.setDescription(save.getDescription());
        postDto1.setContent(save.getContent());
        return postDto1;

//        Post post =mapToEntity (postDto);
//        Post savedPost = postRepository.save(post);
//
//        PostDto dto= mapToDto(savedPost);
//        return dto;

    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public ListPostDto fetchAllData(int pageNo , int pageSize , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);//this method doesn't take string for converting string to sort we have to use sort.by()method

        Page<Post> all = postRepository.findAll(pageable);
        List<Post>ListOfPost  = all.getContent();
        List<PostDto> listOfDtos = ListOfPost.stream().map(lip -> mapToDto(lip)).collect(Collectors.toList());

        ListPostDto listPostDto =  new ListPostDto();
        listPostDto.setPostDtos(listOfDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int)all.getTotalElements());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setPageNumber(all.getNumber());
        return listPostDto;
    }

  public PostDto getPostById(long id){
      Post post =  postRepository.findById(id).orElseThrow(
              ()->new ResourceNotFound("Post not found with id:"+id)
      );
     return mapToDto(post);
  }

    Post mapToEntity(PostDto postDto){

      Post post =  modelMapper.map(postDto , Post.class);
        return post;

    }
    PostDto mapToDto(Post post){
        PostDto dto =modelMapper.map(post , PostDto.class);
        return dto;

    }


}
