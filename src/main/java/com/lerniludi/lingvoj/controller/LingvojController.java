package com.lerniludi.lingvoj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Controller de bases de l'application
 */
public class LingvojController {

    @Autowired
    private MessageSource messageSource;

    /**
     * Permet de récupérer une traduction à partir d'une clé
     *
     * @param key la clé de traduction
     * @return la traduction
     */
    protected String getTranslation(String key) {
        return getTranslation(key, null);
    }

    /**
     * Permet de récupérer une traduction à partr d'une clé et d'arguments
     *
     * @param key la clé de traduction
     * @param args les arguments à passer
     * @return la traduction
     */
    protected String getTranslation(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    /**
     * Permet de récupérer la traduction d'une entité Controller
     * Par exemple, DeckController récupérera la traduction liée à la clé entity.deck
     *
     * @return la traduction de l'entité
     */
    protected String getEntityNameTranslated() {
        return getTranslation("entity." + getClass().getSimpleName().replaceFirst("Controller", "").toLowerCase());
    }
}
