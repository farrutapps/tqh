//
// Created by Sebastian Ratz on 15.02.18.
//

#ifndef SERVER_DATA_UPDATE_HANDLER_HPP
#define SERVER_DATA_UPDATE_HANDLER_HPP

#include "../../connection/request.hpp"
#include "../../connection/reply.hpp"
#include "../../connection/rest_endpoint_handler.hpp"
#include "../user.hpp"

class data_update_handler : public http::server::rest_endpoint_handler {
    std::vector<user> users_;

public:
    explicit data_update_handler(std::vector<user> &users);
    http::server::reply perform_action(const http::server::request &req) override;
};


#endif //SERVER_DATA_UPDATE_HANDLER_HPP
