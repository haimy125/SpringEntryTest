package com.myph.springentrytest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @PrePersist
    public void prePersist() {
        if (publishedDate == null) {
            publishedDate = LocalDate.now();
        }
    }
}
