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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.common.Page;
import spring.common.Search;
import spring.domain.Product;
import spring.service.product.ProductService;


@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	@Autowired 
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("${pageUnit}")
	int pageUnit;
	
	@Value("${pageSize}")
	int pageSize;
	
	@Value("${upload.path}")
    private String uploadPath;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value = "/json/addProduct", method = RequestMethod.POST)
	public Product addProductPost(@RequestBody Product product, @RequestPart(value="imageFile" ,required = false) List<MultipartFile> imageFile ,HttpServletRequest request) throws Exception{
		
		System.out.println("/product/addProduct");
		
		//product.setManuDate(product.getManuDate().replaceAll("-", ""));
		int size = imageFile.size();
		String uploadFileName = null;
		
		
		for(int i = 0 ; i<size; i++ ) {
			
			String uuidFileName = null;
			String uuid = UUID.randomUUID().toString();
			
			MultipartFile file = imageFile.get(i);
			String fileName = file.getOriginalFilename();
			
			System.out.println("::"+ fileName);
			
			uuidFileName = uuid+ "_" +fileName;
			
			File saveFile = new File(uploadPath, uuidFileName);
			
			try {
				file.transferTo(saveFile);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(i==0) {
				uploadFileName = uuidFileName;
			} else {
				uploadFileName = uploadFileName + "/" + uuidFileName;
			}
					
			product.setFileName(uploadFileName);
		}
		productService.addProduct(product);
		
		return product;
	}
	

	@RequestMapping(value = "/json/addProduct")
	public Product addProductGet(@ModelAttribute("product") Product product) throws Exception{
		
		System.out.println("/product/addProduct");
		
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		
		productService.addProduct(product);
		
		return product;
	}
	
	
	
	
	@RequestMapping(value = "/json/getProduct/{prodNo}" , method = RequestMethod.GET)
	public Map getProduct (@PathVariable int prodNo , @PathVariable(required = false) String menu , HttpServletRequest request ) throws Exception {
		
		System.out.println("/product/json/getProduct");
		
		Map<String, Object> map = new HashMap();
		
		Product product = productService.getProduct(prodNo);
		System.out.println(product);
		
		map.put("product",product);
		map.put("menu", menu);
		
		
		return map;
		}
			
	
	@RequestMapping(value = "/json/listProduct" , method = RequestMethod.POST)
	public Map listProduct(@RequestBody Search search, HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct :  POST");
		int currentPage = 1;
		if(search.getCurrentPage() != 0) 
			currentPage = search.getCurrentPage();
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(search.getSearchCondition());
		search.setSearchKeyword(search.getSearchKeyword());
		
		search.setPageSize(pageSize);
		
		Map<String,Object> map = productService.getProductList(search);
		
		
		//정보를 다 받은후 만들 그래야 리저트페이지 받을수있음
		Page resultPage = new Page(currentPage,((Integer)(map.get("totalCount"))).intValue(),pageUnit, pageSize);
		System.out.println(resultPage);
		
		map.put("resultPage",resultPage);
		map.put("map",map);
		map.put("search",search);
		map.put("listType","Product");
		
		
		
		return map;
	}
	
	@RequestMapping(value = "/json/updateProduct", method = RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) throws Exception{
		
		System.out.println("/product/updateProduct");
			
		int prodNo = product.getProdNo();
		product.setManuDate(product.getManuDate().replaceAll("-", ""));

		productService.updateProduct(product);
		
		
		
		return product;
	}
	
	


	@RestController
	public class FileUploadController {

	    @PostMapping("/upload")
	    public String uploadFile(@RequestParam("file") MultipartFile file) {
	        // 파일 업로드 처리
	        if (!file.isEmpty()) {
	            // 파일 처리 로직
	            return "File uploaded successfully: " + file.getOriginalFilename();
	        } else {
	            return "Failed to upload file.";
	        }
	    }
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
