//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_USER_HPP
#define SERVER_USER_HPP

#include <iostream>
#include <vector>

struct user {
    int user_id;
    u_int8_t time;
    std::vector<bool> led_states;
};


#endif //SERVER_USER_HPP
