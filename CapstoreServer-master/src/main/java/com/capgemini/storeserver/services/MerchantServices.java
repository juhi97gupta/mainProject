package com.capgemini.storeserver.services;

import java.util.List;

import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.exceptions.MerchantNotFoundException;
import com.capgemini.storeserver.exceptions.ProductNotFoundException;

public interface MerchantServices {

public Merchant registerMerchant(Merchant merchant);
	
	public Merchant updateMerchantProfile(Merchant merchant);
	
	public Merchant deleteMerchantInventory(String username);
	
	public Merchant getMerchant(String username) throws MerchantNotFoundException;
	
	public Merchant getMerchantById(int merchantId);
	
	public void changePassword(Merchant merchant,String password);
	
	public Integer addProduct(Product product) throws ProductNotFoundException;
	
	public List<Product> getAllProducts(int merchantId);
	
	public void updateProduct(Product product)throws ProductNotFoundException;
	
	public Product getProductDetails(int productId);
	
	public void removeProduct(int productId);
	
	public Merchant findMerchantId(int merchantId);	
}
