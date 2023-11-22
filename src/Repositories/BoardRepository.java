package Repositories;

import DTOs.AllBoardsDTO;
import DTOs.DeleteBoardDTO;
import Models.DeleteOperation;
import DTOs.SearchBoardDTO;
import Models.AddOperation;
import Models.Board;
import Models.FindOperation;
import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class BoardRepository {
    private static HashMap<String, Board> listOfBoards = new HashMap<>();

    public static AddOperation addBoard(String id, Board board){
        if (listOfBoards.containsKey(id))
            return AddOperation.ALREADY_EXISTS;
        listOfBoards.put(id, board);
        return AddOperation.SUCCESSFUL;
    }

    public static SearchBoardDTO searchBoard(String id){
        SearchBoardDTO searchBoardDTO = new SearchBoardDTO();
        if (listOfBoards.containsKey(id)){
            searchBoardDTO.findOperation = FindOperation.FOUND;
            searchBoardDTO.board = listOfBoards.get(id);
            return searchBoardDTO;
        }
        searchBoardDTO.findOperation = FindOperation.NOT_FOUND;
        return searchBoardDTO;
    }

    public static DeleteBoardDTO deleteBoard(String id){
        DeleteBoardDTO deleteBoardDTO = new DeleteBoardDTO();
        if (listOfBoards.containsKey(id)){
            deleteBoardDTO.findOperation = FindOperation.FOUND;
            deleteBoardDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
            deleteBoardDTO.board = listOfBoards.remove(id);
            return deleteBoardDTO;
        }
        deleteBoardDTO.findOperation = FindOperation.NOT_FOUND;
        deleteBoardDTO.deleteOperation = DeleteOperation.FAILED;
        return deleteBoardDTO;
    }

    public static AllBoardsDTO getAllBoardIds(){
        AllBoardsDTO responseDTO = new AllBoardsDTO();
        if (listOfBoards.size() == 0){
            responseDTO.findOperation = FindOperation.NOT_FOUND;
            return responseDTO;
        }
        responseDTO.findOperation = FindOperation.FOUND;
        responseDTO.boardIds = listOfBoards.keySet().stream().toList();
        return responseDTO;
    }
}
