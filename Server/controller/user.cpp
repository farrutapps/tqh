//
// Created by Sebastian Ratz on 27.02.18.
//

#include "user.hpp"


namespace controller {
    user::user() {}

    user::user(int user_id) : user_id(user_id) {
        time = 0;
        states.resize(num_states);
    }

    int user::find (std::vector<user> &users, int usr_id) {
        for (int i=0; i<users.size(); ++i) {
            if (usr_id == users[i].user_id)
                return i;
        }
        return -1;
    }
}

