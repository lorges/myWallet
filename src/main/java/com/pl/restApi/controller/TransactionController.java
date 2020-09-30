package com.pl.restApi.controller;

import com.pl.restApi.service.ITransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(path = "/transactions")
@AllArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping()
    public ResponseEntity getTransactions() {
        log.info("TransactionController::getTransactions()");
        return ResponseEntity
                    .ok(transactionService.getTransactions());
    }
}
