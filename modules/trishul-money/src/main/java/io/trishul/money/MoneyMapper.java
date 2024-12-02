package io.trishul.money;

import org.joda.money.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.trishul.money.currency.model.Currency;
import io.trishul.money.currency.model.CurrencyMapper;
import io.trishul.money.dto.MoneyDto;

@Mapper
public interface MoneyMapper {
    MoneyMapper INSTANCE = Mappers.getMapper(MoneyMapper.class);

    @Mapping(source = "money.amount", target = "amount")
    @Mapping(source = "money.currencyUnit.code", target = "currency")
    MoneyDto toDto(Money money);

    default Money fromDto(MoneyDto dto) {
        Money money = null;
        if (dto != null) {
            money = Money.parse(String.format("%s %s", dto.getCurrency(), dto.getAmount()));
        }

        return money;
    }

    default Money fromEntity(MoneyEntity entity) {
        Money money = null;
        if (entity != null) {
            String symbol = entity.getCurrency().getCode();
            String amount = entity.getAmount().toString(); // TODO: Difference between toString and toPlainString() ?
            money = Money.parse(String.format("%s %s", symbol, amount));
        }
        return money;
    }

    default MoneyEntity toEntity(Money money) {
        MoneyEntity entity = null;
        if (money != null) {
            Currency curr = CurrencyMapper.INSTANCE.toEntity(money.getCurrencyUnit());
            entity = new MoneyEntity(curr, money.getAmount());
        }
        return entity;
    }
}
