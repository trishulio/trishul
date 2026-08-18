package sh.trishul.crud.controller;

import sh.trishul.model.base.exception.EntityNotFoundException;

public class ServiceEntityNotFoundException extends EntityNotFoundException {
  public ServiceEntityNotFoundException(String entityName, String identifierName,
      String identifierValue) {
    super(entityName, identifierName, identifierValue);
  }
}
