package com.pl.restApi.controller;

import com.pl.restApi.model.Transaction;
import com.pl.restApi.model.enums.TransactionKind;
import com.pl.restApi.model.enums.TransactionType;
import com.pl.restApi.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITransactionService transactionService;

    public List<Transaction> transactionList;

    @BeforeEach
    void setUp() {
        this.transactionList = new ArrayList<>();
        transactionList.add(new Transaction(1L, "Testowa 1", "Testowe 1 desc",
                LocalDate.of(2020,10,10), BigDecimal.TEN, TransactionType.SPEND, TransactionKind.FOOD));

        transactionList.add(new Transaction(2L, "Testowa 2", "Testowe 2 desc",
                LocalDate.of(2020,10,10), BigDecimal.valueOf(23.333), TransactionType.SPEND, TransactionKind.FOOD));
    }

    @Test
    void shouldReturnEmptyListWhenTransactionsNotExsits() throws Exception {
        given(transactionService.getTransactions()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnTransactionsWhenTransactionsExsits() throws Exception {
        given(transactionService.getTransactions()).willReturn(transactionList);

        mockMvc.perform(get("/transactions/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transactionName", equalTo("Testowa 1")))
                .andExpect(jsonPath("$[0].transactionDesc", equalTo("Testowe 1 desc")))
                .andExpect(jsonPath("$[0].transactionDate", is(LocalDate.of(2020,10, 10 ).toString())))
                .andExpect(jsonPath("$[0].transactionAmount", is(10)))
                .andExpect(jsonPath("$[0].transactionType", equalTo("SPEND")))
                .andExpect(jsonPath("$[0].transactionKind", equalTo("FOOD")))
                .andExpect(jsonPath("$[1].transactionName", equalTo("Testowa 2")))
                .andExpect(jsonPath("$[1].transactionDesc", equalTo("Testowe 2 desc")))
                .andExpect(jsonPath("$[1].transactionDate", is(LocalDate.of(2020,10, 10 ).toString())));

    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
