import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashMap;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONArray;

public class Wallet {
	private LinkedHashMap walletJSON = new LinkedHashMap();
	private String name;
	private String publicKeyString;
	private String privateKeyString;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private double value;

	public Wallet(String name, String publicKey, String privateKey, double value) throws JSONException {
		this.name = name;
		this.publicKey = getPublicKeyFromString(publicKey);
		this.privateKey = getPrivateKeyFromString(privateKey);
		this.value = value;
		this.publicKeyString = publicKey;
		this.privateKeyString = privateKey;
		walletJSON.put("privateKey", this.privateKeyString);
		walletJSON.put("publicKey", this.publicKeyString);
		walletJSON.put("balance", this.value);
	}

	

	public LinkedHashMap getWalletJSON() {
		return walletJSON;
	}



	public void setWalletJSON(LinkedHashMap walletJSON) {
		this.walletJSON = walletJSON;
	}



	public String getPublicKeyString() {
		return publicKeyString;
	}

	public void setPublicKeyString(String publicKeyString) {
		this.publicKeyString = publicKeyString;
	}

	public String getPrivateKeyString() {
		return privateKeyString;
	}

	public void setPrivateKeyString(String privateKeyString) {
		this.privateKeyString = privateKeyString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	PublicKey getPublicKeyFromString(final String publicKeyStr) {
		try {
			X509EncodedKeySpec spec =
					new X509EncodedKeySpec(Hex.decodeHex(publicKeyStr));
			KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
			return factory.generatePublic(spec);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Can't transform [" + publicKeyStr + "] to PublicKey", e);
		}
	}

	PrivateKey getPrivateKeyFromString(final String privateKeyStr) {
		try {
			PKCS8EncodedKeySpec spec =
					new PKCS8EncodedKeySpec(Hex.decodeHex(privateKeyStr));
			KeyFactory factory = KeyFactory.getInstance("ECDSA", "BC");
			return factory.generatePrivate(spec);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Can't transform [" + privateKeyStr + "] to PrivateKey", e);
		}
	}


	
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
