package Controllers;


import DTOs.BoardListRequestDTO;
import DTOs.BoardListResponseDTO;
import Models.FindOperation;
import Services.BoardListService;

public class BoardListController {
    public BoardListResponseDTO createBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.createBoardList(requestDTO);
        return responseDTO;
    }
    public BoardListResponseDTO deleteBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.deleteBoardList(requestDTO);
        if (responseDTO.findOperation == FindOperation.FOUND){
            responseDTO = BoardListService.removeBoardListFromBoard(requestDTO);
        }
        return responseDTO;
    }

    public BoardListResponseDTO setBoardListName(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.setBoardListName(requestDTO);
        return responseDTO;
    }
    public BoardListResponseDTO addCardToBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.addCardToBoardList(requestDTO);
        return responseDTO;
    }
    public BoardListResponseDTO removeCardFromBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.removeCardFromBoardList(requestDTO);
        return responseDTO;
    }
    public BoardListResponseDTO showBoardList(BoardListRequestDTO requestDTO){
        BoardListResponseDTO responseDTO = BoardListService.showBoardList(requestDTO);
        return responseDTO;
    }
}
