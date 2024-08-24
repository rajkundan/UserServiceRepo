package com.rlsf.services;

import java.util.List;

import com.rlsf.entities.User;

public interface UserService {
	
	User saveUser(User user);
	List<User> getAllUser();
	User getUser(String UserId);

}
