package spring.service.product;


import java.util.List;

import spring.common.Search;
import spring.domain.Product;


public interface ProductDao {
	
	public void addProduct(Product product) throws Exception;
	
	public List<Product> getProductList (Search search) throws Exception;

	public void updateProduct(Product product) throws Exception;

	public Product getProduct(int prodNo) throws Exception;

	public int getTotalCount(Search search) throws Exception ;

}
