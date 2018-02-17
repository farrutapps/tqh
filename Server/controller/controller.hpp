//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_CONTROLLER_HPP
#define SERVER_CONTROLLER_HPP

#include <iostream>
#include <vector>
#include "user.hpp"
#include "../connection/rest_endpoint_handler.hpp"

namespace controller {
    class controller {

        std::vector<user> users_;

    public:
        controller(int num_users, int num_leds);
        std::vector<user> get_users();
        void set_users(std::vector<user> users);
        std::vector<http::server::rest_endpoint_handler *> get_endpoint_handlers();
    };
} // namespace controller

#endif //SERVER_CONTROLLER_HPP
