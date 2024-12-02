package io.trishul.crud.service;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class TransactionService {
    public void setRollbackOnly() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
