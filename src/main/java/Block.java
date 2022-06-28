public class Block {
    private final String previousBlockHash;
    private final String blockContent;
    private final long timeStamp;

    private String hash;
    private int nonce;

    public Block(String previousBlockHash, String blockContent, long timeStamp) {
        this.blockContent = blockContent;
        this.previousBlockHash = previousBlockHash;
        this.timeStamp = timeStamp;
        this.hash = calculateHash(this);
    }
    public static String calculateHash(Block block){
        String dataToHash = block.previousBlockHash + Long.toString(block.timeStamp)
                + Integer.toString(block.nonce) + block.blockContent;
        return Hash.sha256(dataToHash);
    }

    public void mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix)
                .equals(prefixString)) {
            nonce++;
            hash = calculateHash(this);
        }
    }
    public String getPreviousBlockHash() {
        return previousBlockHash;
    }
    public String getBlockContent() {
        return blockContent;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "previousBlockHash='" + previousBlockHash + '\'' +
                ", blockContent='" + blockContent + '\'' +
                ", timeStamp=" + timeStamp +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
