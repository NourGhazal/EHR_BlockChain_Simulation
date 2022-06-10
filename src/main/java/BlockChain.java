import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BlockChain {
    static ArrayList<Block> blocks= new ArrayList<Block>();
    private HashMap<String,ArrayList<String>> accessChain = new HashMap<String,ArrayList<String>>();

    public static int prefix = 4;

    static {
        Block genesisBlock = new Block("0" ,
                "The is the Genesis Block.", new Date().getTime());
        blocks.add(genesisBlock);
    }

    private boolean checkBlockChainValidity(){
        Block previousBlock = blocks.get(0);
        for(int i=1; i < blocks.size(); i++){
            Block currentBlock = blocks.get(i);
            String previousBlockHash = Block.calculateHash(previousBlock);
            if(!previousBlockHash.equals(currentBlock.getPreviousBlockHash())){
                return false;
            }
            previousBlock = currentBlock;

        }
        return true;
    }
    public void addBlockToChain(String blockContent,String code) throws Exception {
        Block lastBlock = blocks.get(blocks.size()-1);
        lastBlock.mineBlock(prefix);
        Block newBlock = new Block(lastBlock.getHash(), blockContent, new Date().getTime());
        if(!checkBlockChainValidity() || (accessChain.containsKey(code) && code.split("_")[0].equals("p"))){
            throw new Exception("Block chain Error");
        }
        ArrayList<String> x = new ArrayList<String>();
        if(code.split("_")[0].equals("v")){
          x = accessChain.get(code)!=null?accessChain.get(code): x;
        }
        x.add(newBlock.getHash());
        accessChain.put(code,x);
        blocks.add(newBlock);
    }
    public Block retrievePatientBlock(int index){
        String hash = accessChain.get("p_"+index).get(0);
        Block retrievedBlock = null;
        for (Block block : blocks){
            if(block.getHash().equals(hash)){
                retrievedBlock = block;
                break;
            }
        }
        return retrievedBlock;
    }
    public ArrayList<Block> retrieveAllPatientVisits(int index){
        ArrayList<String> hashs =  accessChain.get("v_"+index);
        ArrayList<Block> retrievedBlocks = new ArrayList<Block>();
        for (Block block : blocks){
            if(hashs.contains(block.getHash())){
                retrievedBlocks.add(block);

            }
        }
        return retrievedBlocks;

    }
//public String toString(){
//    StringBuilder b = new StringBuilder();
//    for(Block block :blocks){
////        System.out.println(block.getBlockContent());
//        b.append(block.getBlockContent());
//        b.append('\n');
//        b.append("_________________________________________");
//    }
//    System.out.println(b);
//    return  b.toString();
//}
}
