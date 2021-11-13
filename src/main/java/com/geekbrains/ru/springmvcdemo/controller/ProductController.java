package com.geekbrains.ru.springmvcdemo.controller;

import com.geekbrains.ru.springmvcdemo.domain.Product;
import com.geekbrains.ru.springmvcdemo.service.CategoryService;
import com.geekbrains.ru.springmvcdemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final Validator validator;

    @GetMapping("/list")
    //http://localhost:8080/product?page_num=1&size=20
    //http://localhost:8080/product?size=20
    public ModelAndView getProducts(@RequestParam(required = false, defaultValue = "") String category) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        if (category.isEmpty()) {
            modelAndView.addObject("products", productService.getProducts());
        } else {
            modelAndView.addObject("products", productService.CategoryIs(categoryService.findByAlias(category)));
        }
        modelAndView.addObject("categoryTree", categoryService.getCategoryTree());
        return modelAndView;
    }

    @GetMapping("/form")
    public String getProductForm(@RequestParam(required = false) Long id, Model model,
                                 @ModelAttribute(value = "violations") String violations) {

        Product product = new Product();
        if (id != null) {
            product = productService.findById(id);
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());

        return "product/form";
    }

    @PostMapping
    public RedirectView saveProduct(@ModelAttribute Product product,
                                    @RequestParam(required = false) MultipartFile image,
                                    RedirectAttributes attributes) {

        Set<ConstraintViolation<Product>> validationResult = validator.validate(product);
        if (!validationResult.isEmpty()) {
            String violations = validationResult.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            attributes.addFlashAttribute("violations", violations);

            return new RedirectView("/product/form");
        }

        productService.saveProductWithImage(product, image);
        return new RedirectView("/product/list");
    }


    @GetMapping("/{productId}")
    //http://localhost:8080/product/1
    //http://localhost:8080/product/2
    public ModelAndView getProduct(@PathVariable Long productId) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", productService.findById(productId));
        return modelAndView;
    }

    @GetMapping("/delete/{productId}")
    public ModelAndView delProduct(@PathVariable Long productId) {
        productService.del(productId);
        return getProducts("");
    }

    // сортировка от меньшей цены к большей
    @GetMapping("/minmax")
    public ModelAndView minSort() {
        ModelAndView modelAndView = new ModelAndView("product/productListMy");
        modelAndView.addObject("products", productService.minSort());
        return modelAndView;
    }

    @GetMapping("/query")
    // http://localhost:8080/product/query?min=
    // http://localhost:8080/product/query?max=
    // http://localhost:8080/product/query?min= &max=
    public ModelAndView Query(@RequestParam(required = false, defaultValue = "-1") int min, @RequestParam(required = false, defaultValue = "-1") int max) {
        ModelAndView modelAndView = new ModelAndView("product/productListMy");
        if (min != -1) {
            modelAndView.addObject("products", productService.GreaterThan(min));
        }
        if (max != -1) {
            modelAndView.addObject("products", productService.LessThan(max));
        }
        if (min != -1 && max != -1) {
            modelAndView.addObject("products", productService.Between(min, max));
        }
        return modelAndView;
    }

    // REST controller - в отдельный класс не стал выносить

    @RestController
    @RequestMapping("/api/v1/product/")

    public class RestProductController {

        private ProductService productService;

        @Autowired
        public void setProductService(ProductService productService) {
            this.productService = productService;
        }

        @GetMapping
        public List<Product> getAllProducts() {
            return productService.getProducts();
        }

        @GetMapping("/{id}")
        public Product getProductById(@PathVariable Long id) {
            return productService.findById(id);
        }

        @DeleteMapping("/{id}")
        public int deleteProduct(@PathVariable Long id) {
            productService.del(id);
            return HttpStatus.OK.value();
        }

        @PostMapping
        public void addProduct(@RequestBody Product product) {
            productService.saveProductWithImage(product, null);
        }
    }

}
