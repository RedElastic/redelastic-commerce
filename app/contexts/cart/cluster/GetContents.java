package contexts.cart.cluster;

public class GetContents implements CartMessage {
    private final String userId;

    public GetContents(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
