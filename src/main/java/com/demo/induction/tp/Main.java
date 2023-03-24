package com.demo.induction.tp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
		System.out.print("Enter the one extension  among (xml/csv)   :   ");  
		String a= sc.nextLine();  
		 
		
		if (a.equals("xml"))
		{
			
			  String files = ("file.xml");
			  InputStream file = new FileInputStream(files);
			  TransactionProcessorImpXml obj = new TransactionProcessorImpXml();
			  obj.importTransactions(file);
				
		}
		
		
		else if (a.equals("csv"))
		{
		
		  String files = ("file.csv"); 
		  InputStream file = new FileInputStream(files);
		  TransactionProcessorImpCsv obj = new TransactionProcessorImpCsv();
		  obj.importTransactions(file);
		 
		}
		
		
		
		
	}

}
