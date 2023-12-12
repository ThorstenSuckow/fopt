package fopt4.uebung2_3.login;

import java.util.*;

public class Model
{
    private HashMap<String, String> userList;

    public Model()
    {
        userList = new HashMap<>();
        userList.put("h�nsel", "password1");
        userList.put("gretel", "password2");
        userList.put("aschenputtel", "password3");
        userList.put("wolf", "password4");
        userList.put("7zwerge", "password5");
        userList.put("hansimgl�ck", "password6");
    }

    public boolean isOkay(String loginName, String password)
    {
        return password.equals(userList.get(loginName));
    }
}
