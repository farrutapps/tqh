//
// Created by Sebastian Ratz on 15.02.18.
//

#include "rest_endpoint_handler.hpp"
#include "data_update_handler.hpp"
#include "../reply.hpp"
using json = nlohmann::json;

data_update_handler::data_update_handler() {
    uri_ = "/update";
    method_= method::POST;
}

http::server::reply data_update_handler::perform_action(const http::server::request &req) {
    json data = json::parse(req.data);

    std::string me = data["password"];
    // TODO save data, control leds etc.
    return http::server::reply::stock_reply(http::server::reply::status_type::accepted);
}
