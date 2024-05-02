package dev.codescreen.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.domain.AuthorizationRequest;
import dev.codescreen.domain.AuthorizationResponse;
import dev.codescreen.domain.LoadRequest;
import dev.codescreen.domain.LoadResponse;
import dev.codescreen.domain.Ping;
import dev.codescreen.exception.TransactionError;
import dev.codescreen.exception.TransactionException;
import dev.codescreen.service.TransactionService;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/ping")
    public ResponseEntity<Ping> ping() {
    	Ping ping = new Ping();
    	ping.setServerTime(String.valueOf(LocalDateTime.now()));
    	return ResponseEntity.ok(ping);
    	
    }

    @PutMapping("/authorization/{messageId}")
    public ResponseEntity<Object> authorizationTransaction(@PathVariable String messageId,
            @RequestBody AuthorizationRequest authorizationRequest) {
    	
    	logger.info(this.getClass().getSimpleName() + " - authorizationTransaction is invoked with messageId: %s", messageId);

        try {

            AuthorizationResponse response = transactionService.authorizeTransaction(messageId,
                    authorizationRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (final TransactionException e) {

        	TransactionError error = new TransactionError("Authorization failed", e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }

    }// authorizationTransaction

    @PutMapping("/load/{messageId}")
    public ResponseEntity<Object> loadFunds(@PathVariable String messageId,
            @RequestBody LoadRequest loadRequest) {

    	logger.info(this.getClass().getSimpleName() + " -loadFunds is invoked with messageId: %s", messageId);
        try {

            LoadResponse response = transactionService.loadFunds(messageId, loadRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (final TransactionException e) {

        	TransactionError error = new TransactionError("Load Funds failed", e.getMessage());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }

    }// loadFunds

}
