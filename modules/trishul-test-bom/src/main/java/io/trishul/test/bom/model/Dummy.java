package io.trishul.test.bom.model;

public class Dummy {
  private String string;
  private Long longg;
  private Double doublee;
  private Integer integer;
  private Character character;
  private Boolean bool;

  public Dummy() {}

  public Dummy(String string, Long longg, Double doublee, Integer integer, Character character,
      Boolean bool) {
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

  public final Dummy setString(String string) {
    this.string = string;
    return this;
  }

  public Long getLongg() {
    return longg;
  }

  public final Dummy setLongg(Long longg) {
    this.longg = longg;
    return this;
  }

  public Double getDoublee() {
    return doublee;
  }

  public final Dummy setDoublee(Double doublee) {
    this.doublee = doublee;
    return this;
  }

  public Integer getInteger() {
    return integer;
  }

  public final Dummy setInteger(Integer integer) {
    this.integer = integer;
    return this;
  }

  public Character getCharacter() {
    return character;
  }

  public final Dummy setCharacter(Character character) {
    this.character = character;
    return this;
  }

  public Boolean getBool() {
    return bool;
  }

  public final Dummy setBool(Boolean bool) {
    this.bool = bool;
    return this;
  }
}
