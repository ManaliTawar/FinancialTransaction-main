package dev.codescreen.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.codescreen.entity.Amount;
import dev.codescreen.domain.AuthorizationRequest;
import dev.codescreen.domain.AuthorizationResponse;
import dev.codescreen.domain.DebitOrCredit;
import dev.codescreen.domain.LoadRequest;
import dev.codescreen.domain.LoadResponse;
import dev.codescreen.domain.ResponseCode;
import dev.codescreen.entity.AuthorizationDecline;
import dev.codescreen.entity.User;
import dev.codescreen.exception.TransactionException;
import dev.codescreen.repository.UserRepository;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;
    
    /*
     * 
     * authorize Transaction to authorize and perform the Debit request.
     * 
     * */
    public AuthorizationResponse authorizeTransaction(final String messageId, final AuthorizationRequest request) {
        User user =userRepository.findById(request.getUserId()).orElseThrow(() -> new TransactionException("User not Found"));
        Amount transactionAmount = request.getTransactionAmount();
        
        //initialize the response needs to set before returning to user
        AuthorizationResponse response = new AuthorizationResponse();
        
        if (transactionAmount.getDebitOrCredit() == DebitOrCredit.DEBIT) {
            int debitAmount = Integer.parseInt(transactionAmount.getAmount());
            int existingAmount = Integer.parseInt(user.getAmount().getAmount());
            
            if (existingAmount >= debitAmount) {
                user.getAmount().setAmount(String.valueOf(existingAmount - debitAmount));
                userRepository.save(user);
                
                
                response.setMessageId(messageId);
                response.setUserId(user.getUserId());
                response.setResponseCode(ResponseCode.APPROVED);
                response.setAmount(user.getAmount());
                return response;
            } else {
            	response.setResponseCode(ResponseCode.DECLINED);
            	// Save authorization decline
                saveAuthorizationDecline(messageId, user.getUserId());
                throw new TransactionException("Insufficient funds");
            }
        }
       
        response.setResponseCode(ResponseCode.DECLINED);
        // Save authorization decline
        saveAuthorizationDecline(messageId, user.getUserId());
        return response;
    }
    
    
    /*
     * 
     * load Transaction to authorize and perform the Credit Request.
     * 
     * */
    public LoadResponse loadFunds(final String messageId, final LoadRequest loadRequest) {
    	User user =userRepository.findById(loadRequest.getUserId()).orElseThrow(() -> new TransactionException("User not Found"));
        Amount transactionAmount = loadRequest.getTransactionAmount();

        if (transactionAmount.getDebitOrCredit() == DebitOrCredit.CREDIT) {
        	int creditAmount = Integer.parseInt(transactionAmount.getAmount());
        	user.getAmount().setAmount(String.valueOf(Integer.parseInt(user.getAmount().getAmount()) + creditAmount));
            userRepository.save(user);
           
            LoadResponse response = new LoadResponse();
            response.setMessageId(messageId);
            response.setUserId(user.getUserId());
            response.setAmount(user.getAmount());
            return response;
        } else {
        	throw new TransactionException("Invalid transaction type for loading funds");
        }
    }
    
    
    /*
     * Helper method to save the decline transaction info
     * 
     * */
    
    private void saveAuthorizationDecline(String messageId, String userId) {
        AuthorizationDecline decline = new AuthorizationDecline();
        decline.setMessageId(messageId);
        decline.setUserId(userId);
        decline.setTimestamp(LocalDateTime.now());
        userRepository.saveAuthorizationDecline(decline);
    }

}
