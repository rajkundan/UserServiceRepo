package com.rlsf.service;

import java.util.List;

import com.rlsf.entity.Hotel;

public interface HotelService {
	
	//create
	Hotel create(Hotel hotel);
	
	//getAll
	List<Hotel> getAll();
	
	//get single
	Hotel get(String id);
	
}
