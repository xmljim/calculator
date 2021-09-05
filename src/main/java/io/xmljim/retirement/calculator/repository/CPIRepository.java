package io.xmljim.retirement.calculator.repository;

import io.xmljim.retirement.calculator.entity.stocks.CPI;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CPIRepository extends CrudRepository<CPI, Long> {
    Optional<CPI> findByYear(int year);

    @Query("select c from CPI c where c.year between :yearStart and :yearEnd")
    List<CPI> findByYearIsBetween(@Param("yearStart") int yearStart, @Param("yearEnd") int yearEnd);



}
