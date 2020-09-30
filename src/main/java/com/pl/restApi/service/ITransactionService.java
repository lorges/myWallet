package com.pl.restApi.service;

import com.pl.restApi.model.Transaction;

import java.util.List;

public interface ITransactionService {

    List<Transaction> getTransactions();
}
