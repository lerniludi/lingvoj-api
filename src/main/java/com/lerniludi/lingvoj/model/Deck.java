package com.lerniludi.lingvoj.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un paquet de cartes
 */
@Entity
@Data
public class Deck {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 2)
    private String name;

    @OneToMany(mappedBy = "deck")
    @JsonIgnore
    private List<Card> cards;

    /**
     * Constructeur
     */
    public Deck() {
        this.cards = new ArrayList<>();
    }

    /**
     * Constructeur
     *
     * @param name (required) nom du paquet
     */
    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }
}
