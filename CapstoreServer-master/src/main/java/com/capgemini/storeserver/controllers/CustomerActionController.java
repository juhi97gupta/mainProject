package com.capgemini.storeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.storeserver.beans.Address;
import com.capgemini.storeserver.beans.Cart;
import com.capgemini.storeserver.beans.Category;
import com.capgemini.storeserver.beans.Customer;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.exceptions.CustomerNotFoundException;
import com.capgemini.storeserver.exceptions.InvalidInputException;
import com.capgemini.storeserver.services.CustomerServices;

@RestController
public class CustomerActionController {

	

	@Autowired
	private CustomerServices customerService; 


	// Customer SignUp
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public void signUp(@RequestBody Customer customer) {
		customerService.signUp(customer);
	}

	// CustomerSignIn
	@RequestMapping(value = "/customerSignIn")
	public ResponseEntity<String> customerSignIn(String email, String password) {
		Customer customer=null;
		try {
			customer = customerService.customerSignIn(email, password);
			
		} catch (InvalidInputException e) {
			
			return null;
		}
		String name = customer.getCustomerName();
		return new ResponseEntity<String>(name, HttpStatus.OK);
	}

	// getCustomerDetails
	@RequestMapping(value = "/getCustomerDetails")
	public Customer getCustomerDetails(String phoneNumber) {
		Customer customer=null;
		try {
			customer = customerService.getCustomerDetails(phoneNumber);
		} catch (InvalidInputException e) {
			
			return customer;
		}
		return  customer;
	}

	// getAllProducts
	@RequestMapping(value = "/getAllProducts")
	public List<Product> getAllProductsFromDB() {
		List<Product> products = null;
		try {
			products = customerService.getAllProducts();
		} catch (InvalidInputException e) {
			
			return products;
		}
		return  products;
	}

	@RequestMapping(value = "/getProductById")
	public Product getProductById(int productId) {
		Product product = null;
		try {
			product = customerService.getProductById(productId);
		} catch (InvalidInputException e) {
			
			return product;
		}
		return  product;
	}

	@RequestMapping(value = "/getProductByCategory")
	public List<Product> getProductByCategory(Category category ){
		List<Product> products=null;
		try {
			products = customerService.getProductByCategory(category);
		} catch (InvalidInputException e) {
			
			return products;
		}
		return  products;
	}

	@RequestMapping(value = "/getDeliveryStatus")
	public String getDeliveryStatus(int orderId) {
		String status=null;
		try {
			status = customerService.getDeliveryStatus(orderId);
		} catch (InvalidInputException e) {
			
			return status;
		}
		return  status;
	}
	//gvk
	@RequestMapping(value="/updateSecurityQuestion")
	public boolean updateSecurityQuestion(String phoneNumber,String securityQuestion)
	{
		boolean val;
		try {
		 val = customerService.updateSecurityQuestion(phoneNumber, securityQuestion);
		} catch (InvalidInputException e) {
			return false;
		}
		
		return val;
	}

	@RequestMapping(value="/updateSecurityAnswer")
	public boolean updateSecurityAnswer(String phoneNumber,String securityAnswer){
		try {
			return  customerService.updateSecurityAnswer(phoneNumber, securityAnswer);
		} catch (InvalidInputException e) {
			
			return false;
		}
	}
	@RequestMapping(value="/updateCardNumber")
	public boolean updateCardNumber(String phoneNumber,String cardNumber){
		try {
			return  customerService.updateCardNumber(phoneNumber, cardNumber);
		} catch (InvalidInputException e) {
			return false;
		}
	}
	@RequestMapping(value="/updateCustomerName")
	public boolean updateCustomerName(String phoneNumber,String customerName){
		try {
			return  customerService.updateCustomerName(phoneNumber, customerName);
		} catch (InvalidInputException e) {
			return false;
		}
	}
	//pavani
	@RequestMapping(value = "/addAddress")
	public void addAddressDetails(Address address)
	{
		customerService.addAddress(address);
	}
	@RequestMapping(value = "/viewAddressDetails")
	public Address getAddressDetails(int addressId)
	{
		Address address;

		address = customerService.getAddress(addressId);

		return  address;
	}
	@RequestMapping(value = "/getCoupon")
	public double getCouponDetails(int cartId)
	{
		double price =  customerService.applyCoupon(cartId);
		return  price;
	}
	//aksh
	@RequestMapping(value= "/getWishlist")
	public List<Product> getWishlist(String phoneNumber) {
		 List<Product> wishList=null;
		try {
			wishList = customerService.getWishlist(phoneNumber);
		} catch (InvalidInputException e) {
			return wishList;
		}
		
		return wishList;
	}

