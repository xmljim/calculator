package io.xmljim.retirement.calculator.entity.stocks;

import io.xmljim.retirement.calculator.entity.ServiceResult;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "CPI", indexes = {
        @Index(name = "idx_cpi_year", columnList = "year")
})
@Entity
@Getter
@Setter
public class CPI implements ServiceResult {
    @Column(name="year", nullable =false)
    private int year;

    @Column(name = "cpiRate", nullable = false)
    private double cpiRate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

}
