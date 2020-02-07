import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import java.util.LinkedHashMap;

public class Transaction {
	private Wallet sender;
	private Wallet recipient;
	private String hash;
	private String signature;
	private double value;
	private double outputSenderValue;
	private double outputRecipientValue;
	private String outputSenderHash;
	private String outputRecipientHash;
	private LinkedHashMap transactionJSON = new LinkedHashMap();
	private LinkedHashMap outputRecipientJSON = new LinkedHashMap();
	private LinkedHashMap outputSenderJSON = new LinkedHashMap();
	private ArrayList<LinkedHashMap> outputJSONList;
	
	



	public Transaction(Wallet sender, Wallet recipient, double value) throws Exception {
		this.sender = sender;
		this.value = value;
		this.recipient = recipient;
		this.hash = computeHash();
		this.sender.setValue(this.sender.getValue() - value);
		this.recipient.setValue(this.recipient.getValue() + value);
		this.signature = signTransaction(this.sender.getPrivateKey());
		this.outputSenderValue = sender.getValue() - this.value;
		this.outputRecipientValue = this.value;
		this.outputSenderHash = computeHash(sender.getPublicKey().toString(), this.outputSenderValue, this.hash);
		this.outputRecipientHash = computeHash(recipient.getPublicKey().toString(), this.outputRecipientValue, this.hash);
		if (checkValidity() != true) {
			throw new Exception("This transaction is not valid!");
		}
		this.transactionJSON.put("sender", this.sender.getPublicKeyString());
		this.transactionJSON.put("recipient", this.recipient.getPublicKeyString());
		this.transactionJSON.put("value", this.value);
		this.transactionJSON.put("hash", this.hash);
		this.transactionJSON.put("signature", this.signature);
		this.outputRecipientJSON.put("Recipient", this.recipient.getPublicKeyString());
		this.outputRecipientJSON.put("value", this.outputRecipientValue);
		this.outputRecipientJSON.put("parentHash", this.hash);
		this.outputRecipientJSON.put("hash", this.outputRecipientHash);
		this.outputSenderJSON.put("Recipient", this.sender.getPublicKeyString());
		this.outputSenderJSON.put("value", this.outputSenderValue);
		this.outputSenderJSON.put("parentHash", this.hash);
		this.outputSenderJSON.put("hash", this.outputSenderHash);
		this.outputJSONList = new ArrayList<LinkedHashMap>();
		this.outputJSONList.add(this.outputRecipientJSON);
		this.outputJSONList.add(this.outputSenderJSON);
		this.transactionJSON.put("Output: ", this.outputJSONList);
		
	}
	
	
	public void displayTransaction() {
		System.out.println("Sender: " + sender.getPublicKeyString());
		System.out.println("Recipient: " + recipient.getPublicKeyString());
		System.out.println("Amount Transferred: " + this.value);
		System.out.println("Signature: " + this.signature);
		System.out.println("Unspent Transaction Outputs: \n");
		System.out.println("Recipient: " + recipient.getPublicKeyString());
		System.out.println("Value: " + outputRecipientValue);
		System.out.println("parentHash: " + this.hash);
		System.out.println("Output Hash: " + this.outputRecipientHash);
		System.out.println();
		System.out.println("Recipient: " + sender.getPublicKeyString());
		System.out.println("Value: " + outputSenderValue);
		System.out.println("parentHash: " + this.hash);
		System.out.println("Output Hash: " + this.outputSenderHash);
		
	}

	public String signTransaction(PrivateKey signingKey) throws Exception {
		if (signingKey != this.sender.getPrivateKey()) {
			throw new Exception("You can't sign transactions for other wallets!");
		}
		
		return computeHash(signingKey);
	}
	
	public boolean checkValidity() throws Exception {
		if (this.sender == null || this.recipient == null) return false;
		
		if (this.signature == null|| this.signature.length() == 0) {
			throw new Exception("This transaction does not have a signature!");
		}
		
		if (this.sender.getValue() < this.value) {
			throw new Exception("Can't transfer more than what is in the sender wallet");
		}
		
		return true;
	}
	
	public String computeHash(PrivateKey key) {
		
		String dataToHash = key.toString();

		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.hash = encoded;
		return encoded;
	}
	
	public String computeHash(String recipient, Double value, String parentHash) {

		String dataToHash = recipient+value+parentHash;

		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.hash = encoded;
		return encoded;
	}
	
	public String computeHash() {

		String dataToHash = sender.getPublicKey().toString() + recipient.getPrivateKey().toString() + value;

		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.hash = encoded;
		return encoded;
	}
	
	public LinkedHashMap getOuputRecipientJSON() {
		return outputRecipientJSON;
	}


	public void setOuputRecipientJSON(LinkedHashMap ouputRecipientJSON) {
		this.outputRecipientJSON = ouputRecipientJSON;
	}


	public LinkedHashMap getOutputSenderJSON() {
		return outputSenderJSON;
	}


	public void setOutputSenderJSON(LinkedHashMap outputSenderJSON) {
		this.outputSenderJSON = outputSenderJSON;
	}


	public void setOutputRecipientHash(String outputRecipientHash) {
		this.outputRecipientHash = outputRecipientHash;
	}

	
	public double getOutputSenderValue() {
		return outputSenderValue;
	}


	public void setOutputSenderValue(double outputSenderValue) {
		this.outputSenderValue = outputSenderValue;
	}


	public double getOutputRecipientValue() {
		return outputRecipientValue;
	}


	public void setOutputRecipientValue(double outputRecipientValue) {
		this.outputRecipientValue = outputRecipientValue;
	}


	public String getOutputSenderHash() {
		return outputSenderHash;
	}


	public void setOutputSenderHash(String outputSenderHash) {
		this.outputSenderHash = outputSenderHash;
	}


	public String getOutputRecipientHash() {
		return outputRecipientHash;
	}


	public void setOutputRecipientValue(String outputRecipientValueHash) {
		this.outputRecipientValue = outputRecipientValue;
	}
	
	public Wallet getSender() {
		return sender;
	}

	public void setSender(Wallet sender) {
		this.sender = sender;
	}

	public Wallet getRecipient() {
		return recipient;
	}

	public void setRecipient(Wallet recipient) {
		this.recipient = recipient;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public LinkedHashMap getTransactionJSON() {
		return transactionJSON;
	}

	public void setTransactionJSON(LinkedHashMap transactionJSON) {
		this.transactionJSON = transactionJSON;
	}
	
	public double getoutputSenderValue() {
		return outputSenderValue;
	}

	public void setoutputSenderValue(double outputSenderValue) {
		this.outputSenderValue = outputSenderValue;
	}

	public double getoutputRecipientValue() {
		return outputRecipientValue;
	}

	public void setoutputRecipientValue(double outputRecipientValue) {
		this.outputRecipientValue = outputRecipientValue;
	}

	public String getoutputSenderHash() {
		return outputSenderHash;
	}

	public void setoutputSenderHash(String outputSenderHash) {
		this.outputSenderHash = outputSenderHash;
	}


	public Wallet getsender() {
		return sender;
	}

	public void setsender(Wallet sender) {
		this.sender = sender;
	}

	public Wallet getrecipient() {
		return recipient;
	}

	public void setrecipient(Wallet recipient) {
		this.recipient = recipient;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
