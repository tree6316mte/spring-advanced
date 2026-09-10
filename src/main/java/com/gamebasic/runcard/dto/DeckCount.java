package com.gamebasic.runcard.dto;

import lombok.Getter;

@Getter
public class DeckCount {
    private final Long gameId;
    private final long count; // COUNT()는 long 타입을 반환합니다.
    public DeckCount(Long gameId, long count) {
        this.gameId = gameId;
        this.count = count;
    }
}