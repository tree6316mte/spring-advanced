package com.gamebasic.ranking.service;

import com.gamebasic.game.dto.GameDetailResponse;
import com.gamebasic.game.entity.GameStatus;
import com.gamebasic.ranking.client.RankingClient;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import com.gamebasic.runcard.entity.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingClient rankingClient;

    @Transactional
    public RankingResponse getRankings(){
        RankingSource source = rankingClient.fetch();

        // 카드 타입 목록
        List<String> validTypes = Arrays.stream(CardType.values()).map(Enum::name).toList();

        // 정상 클리어 조건 처리
        List<RankingSource.Record> targetRecords = source.getRecords().stream().filter( record -> {
            if(record.getRun().getStatus() != GameStatus.CLEARED) return false;
            if(record.getRun().getClearedFloor() != 10) return false;
            return true;
        }).toList();

        List<RankingSource.Record> validRecords = targetRecords.stream().filter( record -> {
            if(!(record.getRun().getDurationSeconds() >= record.getRun().getClearedFloor() * 30)) return false;
            if(!(record.getRun().getFinalHp() >= 1 && record.getRun().getFinalHp() <= 99)) return false;
            if(!(record.getDeck().getCards().size() >= 9 && record.getDeck().getCards().size() <= 20 && record.getDeck().getCards().size() == record.getDeck().getSize())) return false;
            if(!record.getDeck().getCards().stream().allMatch(card -> validTypes.contains(card.getCardType()))) return false;
            if(!record.getDeck().getCards().stream().allMatch(card -> card.getAcquiredFloor() >= 0 && card.getAcquiredFloor() <= 9 )) return false;

            List<RankingSource.Record.BossFight.Phase> phases = record.getBossFight().getPhases();
            if (phases == null || phases.size() != 3) return false;
            if (!phases.get(0).getPhase().equals("THRONE")) return false;
            if (!phases.get(1).getPhase().equals("UNBOUND")) return false;
            if (!phases.get(2).getPhase().equals("ECLIPSE")) return false;

            int sumOfTurns = 0;
            for (RankingSource.Record.BossFight.Phase phase : phases) {
                if (!(phase.getTurns() >= 1)) return false;
                sumOfTurns += phase.getTurns();
            }
            if(record.getBossFight().getTotalTurns() != sumOfTurns) return false;
            if(!record.getDeck().getCards().stream().anyMatch(card -> card.getCardType().equals(record.getBossFight().getFinishingCard()))) return false;
            return true;
        }).sorted((left, right)->{
            if(left.getRun().getDurationSeconds() != right.getRun().getDurationSeconds()) return left.getRun().getDurationSeconds() < right.getRun().getDurationSeconds() ? -1 : 1;
            if(left.getRun().getFinalHp() != right.getRun().getFinalHp()) return left.getRun().getFinalHp() > right.getRun().getFinalHp() ? -1 : 1;
            if(!left.getId().equals(right.getId())) return left.getId() < right.getId() ? -1 : 1;
            return 0;
        }).toList();


        List<RankingResponse.Entry> entries = new ArrayList<>();
        Set<String> rankerIds = new HashSet<>();
        int rank = 1;
        for (var validRecord : validRecords) {
            String playerId = validRecord.getPlayer().getId();

            if (rankerIds.contains(playerId)) continue;
            entries.add(new RankingResponse.Entry(
                    rank++,
                    validRecord.getPlayer().getName(),
                    (int) validRecord.getRun().getDurationSeconds(),
                    validRecord.getRun().getFinalHp(),
                    validRecord.getBossFight().getTotalTurns(),
                    validRecord.getDeck().getSize()
            ));

            rankerIds.add(playerId);
        }

        return new RankingResponse(
                source.getMeta().getSeason().getId(),
                source.getMeta().getTotalRecords(),
                targetRecords.size() - validRecords.size(),
                entries
        );
    }
}
