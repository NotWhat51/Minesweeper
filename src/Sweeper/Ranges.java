package Sweeper;

import java.util.ArrayList;
import java.util.*;

public class Ranges {
    public static Coord getSize() {
        return size;
    }

    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

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

    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
    }

    static ArrayList<Coord> getCoordsAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<Coord>();
        for (int i = coord.x - 1; i <= coord.x + 1; i++)
            for (int j = coord.y - 1; j <= coord.y + 1; j++)
                if (inRange(around = new Coord(i, j)))
                    if (!around.equals(coord))
                        list.add(around);
        return list;
    }
}
