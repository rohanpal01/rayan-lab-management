package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.InventoryItem;
import com.example.rayanlabmanagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepo;

    @PostMapping
    public InventoryItem addItem(@RequestBody InventoryItem item) {
        return inventoryRepo.save(item);
    }

    @GetMapping
    public List<InventoryItem> getAllItems() {
        return inventoryRepo.findAll();
    }

    @PutMapping("/{id}")
    public InventoryItem updateItem(@PathVariable Long id, @RequestBody InventoryItem updated) {
        InventoryItem item = inventoryRepo.findById(id).orElseThrow();
        item.setItemName(updated.getItemName());
        item.setCategory(updated.getCategory());
        item.setQuantity(updated.getQuantity());
        item.setThreshold(updated.getThreshold());
        return inventoryRepo.save(item);
    }

    @GetMapping("/low-stock")
    public List<InventoryItem> getLowStockItems() {
        return inventoryRepo.findAll().stream()
                .filter(i -> i.getQuantity() < i.getThreshold())
                .toList();
    }
}
