package com.budgeting.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;

import com.budgeting.demo.model.Register;
import com.budgeting.demo.repository.RegisterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransferController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TransferControllerTest {
    public static final String WALLET = "Wallet";
    public static final String R1 = "R1";
    public static final String R2 = "R2";
    public static final double AMOUNT_1000 = 1000.0;
    public static final String TRANSFER = "/transfer";
    public static final String RECHARGE = "/recharge";
    @Autowired
    private MockMvc mvc;

    @MockBean
    RegisterRepository repository;

    Register register;
    Register sourceRegister;
    Register targetRegister;

    @Before
    public void setUp() {
        register = new Register(1L, WALLET, AMOUNT_1000);
        sourceRegister = new Register(2L, R1, AMOUNT_1000);
        targetRegister = new Register(3L, R2, AMOUNT_1000);
        when(repository.read(WALLET)).thenReturn(register);
        when(repository.read(R1)).thenReturn(sourceRegister);
        when(repository.read(R2)).thenReturn(targetRegister);
    }

    @Test
    public void givenRechargeRequest_rechargeInvoked() throws Exception {
        mvc.perform(post(RECHARGE).content("{ name : \"Wallet\", amount : 100 }"))
                .andExpect(status().is(HttpStatus.OK.value()));
        verify(repository).read(WALLET);
        verify(repository).merge(register);
        assertEquals(1100.0, register.getAmount());
    }

    @Test
    public void givenWrongRecharge_badRequestReturned() throws Exception {
        mvc.perform(post(RECHARGE)
                .content("Some dummy text")).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenNegativeRecharge_badRequestReturned() throws Exception {
        mvc.perform(post(RECHARGE).content("{ name : \"Wallet\", amount : -100 }"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenNonExistingRecharge_badRequestReturned() throws Exception {
        mvc.perform(post(RECHARGE).content("{ name : \"NonExisting\", amount : -100 }"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenTransferRequest_transferInvoked() throws Exception {
        mvc.perform(post(TRANSFER).content("{ source : \"R1\", target : \"R2\", amount : 200 }"))
                .andExpect(status().is(HttpStatus.OK.value()));
        verify(repository).read(R1);
        verify(repository).read(R2);
        verify(repository).merge(sourceRegister, targetRegister);
        assertEquals(800.0, sourceRegister.getAmount());
        assertEquals(1200.0, targetRegister.getAmount());
    }

    @Test
    public void givenWrongTransfer_badRequestReturned() throws Exception {
        mvc.perform(post(TRANSFER)
                .content("Some dummy text")).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenNonExistingTransfer_badRequestReturned() throws Exception {
        mvc.perform(post(TRANSFER).content("{ source : \"R3\", target : \"R2\", amount : 200 }"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenNegativeTransfer_badRequestReturned() throws Exception {
        mvc.perform(post(TRANSFER).content("{ source : \"R1\", target : \"R2\", amount : -200 }"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void givenTooHighTransfer_badRequestReturned() throws Exception {
        mvc.perform(post(TRANSFER).content("{ source : \"R1\", target : \"R2\", amount : 2000 }"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

}