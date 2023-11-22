package Services;

import DTOs.*;
import Models.*;
import Repositories.BoardListRepository;
import Repositories.BoardRepository;
import Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class BoardService {
    public static BoardResponseDTO createBoard(BoardRequestDTO requestDTO){
        String nameOfBoard = requestDTO.name;
        Board board = new Board();
        board.setName(nameOfBoard);
        board.setUrl("");
        board.setPrivacy(requestDTO.privacy == null ? BoardPrivacy.PUBLIC : requestDTO.privacy);
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        responseDTO.id = board.getId();
        responseDTO.url = board.getUrl();
        responseDTO.name = board.getName();
        responseDTO.privacy = board.getPrivacy();
        responseDTO.addOperation = BoardRepository.addBoard(board.getId(),board);
        return responseDTO;
    }

    public static BoardResponseDTO setBoardPrivacy(BoardRequestDTO requestDTO){
        BoardPrivacy privacy = requestDTO.privacy;
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        board.setPrivacy(privacy);
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.id = board.getId();
        responseDTO.url = board.getUrl();
        responseDTO.name = board.getName();
        responseDTO.privacy = board.getPrivacy();
        return responseDTO;
    }

    public static BoardResponseDTO setBoardName(BoardRequestDTO requestDTO){
        String boardName = requestDTO.name;
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        board.setName(boardName);
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.id = board.getId();
        responseDTO.url = board.getUrl();
        responseDTO.name = board.getName();
        responseDTO.privacy = board.getPrivacy();
        return responseDTO;
    }

    public static BoardResponseDTO deleteBoard(BoardRequestDTO requestDTO){
        String boardId = requestDTO.id;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        if (searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            responseDTO.deleteOperation = DeleteOperation.FAILED;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        for (BoardList boardList : board.getLists()){
            BoardListRequestDTO boardListRequestDTO = new BoardListRequestDTO();
            boardListRequestDTO.id = boardList.getId();
            BoardListResponseDTO boardListResponseDTO = BoardListService.deleteBoardList(boardListRequestDTO);
            responseDTO.boardListResponseDTOS.add(boardListResponseDTO);
        }
        DeleteBoardDTO deleteBoardDTO = BoardRepository.deleteBoard(boardId);

        responseDTO.deleteOperation = deleteBoardDTO.deleteOperation;
        return responseDTO;
    }

    public static BoardResponseDTO showBoard(BoardRequestDTO requestDTO){
        String boardId = requestDTO.id;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        if (searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        for (BoardList boardList: board.getLists()){
            BoardListRequestDTO boardListRequestDTO = new BoardListRequestDTO();
            boardListRequestDTO.id = boardList.getId();
            BoardListResponseDTO boardListResponseDTO = BoardListService.showBoardList(boardListRequestDTO);
            responseDTO.boardListResponseDTOS.add(boardListResponseDTO);
        }
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.id = board.getId();
        responseDTO.url = board.getUrl();
        responseDTO.name = board.getName();
        responseDTO.privacy = board.getPrivacy();
        return responseDTO;
    }

    public static BoardResponseDTO setBoardURL(BoardRequestDTO requestDTO){
        String boardURL = requestDTO.url;
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        Board board = searchBoardDTO.board;
        board.setUrl(boardURL);
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.id = board.getId();
        responseDTO.url = board.getUrl();
        responseDTO.name = board.getName();
        responseDTO.privacy = board.getPrivacy();
        return responseDTO;
    }

    public static BoardResponseDTO addUserToBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        String userId = requestDTO.userId;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        SearchUserDTO searchUserDTO = UserRepository.searchUserById(userId);
        if(searchUserDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.userFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.userFindOperation = FindOperation.FOUND;

        Board board = searchBoardDTO.board;
        User user = searchUserDTO.user;
        for (User usr : board.getMembers()){
            if (usr.getId().equals(user.getId())){
                responseDTO.addOperation = AddOperation.ALREADY_EXISTS;
                break;
            }
        }
        if (responseDTO.addOperation != AddOperation.ALREADY_EXISTS){
            board.getMembers().add(user);
            responseDTO.addOperation = AddOperation.SUCCESSFUL;
            responseDTO.userId = user.getId();
        }

        return responseDTO;
    }

    public static BoardResponseDTO removeUserFromBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        String userId = requestDTO.userId;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        SearchUserDTO searchUserDTO = UserRepository.searchUserById(userId);
        if(searchUserDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.userFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.userFindOperation = FindOperation.FOUND;

        Board board = searchBoardDTO.board;
        User user = searchUserDTO.user;

        for (User usr : board.getMembers()){
            if (usr.getId().equals(user.getId())){
                board.getMembers().remove(user);
                responseDTO.removeOperation = RemoveOperation.REMOVED;
                break;
            }
        }
        responseDTO.removeOperation = RemoveOperation.REMOVED == responseDTO.removeOperation ? RemoveOperation.REMOVED : RemoveOperation.NOT_FOUND;

        return responseDTO;
    }

    public static BoardResponseDTO addBoardListToBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        String boardListId = requestDTO.userId;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }

        responseDTO.findOperation = FindOperation.FOUND;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.boardListFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.boardListFindOperation = FindOperation.FOUND;

        Board board = searchBoardDTO.board;
        BoardList boardList = searchBoardListDTO.boardList;
        for (BoardList brdList : board.getLists()){
            if (brdList.getId().equals(boardList.getId())){
                responseDTO.addOperation = AddOperation.ALREADY_EXISTS;
                break;
            }
        }
        if (responseDTO.addOperation != AddOperation.ALREADY_EXISTS){
            board.getLists().add(boardList);
            responseDTO.addOperation = AddOperation.SUCCESSFUL;
            responseDTO.boardListId = boardListId;
        }

        return responseDTO;
    }

    public static BoardResponseDTO removeBoardListFromBoard(BoardRequestDTO requestDTO){
        BoardResponseDTO responseDTO = new BoardResponseDTO();
        String boardId = requestDTO.id;
        String boardListId = requestDTO.userId;
        SearchBoardDTO searchBoardDTO = BoardRepository.searchBoard(boardId);
        if(searchBoardDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }

        responseDTO.findOperation = FindOperation.FOUND;
        SearchBoardListDTO searchBoardListDTO = BoardListRepository.searchBoardList(boardListId);
        if(searchBoardListDTO.findOperation == FindOperation.NOT_FOUND){
            responseDTO.boardListFindOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.boardListFindOperation = FindOperation.FOUND;

        Board board = searchBoardDTO.board;
        BoardList boardList = searchBoardListDTO.boardList;
        for (BoardList brdList : board.getLists()){
            if (brdList.getId().equals(boardList.getId())){
                board.getLists().remove(boardList);
                responseDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
                responseDTO.boardListId = boardListId;
                break;
            }
        }
        if (responseDTO.deleteOperation != DeleteOperation.SUCCESSFUL){
            responseDTO.deleteOperation = DeleteOperation.FAILED;
        }

        return responseDTO;
    }

    public static List<BoardResponseDTO> showAllBoards(BoardRequestDTO requestDTO){
        AllBoardsDTO allBoardsDTO = BoardRepository.getAllBoardIds();
        List<BoardResponseDTO> responseDTOS = new ArrayList<>();
        if (allBoardsDTO.findOperation == FindOperation.NOT_FOUND){
            BoardResponseDTO responseDTO = new BoardResponseDTO();
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            responseDTOS.add(responseDTO);
            return responseDTOS;
        }
        for(String str : allBoardsDTO.boardIds){
            BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
            boardRequestDTO.id = str;
            BoardResponseDTO responseDTO = new BoardResponseDTO();
            responseDTO = BoardService.showBoard(boardRequestDTO);
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }


}
