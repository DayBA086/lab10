package btree;

public class ItemNoFound extends RuntimeException {
    public ItemNoFound(String message) {
        super(message);
    }
}
