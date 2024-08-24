package com.rlsf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlsf.entities.User;
import com.rlsf.services.UserService;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public  ResponseEntity<User> createUser(@RequestBody User user){
		User user1= userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception e){
	//logger.info("Fallback is executed because service is down",e.getMessage());
	User user= User.builder()
			       .email("dummy@gmail.com")
			       .name("dummy")
			       .about("this user is created dummy for fallback purpose")
			       .userId("12346")
			       .build();
	return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		
		List<User> users =userService.getAllUser();
		return ResponseEntity.ok(users);
	}
}
