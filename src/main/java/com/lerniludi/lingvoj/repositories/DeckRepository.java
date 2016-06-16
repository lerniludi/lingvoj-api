package com.lerniludi.lingvoj.repositories;

import com.lerniludi.lingvoj.models.Deck;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends PagingAndSortingRepository<Deck, Long> {

}
