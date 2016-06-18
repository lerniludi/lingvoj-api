package com.lerniludi.lingvoj.repositories;

import com.lerniludi.lingvoj.models.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface du repository lié à la classe Card
 */
public interface CardRepository extends CrudRepository<Card, Long> {

    Optional<Card> findById(Long id);
}
