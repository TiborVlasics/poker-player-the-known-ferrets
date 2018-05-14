package org.leanpoker.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
//        JsonParser jsonParser = new JsonParser();
//        JsonElement jsonTree = jsonParser.parse(request);
//        int f1 = jsonTree.get("current_buy_in").getAsInt();
        System.out.println(request);
        int buyIn = request.getAsInt("current_buy_in");

        System.out.println("Buy in:" + buyIn);
        System.out.println("hello");

        return buyIn;
    }

    public static void showdown(JsonElement game) {
    }
}
