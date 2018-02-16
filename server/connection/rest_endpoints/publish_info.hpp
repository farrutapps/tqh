//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_PUBLISH_INFO_HPP
#define SERVER_PUBLISH_INFO_HPP

#include "rest_endpoint_handler.hpp"

class publish_info : public http::server::rest_endpoint_handler {
    publish_info();

    virtual http::server::reply perform_action(const http::server::request &req);
};


#endif //SERVER_PUBLISH_INFO_HPP
