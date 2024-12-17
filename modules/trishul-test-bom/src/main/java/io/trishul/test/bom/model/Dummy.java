package io.trishul.test.bom.model;

public class Dummy {
    private String string;
    private Long longg;
    private Double doublee;
    private Integer integer;
    private Character character;
    private Boolean bool;

    public Dummy() {
    }

    public Dummy(String string, Long longg, Double doublee, Integer integer, Character character, Boolean bool) {
        setString(string);
        setLongg(longg);
        setDoublee(doublee);
        setInteger(integer);
        setCharacter(character);
        setBool(bool);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Long getLongg() {
        return longg;
    }

    public void setLongg(Long longg) {
        this.longg = longg;
    }

    public Double getDoublee() {
        return doublee;
    }

    public void setDoublee(Double doublee) {
        this.doublee = doublee;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }
}