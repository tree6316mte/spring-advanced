package com.gamebasic.ranking.service;

import com.gamebasic.game.dto.GameDetailResponse;
import com.gamebasic.ranking.client.RankingClient;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingClient rankingClient; // 1단계에서 만든 클라이언트 주입

    @Transactional
    public RankingResponse getRankings(){
        RankingSource source = rankingClient.fetch();


        return new RankingResponse(
                source.getMeta().getSeason().getName(),
                source.getRecords().size(),
                source.getRecords().size(), // TODO : 테스트용 나중에 수정할 것
                null // TODO : 테스트용 나중에 수정할 것
        );
    }
}
