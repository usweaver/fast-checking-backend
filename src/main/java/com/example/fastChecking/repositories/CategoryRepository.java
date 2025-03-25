package com.example.fastChecking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
