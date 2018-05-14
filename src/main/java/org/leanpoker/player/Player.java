package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.*;
import java.util.List;
import java.util.Arrays;

import java.util.ArrayList;

public class Player {

    static String[] highCards = {"7", "8", "9", "10", "J", "Q", "K", "A"};

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        System.out.println(request);
        JsonObject cuccok = request.getAsJsonObject();
        JsonElement buyInJson = cuccok.get("current_buy_in");
        JsonElement inActionJson = cuccok.get("in_action");
        int inAction = inActionJson.getAsInt();
        int buyIn = buyInJson.getAsInt();
        JsonElement players = cuccok.get("players");
        JsonArray playersArray = players.getAsJsonArray();

        ArrayList<String> ranksNeeded = new ArrayList<String>();

        for (JsonElement element : playersArray) {
            JsonObject object = element.getAsJsonObject();
            if (object.get("id").getAsInt() == inAction) {
                JsonElement holeCards = object.get("hole_cards");
                JsonArray myHoleArray = holeCards.getAsJsonArray();

                for(JsonElement ranks: myHoleArray){
                    JsonObject rankObject = ranks.getAsJsonObject();
                    ranksNeeded.add(rankObject.get("rank").toString());
                    System.out.println("rank: "
                            + ranksNeeded.get(ranksNeeded.size()-1));
                }
                System.out.println("PLS WORKKKKK!!!");
                System.out.println(myHoleArray);
            }

        }

        System.out.println("FUCK PLAYERS:" + playersArray);
        System.out.println("OUR PLAYERS ID:" + inAction);

        JsonElement commonCards = cuccok.get("community_cards");
        JsonArray commonCardsArray = commonCards.getAsJsonArray();
        System.out.println("Common cards:");
        for (JsonElement commonCard: commonCardsArray) {
            System.out.println(commonCard);
        }
        buyIn = holdingCards(ranksNeeded, buyIn);
        return buyIn;
    }

    public static void showdown(JsonElement game) {
    }

    public static int holdingCards (ArrayList<String> holdingCards, int buyIn) {
        System.out.println("TWOCARDS");
        if (Arrays.asList(highCards).contains(holdingCards.get(0)) && Arrays.asList(highCards).contains(holdingCards.get(1))) {
            System.out.println("Yes");
            return buyIn * 2;
        }
        else {
            System.out.println("LOW");
            return buyIn;
        }
    }


}
