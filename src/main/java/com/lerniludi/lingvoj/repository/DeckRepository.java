package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Deck;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends CrudRepository<Deck, Long> {

    Collection<Deck> findAll();
    Optional<Deck> findById(Long id);
    boolean exists(Long id);
}
