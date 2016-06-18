package com.lerniludi.lingvoj.controllers;

import com.lerniludi.lingvoj.exceptions.NotFoundException;
import com.lerniludi.lingvoj.models.Card;
import com.lerniludi.lingvoj.repositories.CardRepository;
import com.lerniludi.lingvoj.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller des cartes associées à un paquet
 */
@RestController
@RequestMapping(value = "/decks/{deckId:\\d+}/cards")
public class CardController {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    /**
     * Retourne la liste des cartes d'un paquet
     *
     * @param deckId (required) l'identifiant du paquet de cartes
     * @return Iterable<Card>
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Card> index(@PathVariable Long deckId) {
        return this.deckRepository.findOne(deckId).getCards();
    }

    /**
     * Retourne les données d'une carte
     *
     * @param cardId (required) l'identifiant de la carte
     * @return Card
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{cardId:\\d+}", method = RequestMethod.GET)
    public Card show(@PathVariable Long cardId) {
        return this.cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("La carte n'a pas été trouvée"));
    }
}
