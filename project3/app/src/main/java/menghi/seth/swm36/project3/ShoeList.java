package menghi.seth.swm36.project3;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShoeList {

    String jsonShoeString;
    List<Shoe> shoes = new ArrayList<Shoe>();

    /**
     * Creates a list for shows accessible with
     * getShoeList(), which returns an ArrayList of shoes
     *
     * @param jsonString
     */
    public ShoeList(String jsonString){
        jsonShoeString = jsonString;
        loadShoesFromString();
    }

    /**
     * Loads shoe from filename
     * @param filename
     */
    public void loadShoesFromFile(String filename){
        InputStream is = MainActivity.class.getResourceAsStream("shoes.json");
        try {
            jsonShoeString = IOUtils.toString(is);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        loadShoesFromString();
    }
    /**
     * Loads shoes from json string
     */
    public void loadShoesFromString(){
        // Load JSON of shoes, then use that for searches and display

        // Use the jsonArray obj to load all the shoes
        try {
            // Reset shoes array
            shoes = new ArrayList<Shoe>();
            // Load data into JSON array
            JSONArray jsonShoes = new JSONArray(jsonShoeString);
            String name;
            String color;
            String image;
            int price;

            for (int i = 0; i < jsonShoes.length(); i++) {
                JSONObject jsonShoe = jsonShoes.getJSONObject(i);
                // Create Shoe object
                name = jsonShoe.getString("name");
                color = jsonShoe.getString("color");
                // Turn image resource string into a usable resource
                image = jsonShoe.getString("image");
                price = jsonShoe.getInt("price");
                // Add into shoeList
                Shoe shoe = new Shoe(name, color, image, price);
                shoes.add(shoe);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches through shoe names to find the shoe.name,
     * which contains the String query.
     *
     * @param query
     * @return
     */
    public List<Shoe> searchName(String query){
        List<Shoe> found = new ArrayList<Shoe>();
        for (int i = 0; i < shoes.size(); i++) {
            Shoe currentShoe = shoes.get(i);
            // FOUND! add to list, make sure all lowercase
            if (currentShoe.getName().toLowerCase().contains(query.toLowerCase())) {
                found.add(shoes.get(i));
            }
        }

        return found;
    }

    public List<Shoe> getShoeList(){
        return shoes;
    }
}
