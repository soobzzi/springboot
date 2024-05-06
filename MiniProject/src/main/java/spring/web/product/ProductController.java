
package spring.web.product;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import spring.common.Page;
import spring.common.Search;
import spring.domain.Product;
import spring.service.product.ProductService;



@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired 
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("${pageUnit}")
	int pageUnit;
	
	@Value("${pageSize}")
	int pageSize;
	
	@Value("${upload.path}")
    private String uploadPath;
	
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product, 
	                         @RequestPart(value = "imageFile", required = false) List<MultipartFile> imageFiles,
	                         Model model) throws Exception {

		if (imageFiles == null || imageFiles.isEmpty()) {
			throw new RuntimeException("업로드된 파일이 없습니다.");
		}

		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file : imageFiles) {
			if (file.isEmpty()) {
				continue; // Skip empty files.
			}

			String uuidFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			File saveFile = new File(uploadPath, uuidFileName);

			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				System.err.println("Failed to upload " + file.getOriginalFilename());
				e.printStackTrace();
				continue; // Skip files that fail to save.
			}

			// Append the successfully saved file name to the list.
			if (fileNames.length() > 0) {
				fileNames.append("/");
			}
			fileNames.append(uuidFileName);
		}

		if (fileNames.length() == 0) {
			throw new RuntimeException("파일 업로드에 실패했습니다.");
		}

		// Set the file name(s) in product object
		product.setFileName(fileNames.toString());
		productService.addProduct(product);

		model.addAttribute("product", product);
		return "forward:/product/addProduct.jsp";
	}

	
	@RequestMapping(value = "getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo ,@RequestParam("menu") String menu, Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		System.out.println("/product/getProduct");
		
				Product product = productService.getProduct(prodNo);
		System.out.println(product);
		model.addAttribute("product",product);
		
		if(menu.equals("manage")) {
			return "forward:/product/updateProductView.jsp";
			
		}else if(menu.equals("update")){
			return "forward:/product/updateProduct.jsp";	
		}	
		
		Cookie[] cookies = request.getCookies();
		//������Ʈ�� �����ϴ� ��� ��Ű ������
		String history = null;
		
		boolean coo = true;
		
		int index = 0;
		
		for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					
					coo = false;
					index = i;
					break;				
				}				
			}
		
		if (coo) {		
			Integer itg = new Integer(prodNo);
			Cookie cookie = new Cookie("history",itg.toString());
			
			response.addCookie(cookie);	
			
		}else {	
			history = cookies[index].getValue();
					
					history += ":"+ product.getProdNo();
					cookies[index].setValue(history);
					
					cookies[index].setPath("/");
					response.addCookie(cookies[index]);
					
		}
		
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping(value = "listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model ,HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct :  GET");
		int page = 1;
		if(search.getCurrentPage() != 0) 
			page = search.getCurrentPage();
		
		search.setCurrentPage(page);
		search.setSearchCondition(search.getSearchCondition());
		search.setSearchKeyword(search.getSearchKeyword());
		
		search.setPageSize(pageSize);
		
		Map<String,Object> map = productService.getProductList(search);
		
		
		
		Page resultPage = new Page(page,((Integer)(map.get("totalCount"))).intValue(),pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("resultPage",resultPage);
		model.addAttribute("map",map);
		model.addAttribute("search",search);
		model.addAttribute("listType","Product");
		
		
		
		return "forward:/product/listProduct.jsp?menu="+request.getParameter("menu");
	}
	
	@RequestMapping(value = "updateProduct")
	public String updateProduct(@ModelAttribute("product") Product product, Model model) throws Exception{
		
		System.out.println("/product/updateProduct");
			
		
		
		int prodNo = product.getProdNo();
		//product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.updateProduct(product);
		
		
		
		return "forward:/product/getProduct?prodNo="+prodNo+"&menu=update";
	}
	
//	@RequestMapping("/updateProductView.do")
//	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
//		
//		System.out.println("/updateProductView.do");
//		
//		Product product = productService.getProduct(prodNo);
//		
//		model.addAttribute("product",product);
//		
//		
//		return "forward:/product/updateproduct.jsp";
//	}
//	

	

	
}
