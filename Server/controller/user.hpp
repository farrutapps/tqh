//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_USER_HPP
#define SERVER_USER_HPP

#include <iostream>
#include <vector>
#include "../utils/json.hpp"

using json = nlohmann::json;
namespace controller {

    /// store the current state of each user
    struct user {
        int user_id;
        unsigned int time;
        std::vector<bool> states;

        user();
        user(int user_id);
        static int find(std::vector<user> &users, int usr_id);

    private:
        int num_states;
    };

    /// convert user to json. automatically called by nlohmann::json
    inline void to_json(json& j, const user& u) {
        j = json{{"user_id", u.user_id}, {"time", u.time}, {"states", u.states}};
    }

    /// user from json
    inline void from_json(const json& j, user& u) {
        u.user_id = j.at("user_id").get<int>();
        u.time = j.at("time").get<int>();
        u.states = j.at("states").get< std::vector<bool> >();
    }
}

#endif //SERVER_USER_HPP
