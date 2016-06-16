package com.lerniludi.lingvoj.services;

import com.lerniludi.lingvoj.models.Deck;
import com.lerniludi.lingvoj.repositories.DeckRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service associé aux paquets de cartes
 */
@Service
public class DeckService {

    @Autowired
    protected DeckRepository deckRepository;

    /**
     * Retourne la liste des paquets de cartes
     *
     * @return List<Deck>
     */
    public List<Deck> getDecksList() {
        PageRequest pageReq = new PageRequest(0, 100, Sort.Direction.fromString("ASC"), "id");
        Page<Deck> decks = deckRepository.findAll(pageReq);
        return decks.getContent();
    }

}
