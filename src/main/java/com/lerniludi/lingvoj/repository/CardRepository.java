package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.model.Deck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface du repository lié à la classe Card
 */
public interface CardRepository extends CrudRepository<Card, Long> {

    // Récupération d'une carte par ID et deck associé
    Optional<Card> findByIdAndDeck(Long id, Deck deck);
}
