package sh.trishul.crud.controller;

import sh.trishul.model.base.exception.EntityNotFoundException;

public class TrishulEntityNotFoundException extends EntityNotFoundException {
  public TrishulEntityNotFoundException(String entityName, String identifierName,
      String identifierValue) {
    super(entityName, identifierName, identifierValue);
  }
}
