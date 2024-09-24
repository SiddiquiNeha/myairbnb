package com.blogapp.payload;

import lombok.Data;

import java.util.List;

@Data
public class ListPostDto {
    private List<PostDto> postDtos;
    private int totalPages;
    private int totalElements;
    private boolean LastPage;
    private boolean firstPage;
    private int pageNumber;

}
