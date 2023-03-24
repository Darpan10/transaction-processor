package com.demo.induction.tp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class CsvTransactionProcessorTest {
	
	@Test
	public void testcsv() {
		
		  String files = ("file.csv"); 
		  InputStream file = null;
		try {
			file = new FileInputStream(files);
		} catch (FileNotFoundException e) {
		 
			e.printStackTrace();
		}
		  TransactionProcessorImpCsv obj = new TransactionProcessorImpCsv();
		  obj.importTransactions(file);
	}
	 
	
	
	
	
	
}
