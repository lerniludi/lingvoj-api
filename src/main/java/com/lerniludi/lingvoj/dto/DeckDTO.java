package com.lerniludi.lingvoj.dto;

import com.lerniludi.lingvoj.model.Deck;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * DTO par défaut des paquets de cartes
 */
@Data
public class DeckDTO {

    private long id;

    private String name;

    private int cardsCount;

    /**
     * Permet de sérialiser un objet Deck en sa DTO
     *
     * @param deck (required) paquet de cartes à sérialiser
     * @return DeckDTO
     */
    public static DeckDTO serialize(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();

        deckDTO.setId(deck.getId());
        deckDTO.setName(deck.getName());
        deckDTO.setCardsCount(deck.getCards().size());

        return deckDTO;
    }

    /**
     * Permet de désérialiser une DTO de Deck en son Deck correspondant
     *
     * @param deckDTO (required) DTO de paquet de cartes à sérialiser
     * @return Deck
     */
    public static Deck deserialize(DeckDTO deckDTO) {
        Deck deck = new Deck();

        deck.setName(deckDTO.getName());

        return deck;
    }

    /**
     * Permet de désérialiser une DTO de Deck en son Deck correspondant
     *
     * @return Deck
     */
    public Deck deserialize() { return DeckDTO.deserialize(this); }
}
