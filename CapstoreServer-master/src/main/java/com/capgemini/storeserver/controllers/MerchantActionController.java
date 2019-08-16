package com.capgemini.storeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.exceptions.MerchantNotFoundException;
import com.capgemini.storeserver.exceptions.ProductNotFoundException;
import com.capgemini.storeserver.services.MerchantServices;

@RestController
public class MerchantActionController {

	@Autowired
	private MerchantServices merchantService;

	@RequestMapping(value = "/merchantSignIn", method = RequestMethod.POST)
	public void addMerchant(@RequestBody Merchant merchant) {

		merchantService.registerMerchant(merchant);
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public int addProduct(@RequestParam("merchantId") int merchantId, @RequestBody Product product) {

		Merchant merchant = new Merchant(merchantId);
		int id = 0;
		product.setMerchant(merchant);
		try {
			id = merchantService.addProduct(product);
		} catch (ProductNotFoundException e) {

			return id;
		}
		return id;
	}

	 	

	@RequestMapping(value = "removeProduct")
	public void removeProduct(int productId) {

		merchantService.removeProduct(productId);
	}

	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) {
		try {
			merchantService.updateProduct(product);
		} catch (ProductNotFoundException e) {

			return null;
		}
		return product;
	}

	@RequestMapping(value = "myProfilesuccess")
	public Merchant myProfile(int merchantId) {
		Merchant merchant = merchantService.findMerchantId(merchantId);
		return merchant;
	}

	@RequestMapping(value = "getAllProducts", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAllProduct(@RequestParam("merchantId") int merchantId) {

		List<Product> product = merchantService.getAllProducts(merchantId);
		
		return product;
	}

	@RequestMapping(value = "/getProductDetails")
	public Product getProductDetails(@RequestParam("productId") int productId) {

		Product product = merchantService.getProductDetails(productId);
		return product;
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public void changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password, @RequestParam("merchantId") int merchantId) {
		Merchant merchant = merchantService.findMerchantId(merchantId);
		if (Base64Coder.decodeString(merchant.getPassword()).compareTo(oldPassword) != 0) {
			merchantService.changePassword(merchant, password);
		}
	}

	@RequestMapping(value = "forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(@RequestParam("email") String email,
			@RequestParam("securityAnswer") String securityAnswer) {
		try {

			Merchant merchant = merchantService.getMerchant(email);
			
			if (merchant.getSecurityAnswer().compareTo(securityAnswer) == 0) {
				return merchant.getPassword();
			}
		} catch (MerchantNotFoundException e) {

			return null;

		}
		return "Password not found";
	}
}
