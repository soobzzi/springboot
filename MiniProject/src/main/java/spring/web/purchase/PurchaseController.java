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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spring.common.Page;
import spring.common.Search;
import spring.domain.Product;
import spring.domain.Purchase;
import spring.domain.User;
import spring.service.product.ProductService;
import spring.service.purchase.PurchaseService;



@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	@Autowired 
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired 
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	

	@Value("${pageUnit}")
	int pageUnit;
	
	@Value("${pageSize}")
	int pageSize;
	
	
	
	@RequestMapping(value = "addPurchase" ,method=RequestMethod.POST)
	public String AddPurchase(@ModelAttribute("purchase") Purchase purchase ,HttpServletRequest request, HttpSession session ,@RequestParam("prodNo") int prodNo,Model model)throws Exception {
		
		System.out.println("/purchase/addPurchase");
		
		User user = (User)session.getAttribute("user");
		
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
		
		return "forward:/purchase/addPurchase.jsp";
		
	}
	
	@RequestMapping(value= "addPurchase",method=RequestMethod.GET)
	public String addPurchaseView(@RequestParam("prodNo") int prodNo,Model model, HttpSession session)throws Exception{
		
		Product product = productService.getProduct(prodNo);
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(product);
		
		User user = (User)session.getAttribute("user");
		
		
		model.addAttribute("purchase",purchase);
		model.addAttribute("user", user);
	
		
		System.out.println("/purchase/addPurchaseView");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

	
	@RequestMapping(value="getPurchase")
	public String getPurchase(@RequestParam("tranNo") int tranNo ,Model model) throws Exception {
	
		System.out.println("/getPurchase");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		model.addAttribute("purchase",purchase);
		
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	
	
	@RequestMapping(value="listPurchase")
	public String ListPurchase(@ModelAttribute("search") Search search ,Model model,HttpServletRequest request)throws Exception{
		
		System.out.println("/purchase/listPurchase");
		int page = 1;
		if(search.getCurrentPage() != 0)
		page = search.getCurrentPage();
		
		search.setCurrentPage(page);	
		search.setPageSize(pageSize);		
	
		Map<String, Object> map = purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page(page,(int)map.get("totalCount"),pageUnit,pageSize);
		System.out.println( "::"+resultPage);
		
		model.addAttribute("map", map);
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("listType", "Purchase");
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="listSale")
	public String ListSale(@RequestParam("prodNo") int prodNo,Model model)throws Exception{
		
		System.out.println("/ListSale");
		Product product= productService.getProduct(prodNo);
		
		model.addAttribute("product",product);
		
		
		return "forward:/purchase/listProduct?menu=manage";
	}
	
	@RequestMapping(value="updatePurchase")
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase,HttpSession session)throws Exception{
		
		
		System.out.println("/purchase/updatePurchase"); 
		
		session.getAttribute("user");
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setDivyDate(purchase.getDivyDate().split(" ")[0]);
		//purchase.setPaymentOption(purchase.getPaymentOption().trim());
		//int tranNo = purchase.getTranNo();
		
		
		purchaseService.updatePurchase(purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	
	
	@RequestMapping(value="updatePurchaseView")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo ,Model model,HttpSession session)throws Exception{
		
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		User user = (User)session.getAttribute("user");
		
		purchase.setDivyDate(purchase.getDivyDate().split(" ")[0]);
		//purchase.setPaymentOption(purchase.getPaymentOption().trim());
		purchase.setBuyer(user);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	
	@RequestMapping(value="updateTranCode")
	public String updateTranCode(@ModelAttribute("purchase") Purchase purchase)throws Exception{
		
		System.out.println("/updateTranCode");
		
		int tranNo = purchase.getTranNo();
		purchase.setTranNo(tranNo);
		
		purchaseService.updateTranCode(purchase);
		
		
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping(value="updateTranCodeByProd")
	public String updateTranCodeByProd(@ModelAttribute("product") Product product)throws Exception{
		
		System.out.println("/updateTranCodeByProd");
				
		int prodNo = product.getProdNo();
		product.setProdNo(prodNo);
		
		purchaseService.updateTranCodeByProd(product);
		
		return "forward:/product/listProduct?menu=manage";
	}
	
	
	
}
