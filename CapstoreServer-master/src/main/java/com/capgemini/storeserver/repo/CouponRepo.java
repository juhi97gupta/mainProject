package com.capgemini.storeserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.storeserver.beans.Coupon;


@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer>{

}
