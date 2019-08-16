package com.capgemini.storeserver.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.exceptions.MerchantNotFoundException;
import com.capgemini.storeserver.exceptions.ProductNotFoundException;
import com.capgemini.storeserver.repo.MerchantRepo;
import com.capgemini.storeserver.repo.ProductRepo;

@Service(value="merchantServices")
public class MerchantServicesImpl implements MerchantServices{

	@Autowired
	private MerchantRepo merchantRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public Merchant registerMerchant(Merchant merchant) {
		merchant.setAddMerchantDate(new Date());
		String pwd = Base64Coder.encodeString(merchant.getPassword());
		merchant.setPassword(pwd);
		return merchantRepo.save(merchant);
	}

	@Override
	public Merchant updateMerchantProfile(Merchant merchant) {
		return merchantRepo.save(merchant);
	}

	@Override
	public Merchant deleteMerchantInventory(String username) {
		
		Merchant merchant = merchantRepo.findByUsername(username);
		merchantRepo.deleteById(merchant.getMerchantId());
		return merchant;
	}

	@Override
	public Merchant getMerchant(String username) throws MerchantNotFoundException {
		if(merchantRepo.findByUsername(username) == null) {
			throw new MerchantNotFoundException("No Merchant Found");
		}
		 Merchant merchant = merchantRepo.findByUsername(username);
		 merchant.setPassword(Base64Coder.decodeString(merchant.getPassword()));
		 return merchant;
	}

	@Override
	public Merchant getMerchantById(int merchantId) {
		Merchant merchant = merchantRepo.getOne(merchantId);
		merchant.setPassword(Base64Coder.decodeString(merchant.getPassword()));
		return merchant;
	}

	@Override
	public void changePassword(Merchant merchant, String password) {
		merchant.setPassword(Base64Coder.encodeString(password));
		merchantRepo.save(merchant);
		
	}

	@Override
	public Integer addProduct(Product product) throws ProductNotFoundException {
		
		/*try {
			if(productRepo.getOne(product.getProductId()) != null){
				productRepo.delete(productRepo.getOne(product.getProductId()));
				productRepo.save(product);
				return new Integer(product.getProductId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		productRepo.save(product);
		return new Integer(product.getProductId());
	}

	@Override
	public List<Product> getAllProducts(int merchantId) {
		Merchant merchant = merchantRepo.getOne(merchantId);
		return productRepo.getAllproducts(merchant);
	}


	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
		/*if(productRepo.findById(product.getProductId())==null)
			throw new ProductNotFoundException("product not found");*/
		productRepo.updateProduct(product.getProductId(), product.getProductName(), product.getBrand(), product.getProductQuantityAvailable(), product.getProductPrice(), product.getProductDesc(), product.isProductStatus());
	}

	@Override
	public Product getProductDetails(int productId) {
		return (Product)productRepo.getOne(productId);
	}

	@Override
	public void removeProduct(int productId) {
		productRepo.deleteById(productId);
		
	}

	@Override
	public Merchant findMerchantId(int merchantId) {
		Merchant merchant = merchantRepo.getOne(merchantId);
		return merchant;
	}
}
