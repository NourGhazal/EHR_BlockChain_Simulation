import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashingTest {
    String data = "Doctor visit 2020";

    @Test
    public void generateHashTest(){
        String hash = Hash.sha256(data);
        assertEquals(64, hash.length());
        assertEquals("d522e92e1d0e239695e69ecd1ab0d982b1c8fe75b2be74b3ca402197c4c1e519", hash);
    }

    @Test
    public void generateHashByteArray(){
        String hash = Hash.sha256(data);
        byte[] hashByte = Hash.sha256Byte(data);
        assertArrayEquals(hashByte, Hash.decodeHexString(hash));
    }
}