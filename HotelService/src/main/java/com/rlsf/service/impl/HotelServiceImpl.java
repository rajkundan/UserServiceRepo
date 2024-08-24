package com.rlsf.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlsf.entity.Hotel;
import com.rlsf.exception.ResourceNotFoundException;
import com.rlsf.repository.HotelRepository;
import com.rlsf.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelRepository hotelRepository;
	@Override
	public Hotel create(Hotel hotel) {
		String HotelId=UUID.randomUUID().toString();
		hotel.setId(HotelId);
		// TODO Auto-generated method stub
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		// TODO Auto-generated method stub
		List<Hotel> list=hotelRepository.findAll();
		return list;
	}

	@Override
	public Hotel get(String id) {
		// TODO Auto-generated method stub
		return hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel with given Id not found !!"+id));
	}

}
