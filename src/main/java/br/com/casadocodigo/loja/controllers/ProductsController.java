package br.com.casadocodigo.loja.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private FileSaver fileSaver;
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView form(Product product){
		ModelAndView modelAndView = new ModelAndView("/products/form");
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes attr ){
		if (bindingResult.hasErrors()) {
			return form(product);	
		}
		String webPath = fileSaver.write("uploaded-summaries", summary);
		product.setSummaryPath(webPath);
		productDAO.save(product);
		attr.addAttribute("SUCCESS", "Item criado com sucesso!");
		System.out.println("Cadastrando o produto: " + product);
		return new ModelAndView("redirect:products");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView view = new ModelAndView("/products/list");
		view.addObject("products", productDAO.list());
		return view;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ModelAndView show(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("products/show");
		modelAndView.addObject("product", productDAO.find(id));
		return modelAndView;
	}
	
	//@InitBinder
//	public void initBinder(WebDataBinder binder){
//		binder.addValidators(new ProductValidator());
//	}
}
