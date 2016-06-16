package com.lerniludi.lingvoj.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Entité représentant une carte
 */
@Entity
@Data
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Deck deck;

    private String front;

    private String back;

    /**
     * Constructeur
     */
    private Card() { }

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
