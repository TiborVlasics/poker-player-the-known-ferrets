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
        JsonElement buyInJson = cuccok.get("current_buy_in");
        int buyIn = buyInJson.getAsInt();
        JsonElement players = cuccok.get("players");


        System.out.println("FUCK PLAYERS:" + players);

        return buyIn;
    }

    public static void showdown(JsonElement game) {
    }
}
