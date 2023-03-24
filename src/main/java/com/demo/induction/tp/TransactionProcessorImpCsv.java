package com.demo.induction.tp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class TransactionProcessorImpCsv implements TransactionProcessor {

	Transaction transaction;
	List<Violation> validateLst;
	List<Transaction> importedTransactions;
	double cAmount = 0.00;
	double dAmount = 0.000;
	BigDecimal DebitAmounts = new BigDecimal(dAmount);
	BigDecimal CreditbitAmounts = new BigDecimal(cAmount);
	double Amt = 0.000;
	BigDecimal Amounts = new BigDecimal(Amt);
	int order = 0;
	String[] fields;

	@Override
	public void importTransactions(InputStream is) {
		List<Transaction> transacionlst = new ArrayList<Transaction>();

		@SuppressWarnings("unused")
		CSVReader reader = null;
		Reader reader1 = new InputStreamReader(is);
		CSVReader br = new CSVReader(reader1, ',', '"', 0);

		String[] nextLine;

		try {
			while ((nextLine = br.readNext()) != null) {
				if (nextLine != null) {
					order += 1;
				

				     fields = nextLine[0].split(",");
				         	validate();
				         	if ((validateLst != null)) {   // validateLst is the List of Violation ie Error
				         		
								for (int i = 0; i < 1; i++) {
									System.out.println("                               Errors                      ");
									System.out.println("------------------------------------------------------------");
									System.out.println("Error Order          " + validateLst.get(i).getOrder());
									System.out.println("Error Property       " + validateLst.get(i).getProperty());
									System.out.println("Error Discription    " + validateLst.get(i).getDescription());
			
								}
			
								System.exit(1);
							}
				         	
					        String type = fields[0];
							String narration = fields[2];
							BigDecimal amount = BigDecimal.valueOf(Double.valueOf(fields[1]));
							transaction = new Transaction(type, narration, amount);   // populate the constructor
							transacionlst.add(transaction);
							setImportedTransactions(transacionlst);
							order += 1;
							 
							check();

							if ((validateLst != null)) { // validateLst is the List of Violation ie Error

								for (int i = 0; i < 1; i++) {
									System.out.println("                               Errors                      ");
									System.out.println("------------------------------------------------------------");
									System.out.println("Error Order          " + validateLst.get(i).getOrder());
									System.out.println("Error Property       " + validateLst.get(i).getProperty());
									System.out.println("Error Discription    " + validateLst.get(i).getDescription());

								}
								System.exit(1);
							}

						}
 
						 
					 }
				
			
			isBalanced();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Transaction> getImportedTransactions() {

		return importedTransactions;
	}

	public void setImportedTransactions(List<Transaction> importedTransactions) {
		this.importedTransactions = importedTransactions;
	}

	@Override
	public List<Violation> validate() {
		Violation violation = new Violation();
		List<Violation> validateLst = new ArrayList<Violation>();
		
	 
 
		try {
			Amounts = BigDecimal.valueOf(Double.valueOf(fields[1]));
		} catch (Exception e) {
			violation.setDescription("invalid amount entry");   //  error handling for Amount because it is field of great concern
			violation.setOrder(order);
			violation.setProperty("amount");
			validateLst.add(0, violation);
			setValidateLst(validateLst);
			System.out.println("                     AMOUNT Error                      ");
			System.out.println("------------------------------------------------------------");
			System.out.println("Error Order                 " + violation.getOrder());
			System.out.println("Error Property              " + violation.getProperty());
			System.out.println("Error Discription           " + violation.getDescription());
			System.out.println("------------------------------------------------------------");

		}
		      
	 
		 if (!(fields[0].equals("C")) && !(fields[0].equals("D")) || (fields[0].isEmpty())) {

			violation.setOrder(order);
			violation.setProperty("types");
			violation.setDescription("invalid type entry");
			validateLst.add(0, violation);
			setValidateLst(validateLst);

		}

		else if (fields.length < 3) {
			violation.setDescription("invalid narration");
			violation.setOrder(order);
			violation.setProperty("narration");
			validateLst.add(0, violation);
			setValidateLst(validateLst);

		}

		return validateLst;

	}

	public void check()

	{
		BigDecimal amount = transaction.getAmount();
		String type = transaction.getType(); // adding the debited amount and credited amount seperately
		if (type.equals("D")) {
			DebitAmounts = DebitAmounts.add(amount);

		}
		if (type.equals("C")) {
			CreditbitAmounts = CreditbitAmounts.add(amount);
		}

	}

	public void setValidateLst(List<Violation> validateLst) {
		this.validateLst = validateLst;
	}

	@Override
	public boolean isBalanced() {
		if (DebitAmounts.doubleValue() == CreditbitAmounts.doubleValue()) // comparing two amounts
		{
			System.out.println("balanced");
			return true;
		}

		else {
			System.out.println(" NOT balanced");
			return false;
		}

	}

}
