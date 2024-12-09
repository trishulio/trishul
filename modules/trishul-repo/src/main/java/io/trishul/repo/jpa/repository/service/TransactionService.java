package io.trishul.repo.jpa.repository.service;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class TransactionService {
    public void setRollbackOnly() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
