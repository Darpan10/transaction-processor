package com.demo.induction.tp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class XmlTransactionProcessorTest {
	
	
@Test
public void testxml() {
 
	  String files = ("file.xml");
	  InputStream file = null;
	try {
		file = new FileInputStream(files);
	} catch (FileNotFoundException e) {
	 	e.printStackTrace();
	}
	  TransactionProcessorImpXml obj = new TransactionProcessorImpXml();
	  obj.importTransactions(file);
}

	
}


