public class TransactionInput {
    public String transactionOutputId;
    public TransactionOutput UTXO; //Unspent transaction output

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
