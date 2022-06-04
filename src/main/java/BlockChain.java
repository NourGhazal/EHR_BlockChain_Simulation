import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {
private ArrayList<Block> blocks= new ArrayList<Block>();
private HashMap<String,ArrayList<String>> accessChain = new HashMap<String,ArrayList<String>>();

private boolean checkBlockChainValidity(){
    for(int i=0;i<blocks.size()-1;i++){
        if(!(blocks.get(i).getBlockHash().equals(blocks.get(i+1).getPreviousBlockHash()))){
            return false;
        }
    }
    return true;
}
public void addBlockToChain(String blockContent,String code) throws Exception {
    String previousBlockHash = blocks.size()>0? blocks.get(blocks.size()-1).getBlockHash() : null;
    Block newBlock = new Block(previousBlockHash,blockContent);
    if(!checkBlockChainValidity() || (accessChain.containsKey(code) && code.split("_").equals("p"))){
        throw new Exception("Block chain Error");
    }
    ArrayList<String> x = new ArrayList<String>();
    if(code.split("_")[0].equals("v")){
      x = accessChain.get(code)!=null?accessChain.get(code): x;
    }
    x.add(newBlock.getBlockHash());
    accessChain.put(code,x);
    blocks.add(newBlock);


}
public Block retrievePatientBlock(int index){
    String hash = accessChain.get("p_"+index).get(0);
    Block retrievedBlock = null;
    for (Block block : blocks){
        if(block.getBlockHash().equals(hash)){
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
        if(hashs.contains(block.getBlockHash())){
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
