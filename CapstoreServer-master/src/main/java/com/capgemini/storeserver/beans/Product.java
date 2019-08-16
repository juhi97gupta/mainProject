package com.capgemini.storeserver.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	private String productName;
	private String productDesc;
	private String productImageURL;
	private String productBannerURL;
	private double productPrice;
	private int productQuantityAvailable;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> review = new ArrayList<>();// one to many
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "discountId")
	private Discount discount;// one to one
	private long productViews;
	private long productTimesBought;
	private int cartQuantity;
	private boolean productStatus;
	private Date deliveryDate;
	@ManyToOne
	@JoinColumn(name = "wishlistId")
	private Wishlist wishlist;
	// changed
	@ManyToOne
	@JoinColumn(name = "merchantId")
	private Merchant merchant;
	private Date productAddedDate;
	private String brand;
	private Date productRemovedDate;

	// changed
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Orders order;
	@ManyToOne
	@JoinColumn(name = "inventoryId")
	private Inventory inventory;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	private int averageRating;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
	private List<Cart> carts = new ArrayList<Cart>();

	public Product() {
		super();
	}

	public Product(int productId) {
		super();
		this.productId = productId;
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public String getProductImageURL() {
		return productImageURL;
	}

	public void setProductImageURL(String productImageURL) {
		this.productImageURL = productImageURL;
	}

	public String getProductBannerURL() {
		return productBannerURL;
	}

	public void setProductBannerURL(String productBannerURL) {
		this.productBannerURL = productBannerURL;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public int getProductQuantityAvailable() {
		return productQuantityAvailable;
	}

	public void setProductQuantityAvailable(int productQuantityAvailable) {
		this.productQuantityAvailable = productQuantityAvailable;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public long getProductViews() {
		return productViews;
	}

	public void setProductViews(long productViews) {
		this.productViews = productViews;
	}

	public long getProductTimesBought() {
		return productTimesBought;
	}

	public void setProductTimesBought(long productTimesBought) {
		this.productTimesBought = productTimesBought;
	}

	public boolean isProductStatus() {
		return productStatus;
	}

	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getProductAddedDate() {
		return productAddedDate;
	}

	public void setProductAddedDate(Date productAddedDate) {
		this.productAddedDate = productAddedDate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getProductRemovedDate() {
		return productRemovedDate;
	}

	public void setProductRemovedDate(Date productRemovedDate) {
		this.productRemovedDate = productRemovedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		return result;
	}

	

}
