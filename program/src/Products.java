public class Products {
    private int Id;
    private int User_id;
    private String Title;
    private String Publisher;
    private int Price;

    public Products(int id, int user_id, String title, String publisher, int price){
        this.Id = id;
        this.User_id = user_id;
        this.Title = title;
        this.Publisher = publisher;
        this.Price = price;
    }
    public int getId(){return this.Id;}
    public int getUser_id(){
        return this.User_id;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getPublisher() {
        return this.Publisher;
    }

    public int getPrice() {
        return this.Price;
    }

    @Override
    public String toString(){
        return Id + User_id + Title + Publisher + Price;
    }
}

