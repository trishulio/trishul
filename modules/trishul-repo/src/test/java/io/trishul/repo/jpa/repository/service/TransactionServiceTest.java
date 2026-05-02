package io.trishul.repo.jpa.repository.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

class TransactionServiceTest {
  private TransactionService transactionService;

  @BeforeEach
  void init() {
    transactionService = new TransactionService();
  }

  @Test
  void testSetRollbackOnly_CallsSetRollbackOnlyOnCurrentTransactionStatus() {
    TransactionStatus mockStatus = mock(TransactionStatus.class);

    try (MockedStatic<TransactionAspectSupport> mockedStatic
        = mockStatic(TransactionAspectSupport.class)) {
      mockedStatic.when(TransactionAspectSupport::currentTransactionStatus).thenReturn(mockStatus);

      transactionService.setRollbackOnly();

      verify(mockStatus).setRollbackOnly();
    }
  }
}
