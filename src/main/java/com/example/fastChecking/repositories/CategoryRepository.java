package com.example.fastChecking.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fastChecking.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
