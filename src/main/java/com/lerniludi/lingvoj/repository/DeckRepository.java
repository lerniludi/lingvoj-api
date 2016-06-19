package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Deck;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends PagingAndSortingRepository<Deck, Long> {

    Collection<Deck> findAll();
    Optional<Deck> findById(Long id);
}
