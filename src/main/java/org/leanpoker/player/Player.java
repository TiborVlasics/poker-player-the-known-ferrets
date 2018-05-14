package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.Arrays;

import java.util.ArrayList;

public class Player {

    public static List<String> highCards = new ArrayList<String>();

    public static void addHighCards(){
        highCards.add("7");
        highCards.add("8");
        highCards.add("9");
        highCards.add("10");
        highCards.add("J");
        highCards.add("Q");
        highCards.add("K");
        highCards.add("A");
    }

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        int moneyToBet = 0;
        System.out.println(request);
        JsonObject cuccok = request.getAsJsonObject();
        JsonElement buyInJson = cuccok.get("current_buy_in");
        JsonElement inActionJson = cuccok.get("in_action");
        int inAction = inActionJson.getAsInt();
        int buyIn = buyInJson.getAsInt();
        JsonElement players = cuccok.get("players");
        JsonArray playersArray = players.getAsJsonArray();
        moneyToBet = buyIn;

        ArrayList<String> cardsInHand = new ArrayList<String>();

        for (JsonElement element : playersArray) {
            JsonObject object = element.getAsJsonObject();
            if (object.get("id").getAsInt() == inAction) {
                JsonElement holeCards = object.get("hole_cards");
                JsonElement betJson = object.get("bet");
                int bet  = betJson.getAsInt();
                moneyToBet -= bet;
                JsonArray myHoleArray = holeCards.getAsJsonArray();

                for(JsonElement ranks: myHoleArray){
                    JsonObject rankObject = ranks.getAsJsonObject();
                    cardsInHand.add(rankObject.get("rank").toString());
                    System.out.println("rank: "
                            + cardsInHand.get(cardsInHand.size()-1));
                }
            }
        }

        //Community card section:
        ArrayList<String> commRanks = new ArrayList<String>();
        ArrayList<String> commColors = new ArrayList<String>();
        boolean commIsEmpty = true;

        JsonElement commonCards = cuccok.get("community_cards");
        JsonArray commonCardsArray = commonCards.getAsJsonArray();
        System.out.println("Common cards:");
        JsonArray commonArray = commonCards.getAsJsonArray();
        for (JsonElement commonCard: commonCardsArray) {
            System.out.println(commonCard);
            for (JsonElement ranks: commonArray){
                //rank:
                JsonObject rankCommonCard = ranks.getAsJsonObject();
                commRanks.add(rankCommonCard.get("rank").toString());
                System.out.println("Common rank: "
                        + commRanks.get(commRanks.size()-1));
                //color:
                commColors.add(rankCommonCard.get("suit").toString());
                System.out.println("Common suit: "
                        + commColors.get(commColors.size()-1));
            }
            System.out.println("Itten nezzed");

        }
        if (commRanks.size() > 0) commIsEmpty = false;
        System.out.println("Community cards is empty: " + commIsEmpty);

        if (commIsEmpty) {
            moneyToBet= holdingCards(cardsInHand, moneyToBet);
        } else {
            moneyToBet= act(commRanks, cardsInHand, moneyToBet);
        }

        return moneyToBet;
    }

    public static void showdown(JsonElement game) {
    }

    public static int holdingCards (ArrayList<String> holdingCards, int buyIn) {
        addHighCards();
        String firstC = holdingCards.get(0);
        String secondC = holdingCards.get(1);
        System.out.println("TWOCARDS");
        System.out.println(holdingCards);

        if (firstC.equals("7") || firstC.equals("8") || firstC.equals("9") || firstC.equals("A") || firstC.equals("K") || firstC.equals("J") || firstC.equals("Q")) {
            if (secondC.equals("7") || secondC.equals("8") || secondC.equals("9") || secondC.equals("A") || secondC.equals("K") || secondC.equals("J") || secondC.equals("Q")) {
                System.out.println("HIGHCARDS");
                if (holdingCards.get(0).equals(holdingCards.get(1))) {
                    System.out.println("EQAULED CARDS");
                    return buyIn * 3;
                }
                return buyIn * 2;
            } else {
                System.out.println("LOW");
                return buyIn / 2;
            }
        }
        return 0;
    }

    private static int act(ArrayList < String > commRanks, ArrayList < String > myHole,int buyInt) {
        int sameCards = 0;
        for (String myCard : myHole) {
            for (String community : commRanks) {
                if (myCard.equals(community)) {
                    sameCards += 1;
                }
            }
        }
        boolean buyIntBiggerThan300 = buyInt > 300;
        if (sameCards == 2) {
            buyInt *= 2;
        } else if (sameCards == 3) {
            buyInt *= 3;
        } else if (sameCards == 4) {
            buyInt *= 4;
        } else {
            buyInt = 0;
        }
        return buyInt;
    }

}

