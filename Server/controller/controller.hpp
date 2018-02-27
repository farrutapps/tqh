//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_CONTROLLER_HPP
#define SERVER_CONTROLLER_HPP

#include <iostream>
#include <vector>
#include "user.hpp"
#include "../connection/rest_endpoint_handler.hpp"
#include "UpdateListener.hpp"
#include "../led_board/LedController.hpp"
#include <boost/asio.hpp>

namespace controller {
    class controller {
        boost::asio::io_context *io_context;
        std::vector<user> users_;
        std::vector<UpdateListener *> update_listeners_;
        LedController led_controller_;
    public:
        controller(int num_users, int num_leds, boost::asio::io_context *io_context);
        std::vector<user> get_users();
        void set_users(std::vector<user> users);
        std::vector<http::server::rest_endpoint_handler *> get_endpoint_handlers();
        void register_listener(UpdateListener *listener);
        void notifyUpdate(user user);
    };
} // namespace controller

#endif //SERVER_CONTROLLER_HPP
