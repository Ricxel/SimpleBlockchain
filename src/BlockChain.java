import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class BlockChain {
    public static int difficulty = 5;
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static void main(String[] args){

        System.out.println("Target: " + new String(new char[difficulty]).replace('\0', '0'));
        blockchain.add(new Block("Hi im the first block", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);

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
