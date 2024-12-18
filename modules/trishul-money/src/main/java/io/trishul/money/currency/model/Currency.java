package io.trishul.money.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.trishul.model.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "currency")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Currency extends BaseEntity implements UpdateCurrency {
    public static final String FIELD_NUMERIC_CODE = "numericCode";
    public static final String FIELD_CODE = "code";

    @Id
    @Column(name = "numeric_code")
    private Integer numericCode;

    @Column(name = "code", nullable = false, length = 3)
    private String code;

    public Currency() {
        this(null, null);
    }

    public Currency(Integer numericCode, String code) {
        setNumericCode(numericCode);
        setCode(code);
    }

    @Override
    public Integer getNumericCode() {
        return numericCode;
    }

    @Override
    public final void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public final void setCode(String code) {
        this.code = code;
    }
}
