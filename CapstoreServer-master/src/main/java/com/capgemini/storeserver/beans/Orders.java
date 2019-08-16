package com.capgemini.storeserver.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	// changed
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();// one to many
	private int userId;
	private double totalAmount;
	private String deliveryStatus;// placed, shipped, out for delivery
	private boolean statusOfTransaction; // Success or fail
	private String modeOfPurchase;// can be COD, Online Purchase
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Merchant> merchant = new ArrayList<>();// one to many
	private Date elligibleReturnDate;
	private Date orderPlacedOn;
	@ManyToOne
	@JoinColumn(name="phoneNumber")
	private Customer customer;
	private boolean refundRequest;
	private Date refundRequestDate;
	
	
	public Orders() {
		super();
	}

	
	public Date getRefundRequestDate() {
		return refundRequestDate;
	}

	public void setRefundRequestDate(Date refundRequestDate) {
		this.refundRequestDate = refundRequestDate;
	}

	

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public boolean isStatusOfTransaction() {
		return statusOfTransaction;
	}

	public void setStatusOfTransaction(boolean statusOfTransaction) {
		this.statusOfTransaction = statusOfTransaction;
	}

	public String getModeOfPurchase() {
		return modeOfPurchase;
	}

	public void setModeOfPurchase(String modeOfPurchase) {
		this.modeOfPurchase = modeOfPurchase;
	}

	public List<Merchant> getMerchant() {
		return merchant;
	}

	public void setMerchant(List<Merchant> merchant) {
		this.merchant = merchant;
	}

	public Date getElligibleReturnDate() {
		return elligibleReturnDate;
	}

	public void setElligibleReturnDate(Date elligibleReturnDate) {
		this.elligibleReturnDate = elligibleReturnDate;
	}

	public Date getOrderPlacedOn() {
		return orderPlacedOn;
	}

	public void setOrderPlacedOn(Date orderPlacedOn) {
		this.orderPlacedOn = orderPlacedOn;
	}

	public boolean isRefundRequest() {
		return refundRequest;
	}

	public void setRefundRequest(boolean refundRequest) {
		this.refundRequest = refundRequest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		return result;
	}

}
