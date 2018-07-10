package turing.turingcodey.data.dao.custom;

import turing.turingcodey.data.model.User;

public interface UserMapper extends turing.turingcodey.data.dao.UserMapper {
    User selectByUserName(String userName);
}
