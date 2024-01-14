package nl.hu.bep2.casino.blackjack.data;

import java.util.List;

import nl.hu.bep2.casino.blackjack.domain.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDeckRepository extends JpaRepository<Deck, Long> {
    @Query(value = "Select * from decks where game_id=:gameId", nativeQuery = true)
    List<Deck> findAllDecksByGameId(@Param("gameId") Long gameId);
}