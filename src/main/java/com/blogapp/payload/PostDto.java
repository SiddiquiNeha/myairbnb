package com.blogapp.payload;
import com.blogapp.entity.Post;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 3 , message="Title should be at least 3 characters")
    private String title;
    @NotEmpty
    @Size(min = 3 , message="Description should be at least 3 characters")
    private String description;
    private String content;
    //@Email
  //  private string email;
 //   @Min(value=10, message = "mobile number should be 10 characters")
//    @Max(value=10 , message=" mobile number not excceed by 10 digits")
   // private long mobile;



}
