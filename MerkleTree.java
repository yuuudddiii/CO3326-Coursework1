import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MerkleTree {
	private List<Transaction> transactions;
	private ArrayList<String> hashes;
	private String root;
	
	public MerkleTree(List<Transaction> transactions) {
		this.transactions = transactions;
		this.hashes = new ArrayList<String>();
		for (Transaction t : transactions) {
			this.hashes.add(t.getHash());
		}
		this.root = computeRoot(hashes);
	}
	
	public String computeRoot(ArrayList<String> hashes) {
		while (this.hashes.size() > 1) {
			this.hashes = hashListIteration(this.hashes);
		}
		return this.hashes.get(0);
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public ArrayList<String> getHashes() {
		return hashes;
	}

	public void setHashes(ArrayList<String> hashes) {
		this.hashes = hashes;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public ArrayList<String> hashListIteration(ArrayList<String> hashes) {
		for (int i = 0; i<hashes.size(); i+=2) {
			if (hashes.get(i+1) == null) {
				String temp = computeHash(hashes.get(i),hashes.get(i));
				hashes.add(temp);
				hashes.remove(i);
			}
			
			else {
				String temp = computeHash(hashes.get(i),hashes.get(i+1));
				hashes.add(temp);
				hashes.remove(i);
				hashes.remove(i+1);
			}
		}
		
		return hashes;
	}
	
	public String computeHash(String firstHash, String secondHash) {

		String dataToHash = firstHash + secondHash;

		MessageDigest digest;
		String encoded = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return encoded;
	}
	
}
