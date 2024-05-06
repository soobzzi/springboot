package spring.service.product;


import java.util.Map;

import spring.common.Search;
import spring.domain.Product;


public interface ProductService {
	
	public void addProduct(Product product) throws Exception;
	
	public Product getProduct (int prodNo) throws Exception;
	
	public Map<String, Object> getProductList (Search search) throws Exception;

	public void updateProduct(Product product) throws Exception;
}
