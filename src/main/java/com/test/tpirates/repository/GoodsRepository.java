package com.test.tpirates.repository;

import com.test.tpirates.domain.Delivery;
import com.test.tpirates.domain.Goods;
import com.test.tpirates.mappingInterface.GoodsMapping;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods,Integer> {
    @Query("select g.name as name, g.description as description, min(o.price) as price from Options o join Goods g on g.id = o.goods Group By g.id order by g.regDate")
    List<GoodsMapping> findGoods();

}
