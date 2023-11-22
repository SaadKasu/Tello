package Services;

import DTOs.*;
import Models.*;
import Repositories.BoardListRepository;
import Repositories.BoardRepository;
import Repositories.CardRepository;
import Repositories.UserRepository;

import java.util.List;

public class CardService {

    public static CardResponseDTO createCard(CardRequestDTO requestDTO){
        String boardListId = requestDTO.boardListId;
        String cardName = requestDTO.name;
        Card card = new Card();
        card.setName(cardName);
        card.setDescription(requestDTO.description);
        CardResponseDTO responseDTO = new CardResponseDTO();

        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.boardListFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        BoardList boardList = searchBoardListDTO.boardList;
        CardRepository.addCard(card.getId(),card);
        boardList.getCards().add(card);
        card.setBoardList(boardList);
        responseDTO.cardId = card.getId();
        responseDTO.cardName = card.getName();
        responseDTO.addOperation = AddOperation.SUCCESSFUL;
        return responseDTO;
    }


    public static CardResponseDTO setCardName(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        String cardName = requestDTO.name;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        Card card = searchCardDTO.card;

        card.setName(cardName);
        responseDTO.cardId = card.getId();

        return responseDTO;
    }

    public static CardResponseDTO setCardDescription(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        String cardDescription = requestDTO.description;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        Card card = searchCardDTO.card;

        card.setDescription(cardDescription);
        responseDTO.cardId = card.getId();

        return responseDTO;
    }

    public static CardResponseDTO assignUserToCard(CardRequestDTO requestDTO) {
        String cardId = requestDTO.cardId;
        String userId = requestDTO.userId;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND) {
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        SearchUserDTO searchUserDTO = UserRepository.searchUserById(userId);
        if (searchUserDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.userFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.userFindOperation = FindOperation.FOUND;

        Card card = searchCardDTO.card;
        User user = searchUserDTO.user;

        card.setUser(user);

        responseDTO.userId = user.getId();
        responseDTO.cardId = card.getId();
        responseDTO.assign_unassign_operation = Assign_Unassign_Operation.SUCCESSFUL;
        return responseDTO;

    }

    public static CardResponseDTO unAssignUserToCard(CardRequestDTO requestDTO) {
        String cardId = requestDTO.cardId;
        String userId = requestDTO.userId;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND) {
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        SearchUserDTO searchUserDTO = UserRepository.searchUserById(userId);
        if (searchUserDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.userFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.userFindOperation = FindOperation.FOUND;

        Card card = searchCardDTO.card;
        User user = searchUserDTO.user;

        if (!card.getUser().getId().equals(userId)){
            responseDTO.assign_unassign_operation = Assign_Unassign_Operation.FAILED;
            return responseDTO;
        }
        responseDTO.userId = user.getId();
        responseDTO.cardId = card.getId();
        responseDTO.assign_unassign_operation = Assign_Unassign_Operation.SUCCESSFUL;
        return responseDTO;

    }

    public static CardResponseDTO deleteCard(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;

        Card card = searchCardDTO.card;

        DeleteCardDTO deleteCardDTO = CardRepository.deleteCard(cardId);
        if (deleteCardDTO.deleteOperation == DeleteOperation.SUCCESSFUL){
            responseDTO.deleteOperation = deleteCardDTO.deleteOperation;
            responseDTO.cardId = cardId;
            return responseDTO;
        }
        responseDTO.deleteOperation = DeleteOperation.FAILED;
        return responseDTO;
    }

    public static CardResponseDTO showCard(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        Card card = searchCardDTO.card;
        responseDTO.cardId = card.getId();
        responseDTO.description = card.getDescription();
        responseDTO.userName = card.getUser() == null ? "No User Assigned" : card.getUser().getName();
        responseDTO.cardName = card.getName();
        return responseDTO;
    }

    public static CardResponseDTO moveCardFromOneListToOther(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        String toBoardListId = requestDTO.toBoardListId;
        CardResponseDTO responseDTO = new CardResponseDTO();
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);

        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;

        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(toBoardListId);
        if (searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.toBoardListFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.toBoardListFindOperation = FindOperation.FOUND;
        Card card = searchCardDTO.card;
        BoardList toBoardList = searchBoardListDTO.boardList;
        BoardList fromBoardList = card.getBoardList();

        for (Card crd : toBoardList.getCards()){
            if (crd.getId().equals(card.getId())){
                responseDTO.moveOperation = MoveOperation.ALREADY_IN_LIST;
                return responseDTO;
            }
        }
        for (Card crd : fromBoardList.getCards()){
            if (crd.getId().equals(card.getId())){
                fromBoardList.getCards().remove(card);
                break;
            }
        }
        toBoardList.getCards().add(card);
        responseDTO.moveOperation = MoveOperation.SUCCESSFUL;
        return responseDTO;
    }

    public static CardResponseDTO removeCardFromBoardList(CardRequestDTO requestDTO){
        String cardId = requestDTO.cardId;
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);
        CardResponseDTO responseDTO = new CardResponseDTO();
        if (searchCardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        Card card = searchCardDTO.card;
        BoardList boardList = card.getBoardList();

        List<Card> cardsInBoardList = boardList.getCards();
        boolean cardListRemoved = cardsInBoardList.remove(card);

        if (cardListRemoved){
            responseDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
            return responseDTO;
        }
        responseDTO.deleteOperation = DeleteOperation.FAILED;
        return responseDTO;
    }
}
