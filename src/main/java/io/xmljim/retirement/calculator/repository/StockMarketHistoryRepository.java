package io.xmljim.retirement.calculator.repository;

import io.xmljim.retirement.calculator.entity.stocks.MarketHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StockMarketHistoryRepository extends CrudRepository<MarketHistory, Long> {
    @Query("select distinct m.market from MarketHistory m")
    List<String> findDistinctMarkets();


    @Query("select m from MarketHistory m where m.market = :market order by m.year")
    List<MarketHistory> findByMarket(@Param("market") String market);

    @Query("select m from MarketHistory m where m.year = :year and m.market = :market")
    Optional<MarketHistory> findByYearAndMarket(@Param("year") Integer year, @Param("market") String market);

    @Query("select m from MarketHistory m where m.year = :year")
    List<MarketHistory> findByYear(@Param("year") Integer year);

    @Query("select m from MarketHistory m where m.year >= :year")
    List<MarketHistory> findByYearGreaterThanEqual(@Param("year") Integer year);

    @Query("select m from MarketHistory m where m.market = :market and year >= :year")
    List<MarketHistory> findByMarketAndYearGreaterThanEquals(@Param("year") Integer year, @Param("market") String market);

    @Query("select (count(m) > 0) from MarketHistory m where m.market = :market and m.year = :year")
    boolean marketYearExists(@Param("market") String market, @Param("year") Integer year);




}
