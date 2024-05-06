package spring.service.product.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.common.Search;
import spring.domain.Product;
import spring.service.product.ProductDao;
import spring.service.product.ProductService;



@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void addProduct(Product product) throws Exception {
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		productDao.addProduct(product);
		
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.getProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		List<Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		for(int i = 0 ; i<list.size() ; i++) {
			
			Product product = list.get(i);
			String proTranCode = product.getProTranCode();
		
			if(proTranCode != null) {
				proTranCode = proTranCode.trim();
			}
			
			if(proTranCode == null) {
				proTranCode="판매중";
			}else if(proTranCode.equals("0")) {
				proTranCode="판매완료";
			}else if(proTranCode.equals("1")) {
				proTranCode="배송중";
			}else if(proTranCode.equals("2")){
				proTranCode="배송완료";
			}
			
			
			System.out.println("tranCode : " +product.getProTranCode());
			product.setProTranCode(proTranCode);
			
		
		}
	
		
		Map<String,Object> map = new HashMap<>();
		map.put("list",list);
		map.put("totalCount", new Integer(totalCount));
		//여기 복붙한거임... 공부
		
		
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		productDao.updateProduct(product);
		
	}
	





}

