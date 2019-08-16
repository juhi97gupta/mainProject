package com.capgemini.storeserver.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.storeserver.beans.Address;
import com.capgemini.storeserver.beans.Cart;
import com.capgemini.storeserver.beans.Category;
import com.capgemini.storeserver.beans.Coupon;
import com.capgemini.storeserver.beans.Customer;
import com.capgemini.storeserver.beans.Discount;
import com.capgemini.storeserver.beans.Orders;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.beans.Review;
import com.capgemini.storeserver.beans.Wishlist;
import com.capgemini.storeserver.exceptions.CustomerNotFoundException;
import com.capgemini.storeserver.exceptions.InvalidInputException;
import com.capgemini.storeserver.exceptions.ProductUnavailableException;
import com.capgemini.storeserver.repo.AddressRepo;
import com.capgemini.storeserver.repo.CartRepo;
import com.capgemini.storeserver.repo.CategoryRepo;
import com.capgemini.storeserver.repo.CustomerRepo;
import com.capgemini.storeserver.repo.OrdersRepo;
import com.capgemini.storeserver.repo.ProductRepo;
import com.capgemini.storeserver.repo.WishlistRepo;

@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

	Customer customer;
	Review review;
	Product product;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private OrdersRepo ordersRepo;

	@Override
	public Customer signUp(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public String forgotPassword(String mobileNumber) throws CustomerNotFoundException {

		customer = customerRepo.getOne(mobileNumber);
		if (customer == null)
			throw new CustomerNotFoundException("customer not found with mobile no.");
		else
			return customer.getSecurityQuestion();
	}

	@Override // encryption
	public String securityQuestion(String phoneNumber, String securityAnswer) throws InvalidInputException {
		customer = customerRepo.getOne(phoneNumber);
		if (securityAnswer.equals(customer.getSecurityAnswer())) {
			return customer.getPassword();
		} else
			throw new InvalidInputException("Invalid answer");
	}

	@Override
	public void onlinePayment(String cardNumber, String customerPhoneNumber) {
		customer = new Customer();
		customer = customerRepo.getOne(customerPhoneNumber);
		customer.setCardNumber(cardNumber);
		customerRepo.save(customer);

	}

	@Override
	public void addAddress(Address address) {
		addressRepo.save(address);
	}

	@Override
	public Address getAddress(int addressId) {
		return addressRepo.getOne(addressId);
	}

	public boolean isRefundRequestValid(Orders order) {
		Date date1 = order.getElligibleReturnDate();
		Date date2 = order.getRefundRequestDate();
		if (date1.after(date2)) {
			return false;
		} else
			return true;

	}

	@Override
	public double applyDiscount(int productId) {
		product = productRepo.getOne(productId);

		Discount discount = product.getDiscount();

		double price = product.getProductPrice();
		double finalPrice = price;
		if (discountIsValid(discount)) {
			double percentDiscount = discount.getPercentDiscount();
			finalPrice = price - ((price * percentDiscount) / 100);
			product.setProductPrice(finalPrice);
		}
		return finalPrice;
	}

	public boolean discountIsValid(Discount discount) {

		Date date2 = discount.getEndDateOfDiscount();
		Date date1 = discount.getStartDateOfDiscount();
		if (date1.before(new Date()) && date2.after(new Date())) {

			return true;
		}
		return false;
	}

	@Override

	public double applyCoupon(int cartId) {
		Cart cart = cartRepo.getOne(cartId);
		Coupon coupon = cart.getCoupon();
		double cartAmount = cart.getTotalAmount();
		double finalPrice = cartAmount;
		if (couponIsValid(coupon)) {
			double couponDiscount = coupon.getCouponDiscountValue();
			finalPrice = cartAmount - ((cartAmount * couponDiscount) / 100);
			cart.setTotalAmount(finalPrice);
		}
		return finalPrice;
	}

	public boolean couponIsValid(Coupon coupon) {
		Date date2 = coupon.getCouponEndDate();
		Date date1 = coupon.getCouponStartDate();
		if (date1.before(new Date()) && date2.after(new Date())) {

			return true;
		}
		return false;
	}

	@Override
	public List<Product> getAllProductsFromCart(String phoneNumber) throws InvalidInputException {
		Cart cart;

		customer = customerRepo.getOne(phoneNumber);
		cart = cartRepo.findByCustomer(customer);
		return cart.getProducts();
	}

	@Override
	public void buyNowProduct(int productId, String phoneNumber, int quantity) {
		product = productRepo.getOne(productId);
		if (product.getProductQuantityAvailable() > quantity) {
			product.setProductQuantityAvailable(product.getProductQuantityAvailable() - quantity);
			product.setProductStatus(true);
		}
	}

	@Override
	public void buyNowCart(String phoneNumber) throws ProductUnavailableException {
		customer = customerRepo.getOne(phoneNumber);
		Cart cart = cartRepo.findByCustomer(customer);
		String error = "This quantity of the product is unavailable";
		List<Product> products = cart.getProducts();
		for (Product product : products) {
			if (product.getProductQuantityAvailable() > product.getCartQuantity()) {
				product.setProductQuantityAvailable(product.getProductQuantityAvailable() - product.getCartQuantity());
			} else
				throw new ProductUnavailableException(error);
		}

	}

	// gvk
	@Override
	public boolean updateSecurityQuestion(String phoneNumber, String securityQuestion) throws InvalidInputException {
		customer = customerRepo.getOne(phoneNumber);
		if (customer != null) {
			if (customer.getSecurityQuestion().equals(securityQuestion)) {
				return false;
			} else {
				customer.setSecurityQuestion(securityQuestion);
				customerRepo.save(customer);
				return true;
			}
		} else
			return false;
	}

	@Override
	public boolean updateSecurityAnswer(String phoneNumber, String securityAnswer) throws InvalidInputException {
		customer = customerRepo.getOne(phoneNumber);
		if (customer != null) {
			if (customer.getSecurityAnswer().equals(securityAnswer)) {
				return false;
			} else {
				customer.setSecurityAnswer(securityAnswer);
				customerRepo.save(customer);
				return true;
			}

		} else
			return false;
	}

	@Override
	public boolean updateCardNumber(String phoneNumber, String cardNumber) throws InvalidInputException {
		customer = customerRepo.getOne(phoneNumber);
		if (customer != null) {
			if (customer.getCardNumber().equals(cardNumber)) {
				return false;
			} else {
				customer.setCardNumber(cardNumber);
				customerRepo.save(customer);
				return true;
			}
		} else
			return false;
	}

	@Override
	public boolean updateCustomerName(String phoneNumber, String customerName) throws InvalidInputException {
		customer = customerRepo.getOne(phoneNumber);
		if (customer != null) {
			if (customer.getCustomerName().equals(customerName)) {
				return false;
			} else {
				customer.setCustomerName(customerName);
				customerRepo.save(customer);
				return true;
			}
		} else
			return false;
	}

	@Override
	public Orders getTransaction(int orderId) throws InvalidInputException {
		return ordersRepo.getOne(orderId);
	}

	@Override
	public Product searchByProductName(String productName) throws InvalidInputException {

		product = productRepo.findByProductName(productName);
		if (product != null)
			return product;
		else
			throw new InvalidInputException("Product with this name not found");
	}

	@Override
	public Customer customerSignIn(String email, String inputPassword) throws InvalidInputException {
		customer = customerRepo.findByEmail(email);
		if (customer != null) {
			String password = customer.getPassword();

			if (password.equals(inputPassword)) {
				return customer;
			} else {
				throw new InvalidInputException("Incorrect Password");
			}
		} else {
			throw new InvalidInputException("Account with this email doesnot exist");
		}
	}

	@Override
	public Customer getCustomerDetails(String phoneNumber) throws InvalidInputException {

		return customerRepo.getOne(phoneNumber);
	}

	@Override
	public List<Product> getAllProducts() throws InvalidInputException {
		if (productRepo.findAll() != null)
			return productRepo.findAll();
		else
			throw new InvalidInputException();

	}

	@Override
	public Product getProductById(int productId) throws InvalidInputException {
		if (productRepo.findByProductId(productId) != null)
			return productRepo.findByProductId(productId);
		else
			throw new InvalidInputException();
	}

	@Override
	public List<Product> getProductByCategory(Category category) throws InvalidInputException {
		String categoryName = category.getCategoryName();
		category = categoryRepo.findByCategoryName(categoryName);
		return category.getProducts();
	}

	@Override
	public void setReviewMethod(String phoneNumber, int rating, String comments, int productId)
			throws InvalidInputException {
		review = new Review();
		review.getProduct().setProductId(productId);
		review.getCustomer().setPhoneNumber(phoneNumber);
		review.setComments(comments);
		review.setProductRating(rating);
		// saving reviews in customer
		customer = customerRepo.getOne(phoneNumber);
		List<Review> customerReviewList = customer.getReviews();
		customerReviewList.add(review);
		customer.setReviews(customerReviewList);
		customerRepo.save(customer);
		// saving reviews in product
		product = productRepo.getOne(productId);
		List<Review> productReviewList = product.getReview();
		productReviewList.add(review);
		product.setReview(productReviewList);
		productRepo.save(product);
	}

	@Override
	public String getDeliveryStatus(int orderId) throws InvalidInputException {
		Orders order = ordersRepo.getOne(orderId);
		return order.getDeliveryStatus();
	}

	@Override
	public boolean addProductToWishlist(String phoneNumber, int productId) throws InvalidInputException {
		product = productRepo.getOne(productId);
		customer = customerRepo.getOne(phoneNumber);
		Wishlist wishlist = wishlistRepo.findByCustomer(customer);
		if (wishlist == null) {
			wishlist = new Wishlist();
			product.setWishlist(wishlist);
			wishlist.setCustomer(customer);
			List<Product> productsList = new ArrayList<Product>();
			productsList.add(product);
			wishlist.setProducts(productsList);
			wishlistRepo.save(wishlist);

		} else {
			product.setWishlist(wishlist);
			List<Product> productsList = wishlist.getProducts();
			productsList.add(product);
			wishlist.setProducts(productsList);
			wishlistRepo.save(wishlist);

		}
		return true;
	}

	@Override
	public boolean removeProductFromWishlist(String phoneNumber, int productId) throws InvalidInputException {
		product = productRepo.getOne(productId);
		customer = customerRepo.getOne(phoneNumber);
		Wishlist wishlist = wishlistRepo.findByCustomer(customer);
		List<Product> productsList = wishlist.getProducts();
		productsList.remove(product);
		wishlist.setProducts(productsList);
		product.setWishlist(null);
		wishlistRepo.save(wishlist);
		return true;
	}

	@Override
	public List<Product> getWishlist(String phoneNumber) throws InvalidInputException {
		Wishlist wishlist = new Wishlist();
		try {
			customer = customerRepo.getOne(phoneNumber);
			wishlist = wishlistRepo.findByCustomer(customer);
		} catch (Exception e) {
			e.getMessage();
		}
		return wishlist.getProducts();
	}

	@Override
	public boolean changePassword(String mobileNumber, String newPassword)
			throws InvalidInputException, CustomerNotFoundException {
		customer = customerRepo.getOne(mobileNumber);
		if (customer == null) {
			return false;
		} else {
			if (!customer.getPassword().equals(newPassword)) {
				customer.setPassword(newPassword);
				customerRepo.save(customer);
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public List<Orders> getAllOrders(String phoneNumber) throws CustomerNotFoundException {
		customer = customerRepo.getOne(phoneNumber);
		if (customer == null) {
			throw new CustomerNotFoundException("customer not found with mobile no.");
		} else {
			return customer.getOrders();
		}
	}

	@Override
	public Cart addProductToNewCart(String phoneNumber, int productId, int quantity)
			throws ProductUnavailableException {
		product = productRepo.getOne(productId);
		String error = "This quantity of the product is not available";
		if (product.getProductQuantityAvailable() > quantity) {
			Cart cart = new Cart();
			List<Product> products = new ArrayList<Product>();
			product.setCartQuantity(quantity);
			products.add(product);
			double productPrice = product.getProductPrice();
			double amount = productPrice * quantity;
			cart.setTotalAmount(amount);
			cart.setProducts(products);
			cart.setCustomer(customerRepo.getOne(phoneNumber));
			return cartRepo.save(cart);
		} else
			throw new ProductUnavailableException(error);
	}

	@Override
	public Cart updateCart(String phoneNumber, int productId, int quantity) throws ProductUnavailableException {
		customer = customerRepo.getOne(phoneNumber);

		Cart cart = cartRepo.findByCustomer(customer);
		String error = "This quantity of the product is not Available";

		List<Product> products = cart.getProducts();

		int productIndex = products.indexOf(new Product(productId));

		product = products.get(productIndex);
		if (product.getProductQuantityAvailable() > quantity) {
			if (product.getCartQuantity() > quantity) {
				double productPrice = product.getProductPrice();
				double productAmount = productPrice * (product.getCartQuantity() - quantity);
				double amount = cart.getTotalAmount();
				double totalAmount = amount - productAmount;
				product.setCartQuantity(quantity);
				cart.setTotalAmount(totalAmount);
			} else if (product.getCartQuantity() < quantity) {
				double productPrice = product.getProductPrice();
				double productAmount = productPrice * (quantity - product.getCartQuantity());
				double amount = cart.getTotalAmount();
				double totalAmount = amount + productAmount;
				cart.setTotalAmount(totalAmount);
			}
			products.set(productIndex, product);
			cart.setProducts(products);
			return cartRepo.save(cart);
		} else
			throw new ProductUnavailableException(error);
	}

	@Override
	public Cart removeProductFromCart(String phoneNumber, int productId) {
		customer = customerRepo.getOne(phoneNumber);
		Cart cart = cartRepo.findByCustomer(customer);
		List<Product> products = cart.getProducts();
		int productIndex = products.indexOf(new Product(productId));
		
		products.remove(productIndex);
		cart.setProducts(products);
		return cartRepo.save(cart);
		
	}

	@Override
	public boolean returnGoods(int orderId) {
		Orders order = ordersRepo.getOne(orderId);
		Date refundDate = order.getRefundRequestDate();
		boolean refundRequest = order.isRefundRequest();
		Date returnDate = order.getElligibleReturnDate();
		if (refundRequest == true) {
			if (refundDate.before(returnDate)) {
				order.setRefundRequest(false);
				ordersRepo.save(order);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Product> getProductsByPriceAsc(String categoryName) throws ProductUnavailableException {
		Category category = categoryRepo.findByCategoryName(categoryName);
		List<Product> productsList = productRepo.findByCategoryOrderByProductPriceAsc(category);
		return productsList;
	}

	@Override
	public List<Product> getProductsByPriceDesc(String categoryName) throws ProductUnavailableException {
		Category category = categoryRepo.findByCategoryName(categoryName);
		List<Product> productsList = productRepo.findByCategoryOrderByProductPriceDesc(category);
		return productsList;
	}

	@Override
	public int averageRating(int productId) {
		Product prod = new Product();
		int sum = 0, averageRating = 0;
		prod = productRepo.getOne(productId);
		List<Review> reviews = prod.getReview();
		for (Review review : reviews) {
			sum += review.getProductRating();
		}
		averageRating = sum / (reviews.size());
		prod.setAverageRating(averageRating);
		productRepo.save(prod);
		return averageRating;
	}

}
