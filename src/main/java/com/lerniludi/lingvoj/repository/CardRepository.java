package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface du repository lié à la classe Card
 */
public interface CardRepository extends CrudRepository<Card, Long> {

    Optional<Card> findById(Long id);
}
