package com.lerniludi.lingvoj.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entité représentant un paquet de cartes
 */
@Entity
public class Deck {

    @Id
    @GeneratedValue
    @Getter @Setter private Long id;
    @Getter @Setter private String name;

    /**
     * Constructeur
     */
    private Deck() {}

    /**
     * Constructeur
     *
     * @param name (required) nom du paquet
     */
    public Deck(String name) {
        this.name = name;
    }
}
