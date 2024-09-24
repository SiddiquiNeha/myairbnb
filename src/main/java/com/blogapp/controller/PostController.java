package com.blogapp.controller;

import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
   private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/post
  @PostMapping
    public ResponseEntity<?>  createPost(@Valid @RequestBody PostDto postDto , BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        return new ResponseEntity<>( bindingResult.getFieldError().getDefaultMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
    }

      PostDto dto = postService.createPost(postDto);
      return new ResponseEntity<>(dto , HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/4
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Deleted" , HttpStatus.OK) ;
    }

        //http://localhost:8080/api/post?pageNo=1&PageSize=10&sortBy=description
    @GetMapping
    public ListPostDto fetchAllData(
            @RequestParam(name="pageNo" ,defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="sortBy" , defaultValue = "id", required = false) String sortBy,
            @RequestParam(name="sortDir" ,defaultValue = "asc",required = false) String sortDir
            ){
        ListPostDto listPostDto = postService.fetchAllData(pageNo, pageSize, sortBy, sortDir);
        return listPostDto;


    }
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto ,HttpStatus.OK);

    }


}
