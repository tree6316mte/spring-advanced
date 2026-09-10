package com.gamebasic.ranking.dto;

import com.gamebasic.game.entity.GamePhase;
import com.gamebasic.game.entity.GameStatus;
import com.gamebasic.runcard.dto.CardResponse;
import com.gamebasic.runcard.dto.RunCardRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class RankingResponse {
    public final String season;
    public final int totalRecords;
    public final int excludedCount;
    public final List<Entry> entries;

    public RankingResponse(
            String season,
            int totalRecords,
            int excludedCount,
            List<Entry>entries
    ) {
        this.season = season;
        this.totalRecords = totalRecords;
        this.excludedCount = excludedCount;
        this.entries = entries;
    }

    public static class Entry {
        private int rank;
        private String playerName;
        private int clearTimeSeconds;
        private int remainingHp;
        private int bossTurns;
        private int deckSize;

    }
}
