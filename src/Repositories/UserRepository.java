package Repositories;

import DTOs.SearchUserDTO;
import Models.AddOperation;
import Models.FindOperation;
import Models.User;

import java.util.HashMap;

public class UserRepository {
    private static HashMap<String, User> userHashMap = new HashMap<>();
    private static HashMap<String , User> userNameHashMap = new HashMap<>();

    public static AddOperation addUser(String id, User user){
        if (userHashMap.containsKey(id))
            return AddOperation.ALREADY_EXISTS;
        System.out.println("ID - "+id);
        userHashMap.put(id, user);
        userNameHashMap.put(user.getName(), user);
        return AddOperation.SUCCESSFUL;
    }

    public static SearchUserDTO searchUserById(String id){
        SearchUserDTO searchUserDTO = new SearchUserDTO();
        if (userHashMap.containsKey(id)){
            searchUserDTO.findOperation = FindOperation.FOUND;
            searchUserDTO.user = userHashMap.get(id);
            return searchUserDTO;
        }
        searchUserDTO.findOperation = FindOperation.NOT_FOUND;
        return searchUserDTO;
    }

    public static SearchUserDTO searchUserByName(String name){
        SearchUserDTO searchUserDTO = new SearchUserDTO();
        if (userNameHashMap.containsKey(name)){
            searchUserDTO.findOperation = FindOperation.FOUND;
            searchUserDTO.user = userNameHashMap.get(name);
            return searchUserDTO;
        }
        searchUserDTO.findOperation = FindOperation.NOT_FOUND;
        return searchUserDTO;
    }
}
