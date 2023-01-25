package com.example.hw_27_hibernate_2.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int count;

    @ManyToOne
    @JoinColumn(name = "fk_product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fk_order_id", nullable = false)
    private Order order;
}
