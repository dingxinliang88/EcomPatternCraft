package com.youyi.ecom.repo;

import com.youyi.ecom.pojo.po.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    @Modifying
    @Query(value = "INSERT INTO tb_product_item(id, name, pid) VALUES ((SELECT MAX(id) + 1 FROM tb_product_item), ?1, ?2)",
            nativeQuery = true)
    void addItem(String name, long pid);

    @Modifying
    @Query(value = "DELETE FROM tb_product_item WHERE id = ?1 OR pid = ?1", nativeQuery = true)
    void delItem(long id);

    ProductItem findByNameAndPid(String name, long pid);
}
