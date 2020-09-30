package com.pl.restApi.service;

import com.pl.restApi.model.Transaction;
import com.pl.restApi.repository.ITransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
