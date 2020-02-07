import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import java.util.LinkedHashMap;

public class Blockchain {
	
	private List<Block> chain;
	private List<LinkedHashMap> blockChainJSON;
	private LinkedHashMap submission = new LinkedHashMap();
	Wallet yudi = new Wallet(
			"yudi"
			,"3049301306072a8648ce3d020106082a8648ce3d0301010332000494423003421615d6901e11f32b5f9968ca4550a9af6d76c8245142a208beec41001c3374894cbaa55607a341012b5874"
		, "307b020100301306072a8648ce3d020106082a8648ce3d0301010461305f0201010418859df1716aebb14634e536d74988b3c96423070b9864e354a00a06082a8648ce3d030101a1340332000494423003421615d6901e11f32b5f9968ca4550a9af6d76c8245142a208beec41001c3374894cbaa55607a341012b5874"
		, 40);
	public Blockchain() throws JSONException {
		blockChainJSON = new ArrayList<LinkedHashMap>();
		chain = new ArrayList<Block>();
		
		

	}
	
	public List<Block> getChain() {
		return chain;
	}

	public LinkedHashMap getSubmission() {
		return submission;
	}

	public void setSubmission(LinkedHashMap submission) {
		this.submission = submission;
	}

	public void setChain(List<Block> chain) {
		this.chain = chain;
	}

	public void addGenesisBlock(Block block) {
		System.out.println(block.getBlockJSON());
		this.chain.add(block);

	}
	public void addBlock(Block block) {
		Block newBlock = block;
		newBlock.setPreviousHash(chain.get(chain.size()-1).getHash());
		this.blockChainJSON.add(newBlock.getBlockJSON());
		this.chain.add(newBlock);
	}

	public List<LinkedHashMap> getBlockChainJSON() {
		return blockChainJSON;
	}

	public void setBlockChainJSON(List<LinkedHashMap> blockChainJSON) {
		this.blockChainJSON = blockChainJSON;
	}

	public void displayChain() {
		for (int i=0; i<chain.size(); i++) {
			System.out.println("---------");
			System.out.println("Block: " + i);
			chain.get(i).displayBlock();
			System.out.println("---------");
			System.out.println();  
		}

	}
	
	public void addJSON() throws JSONException {
		for(Block b : chain) this.blockChainJSON.add(b.getBlockJSON());
		this.submission.put("srn", "170281205");
		this.submission.put("name", "Albertus Satria Yudistira");
		this.submission.put("wallet", this.yudi.getWalletJSON());
		this.submission.put("blockchain", this.blockChainJSON);
	}


}