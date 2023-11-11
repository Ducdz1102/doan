/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Controller;

import com.example.Entity.Category;
import com.example.Entity.Product;
import com.example.Service.CategoryServiceImpl;
import com.example.Service.ProductServiceImpl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nguye
 */
@Controller
public class AdminController {

    @Autowired
    ProductServiceImpl prod;
    @Autowired
    CategoryServiceImpl cate;

    @GetMapping("/admin/category")
    public String view11(Model model,
            @RequestParam(name = "page",defaultValue ="0") Integer page,
            @RequestParam(name = "page_size",defaultValue ="1") Integer page_size,
            @RequestParam(name = "keyword",defaultValue ="",required = false) String keyword 
    ) {
        Page<Category> listProducts = cate.findAll(keyword,page,page_size);
        model.addAttribute("listCategory", listProducts);
        return "/admin/category";
    }

    @GetMapping("/admin/products")
    public String view1(Model model,
            @RequestParam(name = "page",defaultValue ="0") Integer page,
            @RequestParam(name = "page_size",defaultValue ="1") Integer page_size,
            @RequestParam(name = "keyword",defaultValue ="",required = false) String keyword      
    ) {

        Page<Product> listProducts = prod.findall(page,page_size,keyword);
        model.addAttribute("listProducts", listProducts);

        return "/admin/products";
    }

    //Category
    @GetMapping("/category/add")
    public String addcate(Model model) {
        Category cate = new Category();
        model.addAttribute("Categ", cate);

        return "/admin/add_category";
    }

    @PostMapping("/category/savee")
    public String addc1(Model model, Category Categ, RedirectAttributes redirectAttributes) {
        // Lưu Category và kiểm tra xem việc lưu đã thành công chưa
        Category savedCategory = cate.save(Categ);

//        if (savedCategory != null) {
//            redirectAttributes.addFlashAttribute("msg", "Thêm danh mục mới thành công");
//        } else {
//            redirectAttributes.addFlashAttribute("msg", "Thêm danh mục mới không thành công");
//        }

        return "redirect:/category/add";
    }

    @GetMapping("/category/del/{cid}")
    public String delc(@PathVariable("cid") Long cid) {
        cate.deleteById(cid);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/edit_category/{id}")
    public String viewcate(@PathVariable("id") Long id, Model model) {

        Category a = cate.findById(id);

        model.addAttribute("cat", a);
        return "/admin/edit_category";
    }

    @PostMapping("/admin/edit_category/{id}")
    public String editc(@PathVariable("id") Long id, @ModelAttribute("cat") Category cat) {
        Category a = cate.findById(id);

//        b.setCid(id);
        a.setCname(cat.getCname());
        cate.save(a);
        return "redirect:/admin/category";
    }

    // Product  /////////////////////////////////////////////////////////////////
    @GetMapping("/admin/edit_product/{id}")
    public String viewproduct1(@PathVariable("id") Long id, Model model) {
        Product a = prod.findById(id).get();
        model.addAttribute("product", a);
        List<Category> cat = cate.getAllCategories();
        model.addAttribute("cate", cat);
        return "/admin/edit_product";
    }

    @PostMapping("/admin/edit_product/{id}")
    public String editproduct1(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        Product a = prod.findById(id).get();
        a.setPid(id);
        a.setPname(product.getPname());
        a.setPrice(product.getPrice());
        a.setPimage(product.getPimage());
        a.setCateid(product.getCateid());
        a.setDescription(product.getDescription());
        a.setPdate(new Date());
        prod.save(a);

        return "redirect:/admin/products";
    }

    @GetMapping("/product/add")
    public String addpro(Model model) {
        Product pro = new Product();
        model.addAttribute("Pro", pro);
        List<Category> cat = cate.getAllCategories();
        model.addAttribute("cate", cat);
        model.addAttribute("msg", "Them moi Thanh cong!");
        return "/admin/add_product";
    }

    @PostMapping("/product/save")
    public String addpro1(Model model, Product pro) {
        pro.setPdate(new Date());

        model.addAttribute("Pro", prod.save(pro));
        return "redirect:/admin/products";
    }

    @GetMapping("/product/del/{id}")
    public String delp(@PathVariable("id") Long id) {
        prod.deleteById(id);
        return "redirect:/admin/products";
    }

}
