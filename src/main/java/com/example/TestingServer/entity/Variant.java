package com.example.TestingServer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "variant_question",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "variant")
    private List<Result> results;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "variant_user",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    public void addResult(Result result) {
        results.add(result);
        result.setVariant(this);
    }

    public void removeResult(Result result) {
        results.remove(result);
        result.setVariant(null);
    }
}
