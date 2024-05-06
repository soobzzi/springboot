package spring.service.purchase.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.common.Search;
import spring.domain.Product;
import spring.domain.Purchase;
import spring.service.purchase.PurchaseDao;
import spring.service.purchase.PurchaseService;




@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{

	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	
	public PurchaseServiceImpl() {	
		System.out.println(this.getClass());
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		
		purchaseDao.addPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		
		
		return purchaseDao.getPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search) throws Exception {
		
		
		
		List<Purchase> list = purchaseDao.getPurchaseList(search);
		int totalCount = purchaseDao.getTotalCount(search);
		
		for(int i = 0 ; i<list.size() ; i++) {
			
			Purchase purchase = list.get(i);
			String tranCode = purchase.getTranCode();
			
			
			if(tranCode != null) {
				tranCode = tranCode.trim();
			}
			if(tranCode.equals("0")) {
				tranCode = "현재 구매완료";
			}else if(tranCode.equals("1")) {
				tranCode = "현재 배송중";
			}else if(tranCode.equals("2")){
				tranCode = "배송완료";
			}
			
			purchase.setTranCode(tranCode);
			list.get(i).setTranCode(tranCode);
			System.out.println(":::::::");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		map.put("totalCount", totalCount);
		
		
		return map;
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchase.setDivyDate(purchase.getDivyDate().split(" ")[0]);
		//purchase.setPaymentOption(purchase.getPaymentOption().trim());
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

	@Override
	public void updateTranCodeByProd(Product product) throws Exception {
		purchaseDao.updateTranCodeByProd(product);
		
	}

	
}