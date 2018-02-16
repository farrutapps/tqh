//
// Created by Sebastian Ratz on 15.02.18.
//

#ifndef SERVER_DATA_UPDATE_HANDLER_HPP
#define SERVER_DATA_UPDATE_HANDLER_HPP

#include "../request.hpp"
#include "../reply.hpp"
#include "rest_endpoint_handler.hpp"

class data_update_handler : public http::server::rest_endpoint_handler {
public:
    data_update_handler();

    virtual http::server::reply perform_action(const http::server::request &req);
};


#endif //SERVER_DATA_UPDATE_HANDLER_HPP
