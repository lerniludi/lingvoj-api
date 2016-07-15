package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Card;
import com.lerniludi.lingvoj.model.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface du repository lié à la classe Card
 */
public interface CardRepository extends CrudRepository<Card, Long> {

    // Récupération d'une carte par ID et deck associé
    Optional<Card> findByIdAndDeck(Long id, Deck deck);

    // Effacement d'un paquet de carte par ID et Deck associé
    @Transactional
    Integer deleteByIdAndDeck(Long id, Deck deck);

    // Récupère le nombre de cartes associées par ID et par Deck
    // Une méthode dérivée du type existsByIdAndDeck n'est pas possible, c'est pourquoi count est utilisé
    Long countByIdAndDeck(Long id, Deck deck);
}
