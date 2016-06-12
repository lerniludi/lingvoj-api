package com.lerniludi.lingvoj.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entité représentant une carte
 */
@Entity
public class Card {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @ManyToOne
    @Getter @Setter
    private Deck deck;

    @Getter @Setter
    private String front;

    @Getter @Setter
    private String back;

    /**
     * Constructeur
     */
    private Card() {}

    /**
     * Constructeur
     *
     * @param deck  (required) paquet auquel est rattachée la carte
     * @param front (required) côté face de la carte
     * @param back  (required) côté retournée de la carte
     */
    public Card(Deck deck, String front, String back) {
        this.deck = deck;
        this.front = front;
        this.back = back;
    }
}
