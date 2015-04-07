package menghi.seth.swm36.project3;

public class Shoe {

    String name; // name of shoe
    String color; // color of shoe
    String image; // img resource as string
    Integer price; // price of shoe

    /**
     * Shoe object for project3
     *
     * Name, color, and price are all simple strings/ints
     *
     * Image is a image resource: R.drawable.IMAGE_NAME, so
     * it needs to be converted to an imgResource which is an int via:
     *   int imageResource = getResources().getIdentifier(jsonShoe.getString("image"),
     *   "drawable", getPackageName());
     *
     * @param name
     * @param color
     * @param image
     * @param price
     */
    public Shoe(String name, String color,
                String image, int price){
        this.name = name;
        this.color = color;
        this.image = image;
        this.price = price;
    }

    public String getName(){
        return name;
    }
}
