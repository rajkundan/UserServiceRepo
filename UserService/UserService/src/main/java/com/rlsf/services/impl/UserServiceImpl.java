package com.rlsf.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rlsf.entities.Hotel;
import com.rlsf.entities.Rating;
import com.rlsf.entities.User;
import com.rlsf.exception.ResourceNotFoundException;
import com.rlsf.external.services.HotelService;
import com.rlsf.repositories.UserRepository;
import com.rlsf.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	HotelService hotelService;
	
	private Logger logger =LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		
		String randomUserId =UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
		//applying REst template for all users
//		 for (User user : users) {
//		        ArrayList<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), ArrayList.class);
//		        logger.info("Ratings for user {}: {}", user.getUserId(), ratings);
//		        user.setRatings(ratings);
//		    }
		for (User user : users) {
	        Rating[] ratingOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
	        logger.info("{}", ratingOfUser);

	        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

	        List<Rating> ratingList = ratings.stream().map(rating -> {
	            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
	            Hotel hotel = forEntity.getBody();
	            logger.info("response Status code: {}", forEntity.getStatusCode());

	            rating.setHotel(hotel);
	            return rating;
	        }).collect(Collectors.toList());

	        user.setRatings(ratingList);
	    }    
	    
		return users;
	}

	@Override
	public User getUser(String UserId) {
		// TODO Auto-generated method stub
		
		User user =userRepository.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User with given Id is not on server !!  :"+UserId));
		//http://localhost:8083/ratings/users/675da786-849c-48e6-86b0-8e8b669e294c
		Rating[] ratingOfUser=restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		logger.info("{}",ratingOfUser);		
		List<Rating> ratings =Arrays.stream(ratingOfUser).toList();
		
		//http://localhost:8082/hotels/4719ba4b-812b-4b07-813c-6e062872bb73
       List<Rating>	ratingList =	ratings.stream().map(rating->{
    	  // ResponseEntity<Hotel> forEntity= restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
    	   
    // Hotel hotel = forEntity.getBody();
   //  logger.info("response Ststus code: {}",forEntity.getStatusCode());
    	   Hotel hotel=hotelService.getHotel(rating.getHotelId());
     rating.setHotel(hotel);
     return rating;
       }).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		return user;
	}

}
