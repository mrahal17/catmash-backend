public class Cat {

    private String id;
    private String imageUrl;
    private int numberOfVotes = 0;

    public Cat(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public getId() {
        return this.id;
    }

    public getImageUrl() {
        return this.imageUrl;
    }

    public getNumberOfVotes() {
        return this.numberOfVotes;
    }

    public setId(String id) {
        this.id = id;
    }

    public setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}