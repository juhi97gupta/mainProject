package com.capgemini.storeserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.storeserver.beans.Discount;


@Repository
public interface DiscountRepo extends JpaRepository<Discount, Integer>{

}
