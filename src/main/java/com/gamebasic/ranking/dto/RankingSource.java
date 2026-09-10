package com.gamebasic.ranking.dto;

import com.gamebasic.game.entity.BaseEntity;
import com.gamebasic.game.entity.GameStatus;
import com.gamebasic.runcard.dto.RunCardRequest;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RankingSource {
    private Meta meta;
    private List<Record> records;

    @Getter
    public static class Meta {
        private Season season;
        private LocalDateTime generatedAt;
        private int schemaVersion;
        private long totalRecords;

        @Getter
        public static class Season {
            private String id;
            private String name;
            private LocalDateTime startsAt;
            private LocalDateTime endsAt;
        }
    }

    @Getter
    public static class Record { // 제출 기록 목록
        private Long id;
        private String submittedAt;
        private Client client;
        private Player player;
        private Run run;
        private BossFight bossFight;
        private Deck deck;

        @Getter
        public static class Client {
            private String version;
            private String platform;
            private String locale;
        }

        @Getter
        public static class Player {
            private String id;
            private String name;
            private String region;
            private List<String> tags;
        }

        @Getter
        public static class Run {
            private String seed;
            private GameStatus status;
            private int clearedFloor;
            private long durationSeconds;
            private int finalHp;
            private List<Floor> floors;

            @Getter
            public static class Floor {
                private int floor;
                private String enemy;
                private int turns;
                private int hpAfter;
                private List<Reward> rewards;

                @Getter
                public static class Reward {
                    private List<String> offered;
                    private String picked;
                }
            }
        }

        @Getter
        public static class BossFight {
            private List<Phase> phases;
            private String finishingCard;
            private int totalTurns;

            @Getter
            public static class Phase {
                private String phase;
                private int turns;
                private int damageTaken;
            }
        }

        @Getter
        public static class Deck {
            private int size;
            private List<RunCardRequest> cards;
        }
    }
}