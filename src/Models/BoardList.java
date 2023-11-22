package Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BoardList extends BaseClass{
    private String name;
    private List<Card> cards;

    private Board board;

    public BoardList(){
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getId(){
        return this.id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
