package com.lerniludi.lingvoj.dto;

import com.lerniludi.lingvoj.model.Deck;
import lombok.Data;

/**
 * DTO par défaut des paquets de cartes
 */
@Data
public class DeckDTO {

    private long id;
    private String name;

    /**
     * Permet de sérialiser un objet deck en sa DTO
     * @param deck (required) paquet de cartes à sérialiser
     * @return DeckDTO
     */
    public static DeckDTO serialize(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setId(deck.getId());
        deckDTO.setName(deck.getName());

        return deckDTO;
    }
}
