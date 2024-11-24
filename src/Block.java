import java.util.Date;
import java.security.MessageDigest;
public class Block {
    public String hash;
    public String previousHash;
    private String data; //il messaggio
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }
    //calcola l'hash dell'intero blocco
    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + nonce + data
        );
    }
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined: " + hash);
    }
}
