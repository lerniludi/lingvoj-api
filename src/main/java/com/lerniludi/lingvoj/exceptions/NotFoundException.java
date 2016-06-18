package com.lerniludi.lingvoj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe gérant l'exception NotFound
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Entité non trouvée";

    /**
     * Constructeur par défaut avec le message définie dans DEFAULT_MESSAGE
     *
     */
    public NotFoundException() {
        super(NotFoundException.DEFAULT_MESSAGE);
    }

    /**
     * Constructeur
     *
     * @param message (required) message envoyé par l'exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
