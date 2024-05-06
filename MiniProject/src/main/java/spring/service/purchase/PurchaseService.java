package spring.service.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spring.common.Search;
import spring.domain.Product;
import spring.domain.Purchase;




public interface PurchaseService {
	
	
	public void addPurchase(Purchase purchase) throws Exception;
	
	public Purchase getPurchase(int tranNo) throws Exception;
	
	public Map<String,Object> getPurchaseList(Search search) throws Exception;

	public HashMap<String,Object> getSaleList(Search search) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public void updateTranCodeByProd(Product product) throws Exception;	
}
