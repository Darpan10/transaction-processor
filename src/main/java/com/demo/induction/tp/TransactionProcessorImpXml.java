package com.demo.induction.tp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TransactionProcessorImpXml implements TransactionProcessor {

	Transaction transaction;
	List<Transaction> importedTransactions;
	List<Violation> validateLst;
	double cAmount = 0.00;
	double dAmount = 0.000;
	BigDecimal DebitAmounts = new BigDecimal(dAmount);
	BigDecimal CreditbitAmounts = new BigDecimal(cAmount);
	double Amt = 0.000;
	BigDecimal Amount = new BigDecimal(Amt);
	int order = 0;

	@Override
	public void importTransactions(InputStream is) {
		List<Transaction> transacionlst = new ArrayList<Transaction>();

		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		}

		// Build Document
		Document document = null;
		try {
			document = builder.parse(new File("file.xml"));
		} catch (SAXException | IOException e) {

			e.printStackTrace();
		}

		NodeList nlist = document.getElementsByTagName("Transaction");

		for (int temp = 0; temp < nlist.getLength(); temp++) {
			Node node = nlist.item(temp);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// Print each employee's detail
				Element eElement = (Element) node;
				String type = eElement.getAttribute("type");
				String narration = eElement.getAttribute("narration");
				BigDecimal Amount = BigDecimal.valueOf(Double.valueOf(eElement.getAttribute("amount")));
				transaction = new Transaction(type, narration, Amount);
				transaction.getAmount();
				transaction.getNarration();
				transacionlst.add(transaction);
				setImportedTransactions(transacionlst);
				order += 1;
				validate();
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
		double amount = transaction.getAmount().doubleValue();
		String type = transaction.getType();
		String narration = transaction.getNarration();

		if (amount <= 0) {

			violation.setDescription("invalid amount entry"); // error handling for Amount because it is field of great
																// concern
			violation.setOrder(order);
			violation.setProperty("amount");
			validateLst.add(0, violation);
			setValidateLst(validateLst);

		}

		else if (!(type.equals("C")) && !(type.equals("D")) || (type.isEmpty())) {

			violation.setOrder(order);
			violation.setProperty("types");
			violation.setDescription("invalid type entry");
			validateLst.add(0, violation);
			setValidateLst(validateLst);

		}

		else if (narration.length() < 3 || narration.length() == 0) {
			violation.setDescription("invalid narration");
			violation.setOrder(order);
			violation.setProperty("invalid narration entry");
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

	private void setValidateLst(List<Violation> validateLst) {

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
