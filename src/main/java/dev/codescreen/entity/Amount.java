package dev.codescreen.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.codescreen.domain.DebitOrCredit;

@Entity
public class Amount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amountId;

    private String amount;
    
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private DebitOrCredit debitOrCredit;
    
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public DebitOrCredit getDebitOrCredit() {
		return debitOrCredit;
	}
	public void setDebitOrCredit(DebitOrCredit debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}
    
    

}
