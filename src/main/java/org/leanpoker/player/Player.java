package org.leanpoker.player;

import com.google.gson.JsonElement;

public class Player {

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = parser.parse(request);
        int f1 = jsonObject.get("current_buy_in").getAsInt();
        System.out.println(f1);

        return f1;
    }

    public static void showdown(JsonElement game) {
    }
}
