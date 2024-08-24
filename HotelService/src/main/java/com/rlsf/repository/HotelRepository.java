package com.rlsf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlsf.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,String>{

}
