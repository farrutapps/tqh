//
// Created by Sebastian Ratz on 16.02.18.
//

#include "controller.hpp"
#include "rest_endpoints/publish_info.hpp"
#include "rest_endpoints/data_update_handler.hpp"

using rest_endpoint_handler = http::server::rest_endpoint_handler;

controller::controller() {}

std::vector<rest_endpoint_handler*> controller::get_endpoint_handlers() {
    std::vector<rest_endpoint_handler*> handlers;
    handlers.push_back(new publish_info());
    data_update_handler *h = new data_update_handler(users);
    handlers.push_back(h);
    return handlers;
}
