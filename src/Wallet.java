import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public Wallet(){
        generateKeyPair();
    }
    public void generateKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            //initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random); //256 bytes
            KeyPair keyPair = keyGen.generateKeyPair();
            //Set the public and private key from the keyPair
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
