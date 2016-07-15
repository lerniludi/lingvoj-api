package com.lerniludi.lingvoj.controller;

import com.lerniludi.lingvoj.dto.CardDTO;
import com.lerniludi.lingvoj.exception.NotFoundException;
import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.model.Deck;
import com.lerniludi.lingvoj.repository.CardRepository;
import com.lerniludi.lingvoj.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * @param deck (required) Paquet de carte associé aux cartes à afficher
     * @return List<CardDTO>
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<CardDTO> index(@ModelAttribute("deck") Deck deck) {
        return deck.getCards().stream().map(card -> CardDTO.serialize(card)).collect(Collectors.toList());
    }

    /**
     * Retourne les données d'une carte
     *
     * @param deck (required) Paquet de carte associé à la carte à créer
     * @param cardId (required) Identifiant de la carte
     * @return Card
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{cardId:\\d+}", method = RequestMethod.GET)
    public Card show(@ModelAttribute("deck") Deck deck, @PathVariable Long cardId) {
        return this.cardRepository.findByIdAndDeck(cardId, deck)
                .orElseThrow(() -> new NotFoundException(getTranslation("error.notFound",
                        new Object[]{getEntityNameTranslated()})));
    }

    /**
     * Tente de récupérer le deck associé au paramètre deckId de l'URL
     *
     * @param deckId (required) Identifiant du deck à récupérer
     * @return Deck Paquet de cartes correspondant à l'identifiant
     * @throws NotFoundException
     */
    @ModelAttribute("deck")
    private Deck getDeck(@PathVariable Long deckId) {
        return this.deckRepository.findById(deckId)
                .orElseThrow(() -> new NotFoundException(getTranslation("error.notFound",
                        new Object[]{getTranslation("entity.deck")})));
    }
}
