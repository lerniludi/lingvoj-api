package com.lerniludi.lingvoj.dto;

import com.lerniludi.lingvoj.model.Card;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO par défaut correspondant à une carte
 */
@Data
public class CardDTO {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String front;

    @NotNull
    @Size(min = 1)
    private String back;


    /**
     * Permet de sérialiser un objet Card en sa DTO
     *
     * @param card (required) carte à sérialiser
     * @return CardDTO
     */
    public static CardDTO serialize(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setId(card.getId());
        cardDTO.setFront(card.getFront());
        cardDTO.setBack(card.getBack());

        return cardDTO;
    }

    /**
     * Permet de désérialiser une DTO de Card en son objet Card correspondant
     *
     * @return Card
     */
    public Card deserialize() {
        Card card = new Card();

        card.setFront(this.front);
        card.setBack(this.back);

        return card;
    }
}
