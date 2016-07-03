package com.lerniludi.lingvoj.repository;

import com.lerniludi.lingvoj.model.Deck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface du repository lié à la classe Deck
 */
public interface DeckRepository extends CrudRepository<Deck, Long> {

    // Récupération de l'intégralité des paquets de cartes
    Collection<Deck> findAll();

    // Récupération d'un paquet de carte par ID
    Optional<Deck> findById(Long id);

    // Effacement d'un paquet de carte par ID
    @Transactional
    Integer deleteById(Long id);

    // Récupère l'état d'existence d'un paquet de carte, ou non
    boolean exists(Long id);
}
