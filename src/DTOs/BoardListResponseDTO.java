package DTOs;

import Models.AddOperation;
import Models.DeleteOperation;
import Models.FindOperation;

import java.util.ArrayList;
import java.util.List;

public class BoardListResponseDTO {
    public FindOperation boardFindOperation;
    public String boardListId;
    public String boardListName;
    public FindOperation findOperation;
    public DeleteOperation deleteOperation;

    public List<CardResponseDTO> cardResponseDTOList;

    public FindOperation cardFindOperation;
    public AddOperation addOperation;
    public BoardListResponseDTO(){
        cardResponseDTOList = new ArrayList<>();
    }
}
