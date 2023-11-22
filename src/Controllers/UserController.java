package Controllers;

import DTOs.UserRequestDTO;
import DTOs.UserResponseDTO;
import Services.UserService;

public class UserController {
    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        UserResponseDTO responseDTO = UserService.createUser(requestDTO);
        return responseDTO;
    }
}
