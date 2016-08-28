package com.example.bond.myapplication.sceneTouch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapScene {
    public ArrayList<ArrayList<Integer>> map;

    public MapScene() {
        map = createRandomMap();


    }

    private ArrayList<ArrayList<Integer>> createStaticMap() {

        ArrayList<ArrayList<Integer>> newMap = new ArrayList<>();


        newMap.add(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2)));
        newMap.add(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2)));
        newMap.add(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2)));
        newMap.add(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3)));
        newMap.add(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2)));
        newMap.add(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1)));
        return newMap;
    }

    private ArrayList<ArrayList<Integer>> createRandomMap() {
        ArrayList<ArrayList<Integer>> newMap = new ArrayList<>();
        Random random= new Random();
        for (int i = 0; i < 6; i++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            newMap.add(arrayList);

            for (int j = 0; j < 8; j++) {
                arrayList.add(random.nextInt(5));
            }
        }
        return newMap;

    }
}
