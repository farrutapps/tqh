//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_PUBLISH_INFO_HPP
#define SERVER_PUBLISH_INFO_HPP

#include "../../connection/rest_endpoint_handler.hpp"
#include "../user.hpp"
#include "../controller.hpp"

namespace controller {
    class publish_info : public http::server::rest_endpoint_handler {
        controller *cont;
    public:
        explicit publish_info(controller *c);
        http::server::reply perform_action(const http::server::request &req) override;
    };
} // namespace controller


#endif //SERVER_PUBLISH_INFO_HPP
