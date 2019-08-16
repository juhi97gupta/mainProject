package com.capgemini.storeserver.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.storeserver.beans.Category;
import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	@Transactional
	@Modifying
	@Query("UPDATE Product p set p.productName =:name,  p.brand =:brand,p.productQuantityAvailable =:quantity, p.productPrice =:price, p.productDesc =:desc,p.productStatus =:status WHERE p.productId =:id")
	public void updateProduct(@Param("id")int id,@Param("name")String name,@Param("brand")String brand,@Param("quantity")int quantity,@Param("price")double price,@Param("desc")String desc,@Param("status")boolean status);

	public Product findByProductId(int productId);

	public Product findByProductName(String productName);
	
	@Transactional
	@Query("SELECT p FROM Product p WHERE p.merchant =:merchantId")
	public List<Product> getAllproducts(@Param("merchantId")Merchant merchant);
	
	public List<Product> findByCategoryOrderByProductPriceAsc(Category category);
	
	public List<Product> findByCategoryOrderByProductPriceDesc(Category category);
  
	
}
