package DTOs;

import Models.*;

public class CardResponseDTO {
    public FindOperation boardListFindOperation;
    public String cardName;
    public String cardId;
    public FindOperation findOperation;
    public FindOperation userFindOperation;

    public String userId;
    public Assign_Unassign_Operation assign_unassign_operation;
    public DeleteOperation deleteOperation;
    public String description;
    public String userName;
    public FindOperation toBoardListFindOperation;
    public MoveOperation moveOperation;
    public AddOperation addOperation;
}
