package com.geekbrains.ru.springmvcdemo.service.impl;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import com.geekbrains.ru.springmvcdemo.domain.Product;
import com.geekbrains.ru.springmvcdemo.repository.ProductRepository;
import com.geekbrains.ru.springmvcdemo.service.ProductService;
import com.geekbrains.ru.springmvcdemo.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    //private final ProductDao productDao;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void saveProductWithImage(Product product, MultipartFile image) {
        // product save...
        productRepository.save(product);
        if (image != null && !image.isEmpty()) {
            Path pathImage = FileUtils.saveProductImage(image);
            product.setImageLink(pathImage.toString());
            // update
        }
        // return product
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void del(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> minSort() {
        return productRepository.findAll(Sort.by("price"));
    }

    @Override
    public List<Product> GreaterThan(int min) {
        return productRepository.findByPriceGreaterThan(min);
    }

    @Override
    public List<Product> LessThan(int max) {
        return productRepository.findByPriceLessThan(max);
    }

    @Override
    public List<Product> Between(int min, int max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> CategoryIs(Category category) {
        return productRepository.findByCategoriesIs(category);
    }

}
