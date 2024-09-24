package com.blogapp.controller;

import com.blogapp.entity.User;
import com.blogapp.payload.SignUp;
import com.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //http://localhost:8080/api/auth/sign-up

    @Autowired

   private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/sign-up")
    public ResponseEntity<String>  createUser(@RequestBody SignUp signUp){
        if(userRepository.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("email id is already registered", HttpStatus.CREATED);
        }
        if(userRepository.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("User is registered",HttpStatus.CREATED);
        }

        User user = new User();
        user.setName(signUp.getName());
        user.setEmail(signUp.getEmail());
        user.setUsername(signUp.getUsername());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("user registered",HttpStatus.CREATED);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto){

        //!.supplynlonDto.getUsername to loadByUser method in customUserDetailService class
        //2.it will compare
        // Expected credentials-loginDto.getUsername(),loginDto.getPassword()
        //with actual credentials given By loaduser method
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

       //Similar to session variable
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("sign-in successful",HttpStatus.OK);
    }
}
