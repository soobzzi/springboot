package spring.service.purchase;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import spring.common.Search;
import spring.domain.Product;
import spring.domain.Purchase;



public interface PurchaseDao{
	
	public void addPurchase(Purchase purchase) throws Exception;
	
	
	public Purchase getPurchase(int tranNo) throws Exception;
		
	
	public List<Purchase> getPurchaseList(Search search) throws Exception;
		

	
	public void updatePurchase(Purchase purchase) throws Exception;
		

	
	public void updateTranCode(Purchase purchase) throws Exception;
		
		
	
	public void updateTranCodeByProd(Product product) throws Exception;
	
	
	
	public int getTotalCount(Search search) throws Exception;
	
}


