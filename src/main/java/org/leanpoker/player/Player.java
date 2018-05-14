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
        try {
            for (JsonElement element : playersArray) {
                JsonObject object = element.getAsJsonObject();
                if (object.get("id").getAsInt() == inAction) {
                    JsonElement holeCards = object.get("hole_cards");
                    JsonElement betJson = object.get("bet");
                    int bet = betJson.getAsInt();
                    moneyToBet -= bet;
                    JsonArray myHoleArray = holeCards.getAsJsonArray();

                    for (JsonElement ranks : myHoleArray) {
                        JsonObject rankObject = ranks.getAsJsonObject();
                        cardsInHand.add(rankObject.get("rank").toString());
                        System.out.println("rank: "
                                + cardsInHand.get(cardsInHand.size() - 1));
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("ERROR!!!");
        }

        //Community card section:
        ArrayList<String> commRanks = new ArrayList<String>();
        ArrayList<String> commColors = new ArrayList<String>();
        boolean commIsEmpty = true;

        JsonElement commonCards = cuccok.get("community_cards");
        JsonArray commonCardsArray = commonCards.getAsJsonArray();
        System.out.println("Common cards:");
        JsonArray commonArray = commonCards.getAsJsonArray();
        try{
            for (JsonElement commonCard: commonCardsArray) {
                System.out.println(commonCard);
                for (JsonElement ranks : commonArray) {
                    //rank:
                    JsonObject rankCommonCard = ranks.getAsJsonObject();
                    commRanks.add(rankCommonCard.get("rank").toString());
                    System.out.println("Common rank: "
                            + commRanks.get(commRanks.size() - 1));
                    //color:
                    commColors.add(rankCommonCard.get("suit").toString());
                    System.out.println("Common suit: "
                            + commColors.get(commColors.size() - 1));
                }
            }
            System.out.println("Itten nezzed");

        }catch(Exception ex){
            System.out.println("ANOTHER ERROR!!");
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
        String firstC = "" + holdingCards.get(0);
        String secondC = "" + holdingCards.get(1);
        System.out.println("TWOCARDS");
        System.out.println(holdingCards);
        System.out.println(firstC);
        System.out.println(secondC);

        if (firstC.contentEquals("7") || firstC.contentEquals("8") || firstC.contentEquals("9") || firstC.contentEquals("A") || firstC.contentEquals("K") || firstC.contentEquals("J") || firstC.contentEquals("Q") || firstC.contentEquals("10")) {
            System.out.println("ITT");
            if (secondC.equals("7") || secondC.equals("8") || secondC.equals("9") || secondC.equals("A") || secondC.equals("K") || secondC.equals("J") || secondC.equals("Q") || secondC.equals("10")) {
                System.out.println("HIGHCARDS");
                if (holdingCards.get(0).equals(holdingCards.get(1))) {
                    System.out.println("EQAULED CARDS");
                    return buyIn * 3;
                }
                return buyIn * 2;
            }
            else {
                System.out.println("LOW");
                return buyIn / 2;
            }
        }
        System.out.println("BASZIK MUKODNI");
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
            buyInt = buyInt;
        }
        return buyInt;
    }

    private static boolean isDrill(ArrayList<String> cardsInHand, ArrayList<String> commRanks) {
        int counter = 0;
        int iteration = 0;
        String firstCard = "asd";
        for (String cardRank : cardsInHand) {
            if (iteration == 0) {
                firstCard = cardRank;
            }
            if (iteration > 0) {
                if (firstCard.equals(cardRank)) {
                    counter = 1;
                }
            }
        }

        for (String handRank : cardsInHand) {
            for (String commRank : commRanks) {
                if (handRank.equals(commRank)) {
                    counter++;
                }
            }
        }
        if (counter == 2) {
            return true;
        }
        return false;
    }

}

