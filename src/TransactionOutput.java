import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient; //il nuovo possessore
    public float value; //the amount of coins they own
    public String parentTransactionId; //the id of the transaction this output was created in

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient));
    }

    //Controlla se il coin appartiene a public key
    public boolean isMine(PublicKey publicKey) {
        return(publicKey.equals(reciepient));
    }
}
