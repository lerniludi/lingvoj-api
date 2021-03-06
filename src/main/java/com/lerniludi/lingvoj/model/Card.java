package com.lerniludi.lingvoj.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

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
    @JsonIgnore
    private Deck deck;

    private String front;

    private String back;

    /**
     * Constructeur
     */
    public Card() { }

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
