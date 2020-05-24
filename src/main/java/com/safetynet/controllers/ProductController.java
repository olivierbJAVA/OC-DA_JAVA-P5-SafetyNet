package com.safetynet.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.util.JsonFileInputReaderImpl;



@RestController
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	
	public ProductController() {
		super();
		
		//JsonInputFileReader.read();
		logger.info("ProductController info");
		logger.debug("ProductController debug");
	}

	/*
	
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value="/Produits", method=RequestMethod.GET)
	public List<Product> listeProduits () {
		logger.info("ProductController lp info");
		logger.debug("ProductController lp debug");
		return productDao.findAll();
	}

	@GetMapping(value="/Produits/{id}")
	public Product afficherUnProduit (@PathVariable int id) {
		return productDao.findById(id);
	}
	
	//ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product) {
         
    	Product productAdded = productDao.save(product);
    	
    	if (productAdded == null)
    		//construit le header à retourner
    		return ResponseEntity.noContent().build();
    
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productAdded.getId()).toUri();
    
    	return ResponseEntity.created(location).build();
    }
	
	*/
}
