package Sweeper;

import java.util.ArrayList;

public class Ranges {
    public static Coord getSize() {
        return size;
    }

    private static Coord size;
    private static ArrayList<Coord> allCoords;

    public static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList<Coord>();
        for (int i = 0; i < size.x; i++)
            for (int j = 0; j < size.y; j++)
                allCoords.add(new Coord(i, j));
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    static boolean inRange (Coord coord) {
       return coord.x >= 0 && coord.x < size.x &&
               coord.y >= 0 && coord.y < size.y;
    }
}
