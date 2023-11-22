package Controllers;

import DTOs.*;
import Models.AddOperation;
import Models.FindOperation;

import java.util.List;

public class TelloController {

    BoardController boardController;
    CardController cardController;
    BoardListController boardListController;
    UserController userController;

    public TelloController(){
        boardController = new BoardController();
        cardController = new CardController();
        boardListController = new BoardListController();
        userController = new UserController();
    }

    public void getInputFromUser(UserInputRequestDTO requestDTO){
        String [] wordsInInput = requestDTO.input.split(" ");
        if (wordsInInput[0].equals("USER")){
            performUserOperation(wordsInInput);
        }
        else if (wordsInInput[0].equals("BOARD")){
            performBoardOperation(wordsInInput);
        }
        else if(wordsInInput[0].equals("LIST")){
            performBoardListOperation(wordsInInput);
        }
        else if(wordsInInput[0].equals("CARD")){
            performCardOperation(wordsInInput);
        }
        else
            peformShowOperation(wordsInInput);
    }

     void performCardOperation(String [] wordsInInput){
        if (wordsInInput[1].equals("CREATE")){
            createCard(wordsInInput);
        }
    }

     void createCard(String [] wordsInInput){
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.name = wordsInInput[2];
        requestDTO.boardListId = wordsInInput[3];
        CardResponseDTO responseDTO = cardController.createCard(requestDTO);
        if (responseDTO.addOperation == AddOperation.SUCCESSFUL){
            System.out.println("Card has been created and card id is - "+responseDTO.cardId);

        }
        else if (responseDTO.addOperation == AddOperation.ALREADY_EXISTS){
            System.out.println("A Card with similar details already exists ");
        }
        else{
            System.out.println("Board List add operation failed");
        }
    }

     void performBoardListOperation(String [] wordsInInput){
        if (wordsInInput[1].equals("CREATE")){
            createBoardList(wordsInInput);
        }
    }

     void createBoardList(String [] wordsInInput){
        BoardListRequestDTO requestDTO = new BoardListRequestDTO();
        requestDTO.name = wordsInInput[2];
        requestDTO.boardId = wordsInInput[3];
        BoardListResponseDTO responseDTO = boardListController.createBoardList(requestDTO);
        if (responseDTO.addOperation == AddOperation.SUCCESSFUL){
            System.out.println("Board has been created and board id is - "+responseDTO.boardListId );

        }
        else if (responseDTO.addOperation == AddOperation.ALREADY_EXISTS){
            System.out.println("A board list with similar details already exists ");
        }
        else{
            System.out.println("Board List add operation failed + "+responseDTO.boardFindOperation);
        }
    }

     void performBoardOperation(String [] wordsInInput){
        if (wordsInInput[1].equals("CREATE")){
            createBoard(wordsInInput);
        }
    }

     void createBoard(String [] wordsInInput){
        BoardRequestDTO requestDTO = new BoardRequestDTO();
        requestDTO.name = wordsInInput[2];
        BoardResponseDTO responseDTO = boardController.createBoard(requestDTO);
        if (responseDTO.addOperation == AddOperation.SUCCESSFUL){
            System.out.println("Board has been created and board id is - "+responseDTO.id + " and board url is - "+responseDTO.url);

        }
        else if (responseDTO.addOperation == AddOperation.ALREADY_EXISTS){
            System.out.println("A board with similar details already exists ");
        }
        else{
            System.out.println("Board add operation failed");
        }
    }

     void performUserOperation(String [] wordsInInput){
        if (wordsInInput[1].equals("CREATE")){
            createUser(wordsInInput);
        }
    }

     void createUser(String [] wordsInInput){
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.name = wordsInInput[2];
        requestDTO.email = wordsInInput[3];

        UserResponseDTO responseDTO = userController.createUser(requestDTO);

        if (responseDTO.addOperation == AddOperation.SUCCESSFUL){
            System.out.println("User Has Been Created With ID - "+responseDTO.id);
        }
        else if(responseDTO.addOperation == AddOperation.ALREADY_EXISTS){
            System.out.println("User already exists");
        }
        else{
            System.out.println("Add operation Failed");
        }
    }

     void peformShowOperation(String [] wordsInInput){
        if (wordsInInput.length == 1){
            BoardRequestDTO requestDTO = new BoardRequestDTO();
            List<BoardResponseDTO> responseDTOList = boardController.showAllBoards(requestDTO);
            for (BoardResponseDTO responseDTO : responseDTOList){
                displayBoard(responseDTO);
            }
        }
        else if (wordsInInput[1].equals("BOARD")){
            BoardRequestDTO requestDTO = new BoardRequestDTO();
            requestDTO.id = wordsInInput[2];
            BoardResponseDTO responseDTO = boardController.showBoard(requestDTO);
            displayBoard(responseDTO);
        }
        else if (wordsInInput[1].equals("LIST")){
            BoardListRequestDTO requestDTO = new BoardListRequestDTO();
            requestDTO.id = wordsInInput[2];
            BoardListResponseDTO responseDTO = boardListController.showBoardList(requestDTO);
            displayBoardList(responseDTO);
        }
        else {
            CardRequestDTO requestDTO = new CardRequestDTO();
            requestDTO.cardId = wordsInInput[2];
            CardResponseDTO responseDTO = cardController.showCard(requestDTO);
            displayCards(responseDTO);
        }
    }

     void displayBoard(BoardResponseDTO responseDTO){
       if (responseDTO.findOperation != FindOperation.FOUND){
           System.out.println("Did not find board");
           return;
       }
        System.out.println("Board Details are :\n1.)Name"+responseDTO.name+"\n2.)Id - "+responseDTO.id+"\n3.) Privacy -"+responseDTO.privacy+"\n 4.)URL- "+responseDTO.url+"\n\n The Lists in Board are : ");
       for(BoardListResponseDTO boardListResponseDTO : responseDTO.boardListResponseDTOS){
           displayBoardList(boardListResponseDTO);
       }
    }

    public void displayBoardList(BoardListResponseDTO responseDTO){
        if (responseDTO.findOperation != FindOperation.FOUND){
            System.out.println("Did not find board list");
            return;
        }
        System.out.println("Board List Details are :\n1.)Name"+responseDTO.boardListName+"\n2.)Id - "+responseDTO.boardListId+"\n\n The Cards in Board List are : ");
        for(CardResponseDTO cardResponseDTO : responseDTO.cardResponseDTOList){
            displayCards(cardResponseDTO);
        }
    }

    public void displayCards(CardResponseDTO responseDTO){
        if (responseDTO.findOperation != FindOperation.FOUND){
            System.out.println("Did not find card");
            return;
        }
        System.out.println("Board List Details are :\n1.)Name"+responseDTO.cardName+"\n2.)Id - "+responseDTO.cardId + "\n3.) Description - "+responseDTO.description);
    }

}
