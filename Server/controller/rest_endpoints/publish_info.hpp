//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_PUBLISH_INFO_HPP
#define SERVER_PUBLISH_INFO_HPP

#include "../../connection/rest_endpoint_handler.hpp"

class publish_info : public http::server::rest_endpoint_handler {
public:
    publish_info();
    http::server::reply perform_action(const http::server::request &req) override;
};


#endif //SERVER_PUBLISH_INFO_HPP
