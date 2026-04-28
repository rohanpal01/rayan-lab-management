package com.example.rayanlabmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_items")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String itemName;
    private String category;
    private int quantity;
    private int threshold;

    // getters and setters
}
