package com.demo.induction.tp;

import java.math.BigDecimal;


public class Transaction {
	
	private String type;
    private BigDecimal amount;
    private String narration;
    
    
    

    public Transaction(String type, String narration, BigDecimal amount) {   //constructor
        this.amount=amount;
        this.type=type;
        this.narration=narration;
    }
    
        
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}


 

}
