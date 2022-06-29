package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObject(userController,"userRepository",userRepo);
        TestUtils.injectObject(userController,"cartRepository",cartRepo);
        TestUtils.injectObject(userController,"bCryptPasswordEncoder",encoder);

    }

    @Test
    public void create_user_happy_path() throws  Exception{
        when(encoder.encode("test1234")).thenReturn("thisIsHashed");
        CreateUserRequest  r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("test1234");
        r.setConfirmPassword("test1234");
        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200,response.getStatusCode());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test",u.getUsername());
        assertEquals("test1234",u.getPassword());
    }
}
