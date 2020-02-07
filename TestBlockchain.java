import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.util.ArrayList;
import java.util.Scanner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.util.LinkedHashMap;

// ADD NUMBERING TO BLOCKS AND TRANSACTIONS AND OUTPUTS THEN DONE!!!!!!!

public class TestBlockchain {
	
	public static void main(String args[]) throws Exception {
		Security.addProvider(new BouncyCastleProvider());		
		Blockchain test = new Blockchain();
		Wallet yudi = new Wallet(
				"yudi"
				,"3049301306072a8648ce3d020106082a8648ce3d0301010332000494423003421615d6901e11f32b5f9968ca4550a9af6d76c8245142a208beec41001c3374894cbaa55607a341012b5874"
			, "307b020100301306072a8648ce3d020106082a8648ce3d0301010461305f0201010418859df1716aebb14634e536d74988b3c96423070b9864e354a00a06082a8648ce3d030101a1340332000494423003421615d6901e11f32b5f9968ca4550a9af6d76c8245142a208beec41001c3374894cbaa55607a341012b5874"
			, 0);
		
		Wallet alice = new Wallet(
				"alice",
				"3049301306072a8648ce3d020106082a8648ce3d03010103320004988b44751236be1551d22b60ed7961e84e6afaa4518fc8b134cd983f741f413aeae8eff260604ddbe48d45f5eeda3429"
				, "307b020100301306072a8648ce3d020106082a8648ce3d0301010461305f0201010418f5c0f5e5834d4c9340e050bc1abc0432683da731a0b2ab5da00a06082a8648ce3d030101a13403320004988b44751236be1551d22b60ed7961e84e6afaa4518fc8b134cd983f741f413aeae8eff260604ddbe48d45f5eeda3429"
				, 100);
		
		Wallet bob = new Wallet(
				"bob",
				"3049301306072a8648ce3d020106082a8648ce3d03010103320004a863450bf0d4efa642a8c946679b9d64ca2d344cf72b5d8f3b0a743d44785d3cc0c8129cef0cc6827b3668bbd7616ae8"
				, "307b020100301306072a8648ce3d020106082a8648ce3d0301010461305f02010104181be718111e077f7a60add1669e14373f02d87783ecb3a0bba00a06082a8648ce3d030101a13403320004a863450bf0d4efa642a8c946679b9d64ca2d344cf72b5d8f3b0a743d44785d3cc0c8129cef0cc6827b3668bbd7616ae8"
				, 100);
		
		Wallet coinBase = new Wallet(
				"Coin Base",
				"3049301306072a8648ce3d020106082a8648ce3d0301010332000492c92f879ce0433b40f2d62242c31f045830e78603ad41a2a5f21237c0a710b4b291e9abd01e6c3f7d8319e625bbc3ed"
				, "307b020100301306072a8648ce3d020106082a8648ce3d0301010461305f020101041860811539514270a15d86934e22cb2f2645c5e58043ae5ef6a00a06082a8648ce3d030101a1340332000492c92f879ce0433b40f2d62242c31f045830e78603ad41a2a5f21237c0a710b4b291e9abd01e6c3f7d8319e625bbc3ed"
				, 100000);
		
		
		Transaction genesisT = new Transaction(coinBase,yudi,100);
		ArrayList<Transaction> genesisTList = new ArrayList<Transaction>();
		genesisTList.add(genesisT);
		Block genesis = new Block("0", genesisTList, "00033083e41de14d0223df78785cddf1c5d5c3508b942e16a472cbeb4a5bb751"
				, 735, "c68d1323ed5f74021b9d67ad1aacf962ae7802b73bff37602a76cca5f90745f7");
		test.addGenesisBlock(genesis);
		
		ArrayList<Transaction> B1T = new ArrayList<Transaction>();
		B1T.add(new Transaction(yudi,alice,40));
		B1T.add(new Transaction(yudi,bob,30));
		System.out.println("Generating 1st Block...");
		test.addBlock(new Block("", B1T));
		System.out.println("1st Block succesfully generated!");
		
		ArrayList<Transaction> B2T = new ArrayList<Transaction>();
		B2T.add(new Transaction(alice,bob,20));
		B2T.add(new Transaction(bob,yudi,10));
		System.out.println("Generating 2nd Block...");
		test.addBlock(new Block("", B2T));
		System.out.println("2nd Block successfully generated!");
		
		test.addJSON();
		
		System.out.println(test.getSubmission());
//		System.out.println(test.getBlockChainJSON());
		
		JSONValue obj = new JSONValue();
		JSONObject json = new JSONObject(test.getSubmission());
		
		String submission = JSONValue.toJSONString(test.getSubmission());
		writeJsonToFile("AlbertusSatriaYudistira_170281205_CO3326cw1.json", submission);
	
	
	
	}
	
	public static void writeJsonToFile(String filename,String obj) throws Exception {
	    Files.write(Paths.get(filename), obj.toString().getBytes());
	}
	
	public void addTransaction(ArrayList<Transaction> t, Transaction transaction) {
		t.add(transaction);
	}
}