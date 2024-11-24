import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String, TransactionOutputs> UTXSOs = new HashMap<>();
    public static int minimumTransaction = 1;
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;
    public static void main(String[] args){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);
        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());

    }
    public static boolean isChainValid(){
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i = 1; i < blockchain.size(); i++){
            Block prev = blockchain.get(i-1);
            Block curr = blockchain.get(i);
            //validità blocco
            if(!curr.hash.equals(curr.calculateHash())){
                System.out.println("Current hashes not equal");
                return false;
            }
            //validità catena
            if(!curr.previousHash.equals(prev.hash)){
                System.out.println("Previous hashes not equal");
                return false;
            }
            //validità mining (controlliamo che l'hash abbia le prime "difficulty" cifre a 0
            if(!curr.hash.substring(0, difficulty).equals(hashTarget)){
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
