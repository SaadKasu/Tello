package Repositories;

import DTOs.DeleteCardDTO;
import DTOs.SearchBoardDTO;
import DTOs.SearchCardDTO;
import Models.*;

import java.util.HashMap;

public class CardRepository {
    private static HashMap<String, Card> cardHashMap = new HashMap<>();

    public static AddOperation addCard(String id, Card card){
        if (cardHashMap.containsKey(id))
            return AddOperation.ALREADY_EXISTS;
        cardHashMap.put(id, card);
        return AddOperation.SUCCESSFUL;
    }

    public static SearchCardDTO searchCard(String id){
        SearchCardDTO searchCardDTO = new SearchCardDTO();
        if (cardHashMap.containsKey(id)){
            searchCardDTO.findOperation = FindOperation.FOUND;
            searchCardDTO.card = cardHashMap.get(id);
            return searchCardDTO;
        }
        searchCardDTO.findOperation = FindOperation.NOT_FOUND;
        return searchCardDTO;
    }

    public static DeleteCardDTO deleteCard(String id){
        DeleteCardDTO deleteCardDTO = new DeleteCardDTO();
        if (cardHashMap.containsKey(id)){
            deleteCardDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
            deleteCardDTO.card = cardHashMap.remove(id);
            return deleteCardDTO;
        }
        deleteCardDTO.deleteOperation = DeleteOperation.FAILED;
        return deleteCardDTO;
    }
}
