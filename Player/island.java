package Player;

import Grids.Coordinates;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class island {

    List<String> itemsAvailable = new ArrayList<>();

    public player player;

    private Coordinates position;

    public String island_type; // "MATH", "WORD", "GEO" OR "LUCK" ONLY

//    public boolean visited = false;

    public int visited_count = 0;

    public island(){
        ArrayList<String> a = new ArrayList<String>();
        Random random = new Random();
        a.add("MATH");
        a.add("WORD");
        a.add("GEO");
        a.add("LUCK");
        island_type = a.get(random.nextInt(4));
    }


}

