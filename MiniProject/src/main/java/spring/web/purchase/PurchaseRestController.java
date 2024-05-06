package spring.web.purchase;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.common.Page;
import spring.common.Search;
import spring.domain.Product;
import spring.domain.Purchase;
import spring.domain.User;
import spring.service.product.ProductService;
import spring.service.purchase.PurchaseService;


@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	@Autowired 
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired 
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseRestController() {
		System.out.println(this.getClass());
	}
	

	@Value("${pageUnit}")
	int pageUnit;
	
	@Value("${pageSize}")
	int pageSize;
	
	
	
	@RequestMapping(value = "/json/addPurchase" ,method=RequestMethod.POST)
	public Purchase AddPurchase(@RequestBody Purchase purchase ,HttpServletRequest request, HttpSession session ,@RequestParam("prodNo") int prodNo, Model model)throws Exception {
		
		System.out.println("/purchase/addPurchase");
		
		User user = (User) session.getAttribute("user");
		
		Product product = new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone").replaceAll("-", ""));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate").replaceAll("-", ""));
		purchase.setTranCode("0");
		
		System.out.println(purchase.toString());
	
		model.addAttribute("prodNo",prodNo);
		purchaseService.addPurchase(purchase);
		
		return purchase;
		
	}
	
	@RequestMapping(value= "/json/addPurchaseView/{prodNo}",method=RequestMethod.GET)
	public Map addPurchaseView(@PathVariable int prodNo,HttpSession session)throws Exception{
		
		
		Map<String, Object> map = new HashMap();
		
		
		Product product = productService.getProduct(prodNo);
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(product);
		session.getAttribute("user");
		
		map.put("purchase",purchase);
		map.put("user", (User)session.getAttribute("user"));
		
		System.out.println("/purchase/addPurchaseView");
		
		return map;
	}

	
	@RequestMapping(value="/json/getPurchase")
	public Purchase getPurchase(@PathVariable int tranNo ,Model model) throws Exception {
	
		System.out.println("/getPurchase");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase",purchase);
		
		
		return purchase;
	}
	
	
	
	@RequestMapping(value="/json/listPurchase")
	public Map ListPurchase(@ModelAttribute("search") Search search ,Model model,HttpServletRequest request)throws Exception{
		
		System.out.println("/purchase/listPurchase");
				
		int page = 1;
		if(search.getCurrentPage() != 0)
		page = search.getCurrentPage();
		
		search.setCurrentPage(page);	
		search.setPageSize(pageSize);		
	
		Map<String, Object> map = purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page(page,(int)map.get("totalCount"),pageUnit,pageSize);
		System.out.println( "::"+resultPage);
		
		map.put("map", map);
		map.put("search", search);
		map.put("resultPage", resultPage);
		map.put("listType", "Purchase");
		
		return map;
	}
	
//	@RequestMapping(value="listSale")
//	public String ListSale(@RequestParam("prodNo") int prodNo,Model model)throws Exception{
//		
//		System.out.println("/ListSale");
//		Product product= productService.getProduct(prodNo);
//		
//		model.addAttribute("product",product);
//		
//		
//		return "forward:/purchase/listProduct?menu=manage";
//	}
	
	@RequestMapping(value="/json/updatePurchase")
	public Purchase updatePurchase(@RequestBody Purchase purchase,HttpSession session)throws Exception{
		
		
		System.out.println("/purchase/updatePurchase"); 
		
		session.getAttribute("user");
		
		purchase.setBuyer((User)session.getAttribute("user"));
		//purchase.setDivyDate(purchase.getDivyDate().split(" ")[0]);
		
		//int tranNo = purchase.getTranNo();
		
		
		purchaseService.updatePurchase(purchase);
		
		return purchase;
	}
	
	
	
	@RequestMapping(value="/json/updatePurchaseView")
	public Purchase updatePurchaseView(@RequestParam("tranNo") int tranNo ,Model model)throws Exception{
		
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		purchase.setDivyDate(purchase.getDivyDate().split(" ")[0]);
		model.addAttribute("purchase", purchase);
		
		return purchase;
	}
	
	
	@RequestMapping(value="/json/updateTranCode")
	public Purchase updateTranCode(@RequestBody Purchase purchase)throws Exception{
		
		System.out.println("/updateTranCode");
		
		int tranNo = purchase.getTranNo();
		purchase.setTranNo(tranNo);
		
		purchaseService.updateTranCode(purchase);
		
		
		return purchase;
	}
	
	@RequestMapping(value="/json/updateTranCodeByProd")
	public Product updateTranCodeByProd(@RequestBody Product product)throws Exception{
		
		System.out.println("/updateTranCodeByProd");
				
		int prodNo = product.getProdNo();
		product.setProdNo(prodNo);
		
		purchaseService.updateTranCodeByProd(product);
		
		return product;
	}
	
	
	
}
