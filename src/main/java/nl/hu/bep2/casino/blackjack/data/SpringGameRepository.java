package nl.hu.bep2.casino.blackjack.data;

import java.util.List;

import nl.hu.bep2.casino.blackjack.domain.BlackJack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringGameRepository extends JpaRepository<BlackJack, Long> {
    @Query(value = "Select * from games where user_id=:userId order by id desc", nativeQuery = true)
    List<BlackJack> findAllGamesByUserId(@Param("userId") Long userId);

    @Query(value = "Select * from games where user_id=:userId order by id desc limit 1", nativeQuery = true)
    BlackJack findLastGame(@Param("userId") Long userId);
}