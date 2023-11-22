package DTOs;

import Models.*;

import java.util.ArrayList;
import java.util.List;

public class BoardResponseDTO {
    public String id;
    public String name;
    public BoardPrivacy privacy;
    public String url;
    public FindOperation findOperation;
    public DeleteOperation deleteOperation;
    public List<BoardListResponseDTO> boardListResponseDTOS;
    public FindOperation userFindOperation;

    public FindOperation boardListFindOperation;
    public String userId;
    public String boardListId;
    public RemoveOperation removeOperation;
    public AddOperation addOperation;

    public BoardResponseDTO(){
        boardListResponseDTOS = new ArrayList<>();
    }
}
