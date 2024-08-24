package com.rlsf.service;

import java.util.List;

import com.rlsf.entities.Rating;

public interface RatingService {

	//create 
	Rating create(Rating rating);
	
	//get all rating
	List<Rating> getRating();
	
	//getAll by UserId
	List<Rating> getRatingByUserId(String userId);
	
	//get all by hotel
	List<Rating> getRatingByHotelId(String hotelId);
}
