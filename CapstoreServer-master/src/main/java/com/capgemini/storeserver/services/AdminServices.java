package com.capgemini.storeserver.services;

import java.util.List;

import com.capgemini.storeserver.beans.Category;
import com.capgemini.storeserver.beans.Coupon;
import com.capgemini.storeserver.beans.Customer;
import com.capgemini.storeserver.beans.Discount;
import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;


public interface AdminServices {
	
	public Merchant addMerchant(Merchant merchant);
	
	public List<Merchant> viewAllMerchants();
	
	public void removeMerchant(int id);
	
public List<Product> viewAllProducts();
	
	Category updateCategory(int categoryId, String categoryName,String type);
	
	public Coupon addCoupon(Coupon coupon);
	
	public void removeCoupon(int couponId);
	
	public List<Customer> viewAllCustomer();
	public Discount addDiscount(Discount discount); 
	
	public void removeDiscount(int discountId);
}
