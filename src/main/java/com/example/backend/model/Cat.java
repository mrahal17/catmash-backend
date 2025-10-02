public class Cat {

    private String id;
    private String imageUrl;
    private int numberOfVotes = 0;

    public Cat(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return this.id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public int getNumberOfVotes() {
        return this.numberOfVotes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}