package Services;

import DTOs.UserRequestDTO;
import DTOs.UserResponseDTO;
import Models.AddOperation;
import Models.User;
import Repositories.UserRepository;

public class UserService {
    public static UserResponseDTO createUser(UserRequestDTO requestDTO){
        UserResponseDTO responseDTO = new UserResponseDTO();
        User user = new User();
        user.setName(requestDTO.name);
        user.setEmail(requestDTO.email);
        System.out.println(user.getId());
        AddOperation addOperation = UserRepository.addUser(user.getId(),user);
        responseDTO.addOperation = addOperation;
        if (addOperation == AddOperation.SUCCESSFUL)
            responseDTO.id = user.getId();
        return responseDTO;
    }
}
