public class Block {
    private static int counter =0;



    private String previousBlockHash;
    private String blockHash;
    private int tuningParameter;

    public String getBlockContent() {
        return blockContent;
    }

    private String blockContent;

    public Block(String previousBlockHash, String blockContent) {
        if(counter>0){
            this.previousBlockHash = previousBlockHash;
        }
        else {
            this.previousBlockHash = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        this.blockContent = blockContent;
        this.tuningParameter = blockContent.length()>70?blockContent.length():260;
        int x = 0;
        for (char c : blockContent.toCharArray()){
            x += c;
        }
        x*=tuningParameter;
        StringBuilder b = new StringBuilder();
        for (char c : this.previousBlockHash.toCharArray()){
            b.append((c+x)+"");
        }
        this.blockHash = b.toString();
        counter++;
    }
    public String getPreviousBlockHash() {
        return previousBlockHash;
    }
    public String getBlockHash() {
        return blockHash;
    }
}
