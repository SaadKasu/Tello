package Controllers;

import DTOs.BoardRequestDTO;
import DTOs.BoardResponseDTO;
import Services.BoardService;

import java.util.List;

public class BoardController {
    public BoardResponseDTO createBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.createBoard(requestDTO);
        return responseDTO;
    }

    public BoardResponseDTO setBoardPrivacy(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.setBoardPrivacy(requestDTO);
        return responseDTO;
    }
    public BoardResponseDTO deleteBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.deleteBoard(requestDTO);
        return responseDTO;
    }

    public BoardResponseDTO setBoardName(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.setBoardName(requestDTO);
        return responseDTO;
    }
    public BoardResponseDTO showBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.showBoard(requestDTO);
        return responseDTO;
    }
    public BoardResponseDTO setBoardURL(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.setBoardURL(requestDTO);
        return responseDTO;
    }

    public BoardResponseDTO addUserToMember(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.addUserToBoard(requestDTO);
        return responseDTO;
    }
    public BoardResponseDTO removeUserFromMember(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.removeUserFromBoard(requestDTO);
        return responseDTO;
    }

    public BoardResponseDTO addListToBoardList(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.addBoardListToBoard(requestDTO);
        return responseDTO;
    }
    public BoardResponseDTO removeListFromBoardList(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = BoardService.removeBoardListFromBoard(requestDTO);
        return responseDTO;
    }
    public List<BoardResponseDTO> showAllBoards(BoardRequestDTO requestDTO){
        List<BoardResponseDTO> responseDTOS = BoardService.showAllBoards(requestDTO);
        return responseDTOS;
    }
}
