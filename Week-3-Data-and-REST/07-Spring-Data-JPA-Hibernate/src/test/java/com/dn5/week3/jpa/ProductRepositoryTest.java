package com.dn5.week3.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Slice test for {@link ProductRepository}. Test rows are inserted directly
 * via {@link org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager}
 * rather than relying on {@code data.sql}, so {@code spring.sql.init.mode} is
 * disabled here for full isolation.
 */
@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class ProductRepositoryTest {

    @Autowired
    private org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Category electronics;
    private Category books;

    @BeforeEach
    void setUp() {
        electronics = new Category("Electronics");
        books = new Category("Books");
        entityManager.persistAndFlush(electronics);
        entityManager.persistAndFlush(books);

        entityManager.persistAndFlush(new Product("Laptop", new BigDecimal("999.99"), electronics));
        entityManager.persistAndFlush(new Product("Headphones", new BigDecimal("49.99"), electronics));
        entityManager.persistAndFlush(new Product("Novel", new BigDecimal("12.50"), books));
    }

    @Test
    void findByCategory_NameIgnoreCase_returnsMatchingProducts() {
        List<Product> results = productRepository.findByCategory_NameIgnoreCase("ELECTRONICS");

        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getName)
                .containsExactlyInAnyOrder("Laptop", "Headphones");
    }

    @Test
    void findByPriceGreaterThan_returnsOnlyExpensiveProducts() {
        List<Product> results = productRepository.findByPriceGreaterThan(new BigDecimal("50.00"));

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Laptop");
    }

    @Test
    void search_matchesProductsByPartialName() {
        List<Product> results = productRepository.search("phone");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Headphones");
    }

    @Test
    void findByCategory_Name_returnsPagedAndSortedResults() {
        Page<Product> page = productRepository.findByCategory_Name(
                "Electronics", PageRequest.of(0, 1, Sort.by("price").descending()));

        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Laptop");
    }

    @Test
    void findProjectedByCategory_Name_returnsOnlyNameAndPrice() {
        List<ProductNameOnly> projections = productRepository.findProjectedByCategory_Name("Books");

        assertThat(projections).hasSize(1);
        assertThat(projections.get(0).getName()).isEqualTo("Novel");
        assertThat(projections.get(0).getPrice()).isEqualByComparingTo("12.50");
    }
}
