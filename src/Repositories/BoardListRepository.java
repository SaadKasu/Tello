package Repositories;

import DTOs.DeleteBoardDTO;
import DTOs.DeleteBoardListDTO;
import DTOs.SearchBoardDTO;
import DTOs.SearchBoardListDTO;
import Models.AddOperation;
import Models.BoardList;
import Models.DeleteOperation;
import Models.FindOperation;

import java.util.HashMap;
import java.util.TreeMap;

public class BoardListRepository {
    private static HashMap<String, BoardList> boardListHashMap = new HashMap<>();

    public static AddOperation addBoardList(String id, BoardList boardList){
        if (boardListHashMap.containsKey(id))
            return AddOperation.ALREADY_EXISTS;
        boardListHashMap.put(id, boardList);
        return AddOperation.SUCCESSFUL;
    }

    public static SearchBoardListDTO searchBoardList(String id){
        SearchBoardListDTO searchBoardListDTO = new SearchBoardListDTO();
        if (boardListHashMap.containsKey(id)){
            searchBoardListDTO.boardList = boardListHashMap.get(id);
            searchBoardListDTO.findOperation = FindOperation.FOUND;
            return searchBoardListDTO;
        }
        searchBoardListDTO.findOperation = FindOperation.NOT_FOUND;
        return searchBoardListDTO;
    }

    public static DeleteBoardListDTO deleteBoardList(String id){
        DeleteBoardListDTO deleteBoardListDTO = new DeleteBoardListDTO();
        if (boardListHashMap.containsKey(id)){
            deleteBoardListDTO.findOperation = FindOperation.FOUND;
            deleteBoardListDTO.deleteOperation = DeleteOperation.SUCCESSFUL;
            deleteBoardListDTO.boardList = boardListHashMap.remove(id);
            return deleteBoardListDTO;
        }
        deleteBoardListDTO.findOperation = FindOperation.NOT_FOUND;
        deleteBoardListDTO.deleteOperation = DeleteOperation.FAILED;
        return deleteBoardListDTO;
    }
}
