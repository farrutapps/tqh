//
// Created by Sebastian Ratz on 16.02.18.
//

#ifndef SERVER_CONTROLLER_HPP
#define SERVER_CONTROLLER_HPP

#include <iostream>
#include <vector>
#include "user.hpp"
#include "../connection/rest_endpoint_handler.hpp"
#include "update_listener.hpp"
#include "../led_board/led_controller.hpp"
#include <boost/asio.hpp>

namespace controller {
    class controller {
        boost::asio::io_context *io_context;
        int num_users = 2;
        std::vector<user> users_;
        std::vector<update_listener *> update_listeners_;
        led_board::led_controller led_controller_;
    public:
        controller(boost::asio::io_context *io_context);
        std::vector<user> get_users();
        void set_users(std::vector<user> users);
        std::vector<http::server::rest_endpoint_handler *> get_endpoint_handlers();
        void register_listener(update_listener *listener);
        void notifyUpdate(user user);
    };
} // namespace controller

#endif //SERVER_CONTROLLER_HPP
