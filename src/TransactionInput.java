public class TransactionInput {
    public String transactionOutputId;
    public TransactionOutputs UTXO; //Unspent transaction output

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