	@RequestMapping(value= "/addProductToWishlist")
	public boolean addProductToWishlist(String phoneNumber, int productId) {
		boolean val;
		try {
			 val=customerService.addProductToWishlist(phoneNumber, productId);
		} catch (InvalidInputException e) {
			return false;
		}
		
		return val;
	}

	@RequestMapping(value= "/removeProductFromWishlist")
	public boolean removeProductFromWishlist(String phoneNumber,int productId) {
		boolean val;
		try {
			val=customerService.removeProductFromWishlist(phoneNumber, productId);
		} catch (InvalidInputException e) {
			return false;
		}
		
		return val;
	}


	@RequestMapping(value= "/setReview")
	public String setReview(String phoneNumber,int rating,String comments,int productId) {
		try {
			customerService.setReviewMethod(phoneNumber, rating, comments, productId);
		} catch (InvalidInputException e) {
			return null;
		}
		return "review";
	}

	@RequestMapping(value= "/securityQuestion")
	public String securityQuestion(String phoneNumber, String securityAnswer) {
		String securityQuestion = null;
		try {
			securityQuestion = customerService.securityQuestion(phoneNumber, securityAnswer);
		} catch (InvalidInputException e) {
			return securityQuestion;
		}
		
		return securityQuestion;
	}

	@RequestMapping(value= "/applyDiscount")
	public double applyDiscount(int productId){
		
		return customerService.applyDiscount(productId);
	}
	
	@RequestMapping(value= "/forgotPassword")
	public String forgotPassword(String phoneNumber) {
		String forgotPassword=null;
		try {
			forgotPassword=customerService.forgotPassword(phoneNumber);
		} catch (CustomerNotFoundException e) {
			return forgotPassword;
		}
		
		return forgotPassword;
	}
	
	@RequestMapping(value= "/onlinePayment")
	public void onlinePayment(String phoneNumber,String cardNumber){
		customerService.onlinePayment(cardNumber, phoneNumber);
	}
	
	@RequestMapping(value= "/addProductToNewCart")
	public Cart addProductToNewCart(String phoneNumber,int quantity, int productId) { 
		Cart cart=null;
		try {
			cart= customerService.addProductToNewCart(phoneNumber, productId, quantity);
		}
		catch  (Exception e) {
			return cart;
		}
		return cart;
	}
	@RequestMapping(value= "/updateCart")
	public Cart updateCart(String phoneNumber,int quantity, int productId) {
		Cart cart=null;
		try {
			cart = customerService.updateCart(phoneNumber, productId, quantity);
		} catch (Exception e) {
			return cart;
		}
		
		return cart;
	}
	@RequestMapping(value= "/removeFromCart")
	public Cart removeFromCart(String phoneNumber,int productId, int quantity) {
		
		return customerService.removeProductFromCart(phoneNumber, productId);
	}
	@RequestMapping(value= "/getCart")
	public List<Product> getCart(String phoneNumber) {
		List<Product> cartList = null;
		try {
			cartList = customerService.getAllProductsFromCart(phoneNumber);
		} catch (InvalidInputException e) {
			return cartList;
		}
		
		return cartList;
	}
	@RequestMapping(value="/changePassword")
	public boolean changePassword(String phoneNumber, String newPassword) {
		boolean val = false;
		try {
			val = customerService.changePassword(phoneNumber, newPassword);
		} catch (InvalidInputException | CustomerNotFoundException e) {
			return val;
		}
		
		return val;
	}

	@RequestMapping(value="/successReturn")
	public boolean successReturn(int orderId) {
		return customerService.returnGoods(orderId);
	}

	@RequestMapping(value= "/sortByAsc")
	public List<Product> sortByAsc(String categoryName){
		List<Product> sortedList = null;
		try {
			sortedList =  customerService.getProductsByPriceAsc(categoryName);
		} catch (Exception e) {
			return sortedList;
		}
		
		return sortedList;
	}
	
	@RequestMapping(value= "/sortByDesc")
	public List<Product> sortByDesc(String categoryName) {
		List<Product> sortedList = null;
		try {
			sortedList=customerService.getProductsByPriceDesc(categoryName);
		} catch (Exception e) {
			return sortedList;
		}
		
		return sortedList;
	}
	 @RequestMapping(value="/averageRating")
		public int averageRating(int productId) {
			return customerService.averageRating(productId);
		}
	 
}
