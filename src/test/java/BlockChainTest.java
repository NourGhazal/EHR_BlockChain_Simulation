import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

    @Test
    public void addNewBlockTest() throws Exception {
        BlockChain blockChain = new BlockChain();
        assertEquals(1, BlockChain.blocks.size());
        blockChain.addBlockToChain("Patient's data", "Doctor");
        assertEquals(2, BlockChain.blocks.size());
    }

}