package com.lerniludi.lingvoj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe gérant l'exception NotFound
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Constructeur
     *
     * @param message (required) message envoyé par l'exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
