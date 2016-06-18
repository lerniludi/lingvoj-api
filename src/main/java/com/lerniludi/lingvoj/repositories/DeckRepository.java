package com.lerniludi.lingvoj.repositories;

import com.lerniludi.lingvoj.models.Deck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends CrudRepository<Deck, Long> {

    Iterable<Deck> findAll();
    Optional<Deck> findById(Long id);
}
