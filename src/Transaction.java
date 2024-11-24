
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.ArrayList;

public class Transaction {
    public String transactionId;
    public PublicKey sender;
    public PublicKey reciepient;
    public float value;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    private static int sequence = 0; //verrà aggiunto alle transazioni

    public Transaction(PublicKey from,PublicKey to, float value, ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash(){
        sequence++; //Per evitare che transazioni identiche abbiano lo stesso hash
        return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + value + sequence);

    }
    public void generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + value;
        signature = StringUtil.applyECDSASig(privateKey, data);
    }
    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + value;
        return StringUtil.verifyECDSASig(sender,data,signature);
    }
    public boolean processTransaction(){
        if(verifySignature() == false){
            System.out.println("#Transaction Signature failed to verify");
        }
        //Transaction inputs
        for(TransactionInput input : inputs){
            input.UTXO = BlockChain.UTXOs.get(input.transactionOutputId);
        }
        //controllo se a transazione è valida
        if(getInputsValue() < BlockChain.minimumTransaction){
            System.out.println("#Transaction value too small " + getInputsValue());
            return false;
        }
        //genereo output
        float leftOver = getInputsValue() - value; //Calcolo del resto
        transactionId = calculateHash();
        outputs.add(new TransactionOutput(this.reciepient,value,transactionId));
        outputs.add(new TransactionOutput(this.sender,leftOver,transactionId));

        //add outputs to unspent list
        for(TransactionOutput o : outputs){
            BlockChain.UTXOs.put(o.id,o);
        }
        //rimuovo gli input dalle UTXO
        for(TransactionInput i : inputs){
            if(i.UTXO == null)continue;
            BlockChain.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }
    //Somma degli input
    public float getInputsValue(){
        float total = 0;
        for(TransactionInput i : inputs){
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }
    //Somma degli output
    public float getOutputsValue(){
        float total = 0;
        for(TransactionOutput o : outputs){
            total += o.value;
        }
        return total;
    }
}

