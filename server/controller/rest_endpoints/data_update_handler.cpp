//
// Created by Sebastian Ratz on 15.02.18.
//

#include "../../connection/rest_endpoint_handler.hpp"
#include "data_update_handler.hpp"

using json = nlohmann::json;

data_update_handler::data_update_handler(std::vector<user> &users) : users_(users){
    uri_ = "/update";
    method_= method::POST;
}

http::server::reply data_update_handler::perform_action(const http::server::request &req) {
    try {
        json j = json::parse(req.data);
        for (json::iterator it = j.begin(); it != j.end(); ++it) {
            std::cout << *it << '\n';
        }
    }
    catch (json::exception e) {
        //std::cout << e.what() << std::endl;
        return http::server::reply::stock_reply(http::server::reply::not_implemented);
    }

    return http::server::reply::stock_reply(http::server::reply::accepted);
}
