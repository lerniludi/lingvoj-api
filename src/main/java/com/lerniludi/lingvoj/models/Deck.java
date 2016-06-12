package com.lerniludi.lingvoj.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Entité représentant un paquet de cartes
 */
@Entity
public class Deck {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @OneToMany(mappedBy="deck")
    @Getter @Setter
    private List<Card> cards;

    /**
     * Constructeur
     */
    private Deck() {
    }

    /**
     * Constructeur
     *
     * @param name (required) nom du paquet
     */
    public Deck(String name) {
        this.name = name;
    }

    public Deck(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }
}
