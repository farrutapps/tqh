//
// Created by Sebastian Ratz on 15.02.18.
//

#include "rest_endpoint_handler.hpp"
#include "../../utils/json.hpp"
#include "data_update_handler.hpp"
#include "../request.hpp"

using json = nlohmann::json;

data_update_handler::data_update_handler() {
    uri = "/update";
}
void data_update_handler::perform_action(const http::server::request &req) {
    json data = json::parse(req.data);

    std::string me = data["password"];
    // TODO save data, control leds etc.
}
