package com.pl.restApi.service;

import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.repository.ITransactionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class TransactionServiceTest {

    @Mock
    ITransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    private List<Transaction> mockTransactionsList = new ArrayList<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockTransactionsList = new ArrayList<>();
        mockTransactionsList.add(new Transaction(1L, "Testowa 1", "Testowe 1 desc",
                LocalDate.of(2020,10,10), BigDecimal.TEN, TransactionType.SPEND, TransactionKind.FOOD));

        mockTransactionsList.add(new Transaction(2L, "Testowa 2", "Testowe 2 desc",
                LocalDate.of(2020,10,10), BigDecimal.valueOf(23.333), TransactionType.SPEND, TransactionKind.FOOD));
    }

    @Test
    void getTransactionShouldReturnEmptyListWhenNoTransactions() {
        given(transactionRepository.findAll()).willReturn(Collections.emptyList());

        List<Transaction> transactionList = transactionService.getTransactions();

        assertEquals(0, transactionList.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransactionShouldReturnTransactionsList() {
        given(transactionRepository.findAll()).willReturn(mockTransactionsList);

        List<Transaction> transactionList = transactionService.getTransactions();

        assertEquals(mockTransactionsList.size(), transactionList.size());
        assertEquals(mockTransactionsList.get(0),transactionList.get(0));
        assertEquals(mockTransactionsList.get(1),transactionList.get(1));
        verify(transactionRepository, times(1)).findAll();
    }
}
