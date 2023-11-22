package Controllers;

import DTOs.CardRequestDTO;
import DTOs.CardResponseDTO;
import Models.FindOperation;
import Services.CardService;

public class CardController {
    public CardResponseDTO createCard(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.createCard(requestDTO);
        return responseDTO;
    }

    public CardResponseDTO setCardName(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.setCardName(requestDTO);
        return responseDTO;
    }

    public CardResponseDTO setCardDescription(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.setCardDescription(requestDTO);
        return responseDTO;
    }
    public CardResponseDTO assignUserToCard(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.assignUserToCard(requestDTO);
        return responseDTO;
    }
    public CardResponseDTO unAssignUserToCard(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.unAssignUserToCard(requestDTO);
        return responseDTO;
    }
    public CardResponseDTO moveCardFromOneListToOther(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.moveCardFromOneListToOther(requestDTO);
        return responseDTO;
    }

    public CardResponseDTO deleteCard(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.deleteCard(requestDTO);
        if (responseDTO.findOperation == FindOperation.FOUND)
            responseDTO = CardService.removeCardFromBoardList(requestDTO);
        return responseDTO;
    }

    public CardResponseDTO showCard(CardRequestDTO requestDTO){
        CardResponseDTO responseDTO = CardService.showCard(requestDTO);
        return responseDTO;
    }
}
