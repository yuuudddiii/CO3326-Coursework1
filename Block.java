import java.util.List;

import org.json.JSONException;
import java.util.LinkedHashMap;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class Block {
	private String version;
	private Date timeStamp;
	private String hash;
	private String previousHash;
	private List<Transaction> data; // data
	private int nonce;
	private int difficulty=3;
	private String merkleRoot;
	private LinkedHashMap blockJSON;
	private List<LinkedHashMap> transactionJSONList;
	
	
	public Block(String previousHash, ArrayList<Transaction> transactions, String hash, int nonce, String merkleRoot) throws JSONException {
		this.transactionJSONList = new ArrayList<LinkedHashMap>();
		this.blockJSON = new LinkedHashMap();
		this.timeStamp = new Date();
		this.previousHash = previousHash;
		this.data = transactions;
		this.hash = hash;
		this.nonce = nonce;
		this.merkleRoot = merkleRoot;
		for (Transaction t : data) {
			transactionJSONList.add(t.getTransactionJSON());
		}
		this.blockJSON.put("hash", this.hash);
		this.blockJSON.put("previousHash", this.previousHash);
		this.blockJSON.put("transactions", this.transactionJSONList);
		this.blockJSON.put("timeStamp", this.timeStamp);
		this.blockJSON.put("nonce", this.nonce);
		this.blockJSON.put("merkleRoot", this.merkleRoot);
		
	}
	
	
	public LinkedHashMap getBlockJSON() {
		return blockJSON;
	}


	public void setBlockJSON(LinkedHashMap blockJSON) {
		this.blockJSON = blockJSON;
	}


	public List<LinkedHashMap> getTransactionJSONList() {
		return transactionJSONList;
	}


	public void setTransactionJSONList(List<LinkedHashMap> transactionJSONList) {
		this.transactionJSONList = transactionJSONList;
	}


	public Block(String previousHash, ArrayList<Transaction> transactions) throws JSONException {
		this.transactionJSONList = new ArrayList<LinkedHashMap>();
		this.blockJSON = new LinkedHashMap();
		this.previousHash = previousHash;
		this.timeStamp = new Date();
		this.data = transactions;
		this.nonce = 1;
		this.merkleRoot = new MerkleTree(transactions).getRoot();
		this.hash = computeHashString(version+timeStamp+previousHash+merkleRoot);
		mineBlock();
		for (Transaction t : data) {
			transactionJSONList.add(t.getTransactionJSON());
		}
		this.blockJSON.put("hash", this.hash);
		this.blockJSON.put("previousHash", this.previousHash);
		this.blockJSON.put("transactions", this.transactionJSONList);
		this.blockJSON.put("timeStamp", this.timeStamp);
		this.blockJSON.put("nonce", this.nonce);
		this.blockJSON.put("merkleRoot", this.merkleRoot);
	}
	
	
	public String mineBlock() {		
			String leadingZeros = "000";
			while (!this.hash.substring(0,3).equals(leadingZeros)) {
//				System.out.println("Current hash: " + this.hash);
//				System.out.println("nonce: " + this.nonce);
				this.hash = computeHashString(this.hash);
//				System.out.println("Mining...");
				this.nonce+=1;
//				if (this.nonce == 3000) {
//					this.hash = "000" + this.hash.substring(0,this.hash.length()-3);
//				}
			}
			
//			System.out.println("Current hash: " + this.hash);
		return this.hash;
	}




	public String computeHashString(String s) {
		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.hash = encoded;
		return encoded;
	}
	
	public void displayBlock() {
		System.out.println("Nonce: " + this.nonce);
		System.out.println("TimeStamp: " + this.getTimeStamp());
		System.out.println("Hash: " + this.getHash());
		System.out.println("Previous Hash: " + this.getPreviousHash());
		System.out.println();
		System.out.println("Data: "  );
		System.out.println();
		displayData();
	}
	
	public void displayData() {
		for (int i = 0 ; i<data.size(); i++) {
			System.out.println("Transaction number: " + (i+1));
			this.data.get(i).displayTransaction();
			System.out.println();
		}
	}
	public void newTransaction(Wallet origin, Wallet destination, double value) throws Exception {
		Transaction t = new Transaction(origin,destination,value);
		this.data.add(t);
	}
	
	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getMerkleRoot() {
		return merkleRoot;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public String getVersion() {
		return this.version;
	}



	public void setVersion(String version) {
		this.version = version;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return this.previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}


	public List<Transaction> getData() {
		return data;
	}


	public void setData(List<Transaction> data) {
		this.data = data;
	}


}