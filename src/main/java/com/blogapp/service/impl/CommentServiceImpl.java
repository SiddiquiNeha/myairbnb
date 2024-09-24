package com.blogapp.service.impl;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.payload.CommentDto;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostWithCommentDto;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);

        return dto;
    }
    public PostWithCommentDto getAllCommentsById(long id){
        Post post = postRepository.findById(id).
                get();//Post found
        PostDto dto1 = new PostDto();
        dto1.setId(post.getId());
        dto1.setTitle(post.getTitle());
        dto1.setDescription(post.getDescription());
        dto1.setContent(post.getContent());

        List<Comment> comment = commentRepository.findByPostId(id);//comment found

       List<CommentDto> dtos = comment.stream().map(c->mapToDto(c)).collect(Collectors.toList());//converted to dto

        PostWithCommentDto postWithCommentDto = new PostWithCommentDto();

        postWithCommentDto.setCommentDto(dtos);
        postWithCommentDto.setPost(dto1);
        return postWithCommentDto;//returning object

    }

   Comment mapToEntity(CommentDto dto){
       Comment comment = modelMapper.map(dto, Comment.class);
        return comment;

   }

  CommentDto mapToDto(Comment comment){

  return modelMapper.map(comment, CommentDto.class);
  }
}
