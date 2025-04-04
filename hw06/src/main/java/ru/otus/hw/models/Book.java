package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
    public class Book {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "title",nullable = false)
        private String title;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id", nullable = false)
        private Author author;

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "genre_id", nullable = false)
        private Genre genre;

        @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        private List<Comment> comments = new ArrayList<>();

    }

