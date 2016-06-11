package com.lerniludi.lingvoj.repositories;

import com.lerniludi.lingvoj.models.Deck;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends CrudRepository<Deck, Long> {

}
