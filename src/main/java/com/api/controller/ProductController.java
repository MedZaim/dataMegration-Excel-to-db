package com.api.controller;

import com.api.entity.Product;
import com.api.helper.Helper;
import com.api.repo.ProductRepo;
import com.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;

    @PostMapping("/product/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        if (Helper.checkExcelFormat(file)) {
            productService.save(file);
            model.addAttribute("data", productService.getAllProducts());
            return "productsView" ;
        }
        return "redirect:/feed" ;
    }

    @GetMapping("/products")
    public String getAllProduct(Model model) {
        model.addAttribute("data", productService.getAllProducts());
        return "productsView" ;
    }

    @GetMapping("/feed")
    public String hello() {
        return "uploadFileView";
    }

     @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product)  {
        System.out.println("-------------> "+product);
        productRepo.save(product);
        System.out.println(product);
        return "redirect:/products";

    }
    @GetMapping("/delete/{id}")
    public  String delete(@PathVariable("id") Integer id){
     productRepo.deleteById(id);
     return "redirect:/products";
    }
    @GetMapping("/search")
    public  String search(@RequestParam  String query, Model model){
        model.addAttribute("query", query);
        model.addAttribute("data", productRepo.searchProducts(query));
        return "productsView" ;
    }

}








