package com.example.libraryservicemanager.web;

import com.example.libraryservicemanager.domain.Response;
import com.example.libraryservicemanager.dto.LoginRequest;
import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.dto.UserRequest;
import com.example.libraryservicemanager.service.JwtService;
import com.example.libraryservicemanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import static com.example.libraryservicemanager.utils.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody UserRequest user, HttpServletRequest request){
        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return ResponseEntity.created(getUrl()).body(getResponse(request,emptyMap(),"Account created, Check your email to enable your account",CREATED));
    }

    @GetMapping("/verify/account")
    public ResponseEntity<Response> verifyAccount(@RequestParam("key") String key, HttpServletRequest request){
        userService.verifyAccount(key);
        return ResponseEntity.ok().body(getResponse(request,emptyMap(),"Account verified",OK));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        jwtService.addCookie(response, user, com.example.libraryservicemanager.model.enumeration.TokenType.ACCESS);
        jwtService.addCookie(response, user, com.example.libraryservicemanager.model.enumeration.TokenType.REFRESH);

        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Login successful", OK));

    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request, HttpServletResponse response) {
        jwtService.removeCookie(request, response, "ACCESS_TOKEN");
        jwtService.removeCookie(request, response, "REFRESH_TOKEN");

        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Logout successful", HttpStatus.OK));
    }

    private URI getUrl() {
        return URI.create("");
    }


}
