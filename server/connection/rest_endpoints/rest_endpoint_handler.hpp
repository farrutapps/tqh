//
// Created by Sebastian Ratz on 15.02.18.
//

#ifndef SERVER_REST_ENDPOINT_HANDLER_HPP
#define SERVER_REST_ENDPOINT_HANDLER_HPP
#include <iostream>
#include "../request.hpp"
namespace http {
    namespace server {
        class rest_endpoint_handler {
        protected:
            std::string uri;

        public:
            virtual void perform_action(const request &req) = 0;

            std::string get_uri() {
                return uri;
            }
        };
    }
}

#endif //SERVER_REST_ENDPOINT_HANDLER_HPP
