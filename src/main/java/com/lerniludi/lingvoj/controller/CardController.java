package com.lerniludi.lingvoj.controller;

import com.lerniludi.lingvoj.exception.NotFoundException;
import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.repository.CardRepository;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller des cartes associées à un paquet
 */
@RestController
@RequestMapping(value = "/decks/{deckId:\\d+}/cards")
public class CardController extends LingvojController {

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
                .orElseThrow(() -> new NotFoundException(getTranslation("error.notFound", new Object[]{getEntityNameTranslated()})));
    }
}
