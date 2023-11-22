package Client;

import Controllers.TelloController;
import DTOs.UserInputRequestDTO;
import DTOs.UserRequestDTO;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        TelloController controller = new TelloController();
        Scanner inp = new Scanner(System.in);
        UserInputRequestDTO requestDTO = new UserInputRequestDTO();
        while (true){
            String nextInput = inp.nextLine();
            requestDTO.input = nextInput;
            controller.getInputFromUser(requestDTO);
        }
    }
}
