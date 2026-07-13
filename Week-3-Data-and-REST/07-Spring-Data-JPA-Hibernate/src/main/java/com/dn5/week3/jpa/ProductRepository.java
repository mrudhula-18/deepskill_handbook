package com.dn5.week3.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** Derived query: case-insensitive match on the related category name. */
    List<Product> findByCategory_NameIgnoreCase(String name);

    /** Derived query: all products strictly more expensive than the given price. */
    List<Product> findByPriceGreaterThan(BigDecimal price);

    /** JPQL query using a LIKE search over the product name. */
    @Query("select p from Product p where p.name like %:kw%")
    List<Product> search(@Param("kw") String kw);

    /** Paginated / sortable lookup by category name. */
    Page<Product> findByCategory_Name(String name, Pageable pageable);

    /**
     * Interface-based projection variant of the category-name lookup.
     * Named distinctly ("findProjectedBy...") to avoid overload ambiguity
     * with {@link #findByCategory_Name(String, Pageable)}.
     */
    List<ProductNameOnly> findProjectedByCategory_Name(String name);
}
