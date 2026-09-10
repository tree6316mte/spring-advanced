package com.gamebasic.runcard.repository;

import com.gamebasic.game.entity.Game;
import com.gamebasic.runcard.dto.DeckCount;
import com.gamebasic.runcard.entity.RunCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RunCardRepository extends JpaRepository<RunCard, Long> {
    List<RunCard> findAllByGameOrderByIdAsc(Game game);

    void deleteAllByGame(Game game);

    // TODO (Lv 11): @Query 작성
    @Query("SELECT new com.gamebasic.runcard.dto.DeckCount(rc1_0.game.id,count(rc1_0.id)) FROM RunCard rc1_0 WHERE rc1_0.game IN :games GROUP BY rc1_0.game.id")
    List<DeckCount> countByGames(@Param("games") List<Game> games);
}
