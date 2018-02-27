//
// Created by Sebastian Ratz on 16.02.18.
//

#include "controller.hpp"
#include "rest_endpoints/publish_info.hpp"
#include "rest_endpoints/data_update_handler.hpp"

using rest_endpoint_handler = http::server::rest_endpoint_handler;

namespace controller {

    controller::controller(int num_users, int num_leds, boost::asio::io_context *io_context)
            : io_context(io_context),
              led_controller_(io_context){

        for (int i=0; i<num_users; ++i) {
            user new_user = {i, 0, std::vector<bool>(num_leds, 0)};
            users_.push_back(new_user);
        }
    }

    std::vector<rest_endpoint_handler*> controller::get_endpoint_handlers() {
        std::vector<rest_endpoint_handler*> handlers;
        handlers.push_back(new publish_info(this));
        handlers.push_back(new data_update_handler(this));
        return handlers;
    }

    std::vector<user> controller::get_users() {
        return users_;
    }

    void controller::set_users(std::vector<user> users) {
        users_=users;
    }

    void controller::register_listener(UpdateListener *listener) {
        update_listeners_.push_back(listener);
    }

    void controller::notifyUpdate(user user) {
        for (int i=0; i<update_listeners_.size(); ++i) {
            update_listeners_[i]->onUpdate(user);
        }
    }
} // namespace controller

