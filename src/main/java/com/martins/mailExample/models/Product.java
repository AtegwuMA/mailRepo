package com.martins.mailExample.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_tbl")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private int quantity;
    private String size;
    private String color;
    private String imageUrl;
    private double price;
}
