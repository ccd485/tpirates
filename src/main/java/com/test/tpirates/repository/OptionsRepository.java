package com.test.tpirates.repository;

import com.test.tpirates.domain.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository extends JpaRepository<Options,Integer> {

}
