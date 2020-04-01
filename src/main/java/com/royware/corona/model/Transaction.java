package com.royware.corona.model;

import java.util.Date;

/**
 * Class for logging banking transactions
 *
 */
public class Transaction {
	//Class data
	private Date transactionDate;
	private String accountNumber;
	private double amount;
	private String transactionType;
	//Constructors
	public Transaction() {
		
	}
	
	/**
	 * @param transactionDate the date-time of the transaction
	 * @param accountNumber the account number string
	 * @param amount the dollar amount of the transaction
	 * @param transactionType the description of the transaction
	 */
	public Transaction(Date transactionDate, String accountNumber, double amount, String transactionType) {
		this.transactionDate = transactionDate;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionDate=" + transactionDate + ", accountNumber=" + accountNumber + ", amount="
				+ amount + ", transactionType=" + transactionType + "]";
	}

	//Accessors and mutators
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}
