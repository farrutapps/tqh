//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_CONTROLLER_HPP
#define SERVER_CONTROLLER_HPP

#include <iostream>
#include <vector>
#include "user.hpp"
#include "../connection/rest_endpoint_handler.hpp"

class controller {

    std::vector<user> users;

public:
    controller ();
    std::vector<http::server::rest_endpoint_handler*> get_endpoint_handlers();
};


#endif //SERVER_CONTROLLER_HPP
