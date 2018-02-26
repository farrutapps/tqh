//
// Created by Sebastian Ratz on 15.02.18.
//

#include "../../connection/rest_endpoint_handler.hpp"
#include "data_update_handler.hpp"

using json = nlohmann::json;

namespace controller {
    data_update_handler::data_update_handler(controller *c) : c(c){
        uri_ = "/update";
        method_= method::POST;
    }

    http::server::reply data_update_handler::perform_action(const http::server::request &req) {
        std::vector<user> users = c->get_users();
        try {
            json j = json::parse(req.data);
            for (auto& element : j) {
                user user_i = element;

                // check whether updated values make sense
                if (user_i.user_id >= users.size() || user_i.time > 12)  {
                    return http::server::reply::stock_reply(http::server::reply::forbidden);
                }

                users[user_i.user_id] = user_i;
            }
            c->set_users(users);
        }
        catch (json::exception e) {
            std::cerr << e.what() << std::endl;
            return http::server::reply::stock_reply(http::server::reply::json_parse_error);
        }

        // TODO remove debug output
        for (int i=0; i<users.size(); ++i) {
            std::cout << "user:" << std::endl;
            std::cout << users[i].user_id << std::endl;
            std::cout << users[i].time << std::endl;
        }

        return http::server::reply::stock_reply(http::server::reply::accepted);
    }
} // namespace controller

