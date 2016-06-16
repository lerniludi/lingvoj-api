package com.lerniludi.lingvoj.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO par défaut des paquets de cartes
 */
public class DeckDto {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;
}
