package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByQuantityLessThan(int threshold);
}
