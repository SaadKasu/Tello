package Services;


import DTOs.*;
import Models.*;
import Repositories.*;

import java.util.List;

public class BoardListService {
    public static BoardListResponseDTO createBoardList(BoardListRequestDTO requestDTO){
        String boardId = requestDTO.boardId;
        String listName = requestDTO.name;
        BoardList boardList = new BoardList();
        boardList.setName(listName);
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();

        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.boardFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        BoardListRepository.addBoardList(boardList.getId(),boardList);
        board.getLists().add(boardList);
        boardList.setBoard(board);
        responseDTO.boardListId = boardList.getId();
        responseDTO.boardListName = boardList.getName();
        responseDTO.addOperation = AddOperation.SUCCESSFUL;
        return responseDTO;
    }


    public static BoardListResponseDTO deleteBoardList(BoardListRequestDTO requestDTO){
        String boardListId = requestDTO.id;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();
        if (searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            responseDTO.deleteOperation = DeleteOperation.FAILED;
            return responseDTO;
        }
        BoardList boardlist = searchBoardListDTO.boardList;
        for (Card card : boardlist.getCards()) {
            CardRequestDTO cardRequestDTO = new CardRequestDTO();
            cardRequestDTO.cardId = card.getId();
            CardResponseDTO cardResponseDTO = CardService.deleteCard(cardRequestDTO);
            responseDTO.cardResponseDTOList.add(cardResponseDTO);
        }
        DeleteBoardListDTO deleteBoardListDTO = BoardListRepository.deleteBoardList(boardListId);
        responseDTO.deleteOperation = deleteBoardListDTO.deleteOperation;
        return responseDTO;
    }

    public static BoardListResponseDTO showBoardList(BoardListRequestDTO requestDTO){
        String boardListId = requestDTO.id;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();
        if (searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        BoardList boardList = searchBoardListDTO.boardList;
        for (Card card: boardList.getCards()){
            CardRequestDTO cardRequestDTO = new CardRequestDTO();
            cardRequestDTO.cardId = card.getId();
            CardResponseDTO cardResponseDTO = CardService.showCard(cardRequestDTO);
            responseDTO.cardResponseDTOList.add(cardResponseDTO);
        }
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.boardListId = boardList.getId();
        responseDTO.boardListName = boardList.getName();

        return responseDTO;
    }

    public static BoardListResponseDTO setBoardListName(BoardListRequestDTO requestDTO){
        String boardListId = requestDTO.id;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();
        if (searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        BoardList boardList = searchBoardListDTO.boardList;
        boardList.setName(requestDTO.name);

        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.boardListId = boardList.getId();
        return responseDTO;
    }

    public static BoardListResponseDTO addCardToBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();
        String boardListId = requestDTO.id;
        String cardId = requestDTO.cardId;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }

        responseDTO.findOperation = FindOperation.FOUND;
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.cardFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.cardFindOperation = FindOperation.FOUND;

        BoardList boardList = searchBoardListDTO.boardList;
        Card card = searchCardDTO.card;
        for (Card crd : boardList.getCards()){
            if (crd.getId().equals(card.getId())){
                responseDTO.addOperation = AddOperation.ALREADY_EXISTS;
                break;
            }
        }
        if (responseDTO.addOperation != AddOperation.ALREADY_EXISTS){
            boardList.getCards().add(card);
            responseDTO.addOperation = AddOperation.SUCCESSFUL;
            responseDTO.boardListId = boardListId;
        }

        return responseDTO;
    }

    public static BoardListResponseDTO removeCardFromBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();
        String boardListId = requestDTO.id;
        String cardId = requestDTO.cardId;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }

        responseDTO.findOperation = FindOperation.FOUND;
        SearchCardDTO searchCardDTO = CardRepository.searchCard(cardId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.cardFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.cardFindOperation = FindOperation.FOUND;

        BoardList boardList = searchBoardListDTO.boardList;
        Card card = searchCardDTO.card;
        for (Card crd : boardList.getCards()){
            if (crd.getId().equals(card.getId())){
                responseDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
                boardList.getCards().remove(card);
                break;
            }
        }
        if (responseDTO.deleteOperation != DeleteOperation.SUCCESSFUL){
            responseDTO.deleteOperation = DeleteOperation.FAILED;
        }

        return responseDTO;
    }

    public static BoardListResponseDTO removeBoardListFromBoard(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = new BoardListResponseDTO();

        String boardListId = requestDTO.id;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;

        BoardList boardList = searchBoardListDTO.boardList;
        Board board = boardList.getBoard();

        List<BoardList> boardListsInBoard = board.getLists();

        boolean removedBoardList = boardListsInBoard.remove(boardList);

        if (removedBoardList){
            responseDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
            return responseDTO;
        }
        responseDTO.deleteOperation = DeleteOperation.FAILED;
        return responseDTO;
    }

}
