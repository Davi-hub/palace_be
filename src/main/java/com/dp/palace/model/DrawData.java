package com.dp.palace.model;

import com.dp.palace.domain.Card;
import com.dp.palace.model.Ids;

import java.util.ArrayList;
import java.util.Optional;

public class DrawData {
    private ArrayList<Card> cards;
    private Ids ids;
    private Optional<Integer> onTableIndex;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    
    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public Optional<Integer> getOnTableIndex() {
        return onTableIndex;
    }

    public void setOnTableIndex(Optional<Integer> onTableIndex) {
        this.onTableIndex = onTableIndex;
    }
}
