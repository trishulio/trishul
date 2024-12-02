package io.trishul.quantity.unit;

import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import tec.uom.se.function.RationalConverter;
import tec.uom.se.quantity.QuantityDimension;
import tec.uom.se.unit.BaseUnit;
import tec.uom.se.unit.TransformedUnit;
import tec.uom.se.unit.Units;

public class SupportedUnits {
    //Any new units must also be added to the QTY_UNIT table
    public static final Unit<AmountOfSubstance> EACH = new BaseUnit<AmountOfSubstance>("each", QuantityDimension.AMOUNT_OF_SUBSTANCE);
    public static final Unit<Volume> LITRE = new TransformedUnit<Volume>("l", Units.CUBIC_METRE, Units.CUBIC_METRE.getSystemUnit(), new RationalConverter(1, 1000));
    public static final Unit<Volume> MILLILITRE = new TransformedUnit<Volume>("ml", SupportedUnits.LITRE, SupportedUnits.LITRE.getSystemUnit(), RationalConverter.of(1, 1000));
    public static final Unit<Volume> HECTOLITRE = new TransformedUnit<Volume>("hl", SupportedUnits.LITRE, SupportedUnits.LITRE.getSystemUnit(), RationalConverter.of(100, 1));
    public static final Unit<Mass> KILOGRAM = new BaseUnit<Mass>("kg", QuantityDimension.MASS);
    public static final Unit<Mass> GRAM = new TransformedUnit<Mass>("g", SupportedUnits.KILOGRAM, SupportedUnits.KILOGRAM.getSystemUnit(), RationalConverter.of(1, 1000));
    public static final Unit<Mass> MILLIGRAM = new TransformedUnit<Mass>("mg", SupportedUnits.KILOGRAM, SupportedUnits.KILOGRAM.getSystemUnit(), RationalConverter.of(1, 1000000));

    public static final Unit<Mass> DEFAULT_MASS = KILOGRAM;
    public static final Unit<Volume> DEFAULT_VOLUME = LITRE;
}
