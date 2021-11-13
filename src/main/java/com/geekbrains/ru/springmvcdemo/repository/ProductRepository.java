package com.geekbrains.ru.springmvcdemo.repository;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import com.geekbrains.ru.springmvcdemo.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long>, JpaRepository<Product, Long> {
    List<Product> findByPriceGreaterThan(int minprice);  // товары дороже min цены
    List<Product> findByPriceLessThan(int maxprice);    // товары дешевле max цены
    List<Product> findByPriceBetween(int min, int max);
    List<Product> findByCategoriesIs(Category category);
}
