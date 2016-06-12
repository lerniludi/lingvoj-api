package com.lerniludi.lingvoj.repositories;

import com.lerniludi.lingvoj.models.Card;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface du repository lié à la classe Card
 */
public interface CardRepository extends CrudRepository<Card, Long> {

}
