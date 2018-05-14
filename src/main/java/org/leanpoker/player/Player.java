package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        System.out.println(request);
        JsonObject cuccok = request.getAsJsonObject();
        JsonElement buyIn = cuccok.get("current_buy_in");

        System.out.println("FUCK Buy in:" + buyIn.toString());
        System.out.println("hello");

        return 1000;
    }

    public static void showdown(JsonElement game) {
    }
}
