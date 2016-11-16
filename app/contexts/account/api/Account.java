package contexts.account.api;

public class Account {
    private final String userId;
    private final String name;
    private final String image;

    public Account(String userId, String name, String image) {
        this.userId = userId;
        this.name = name;
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getImageUri() {
        return image;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
