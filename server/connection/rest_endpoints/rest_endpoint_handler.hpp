//
// Created by Sebastian Ratz on 15.02.18.
//

#ifndef SERVER_REST_ENDPOINT_HANDLER_HPP
#define SERVER_REST_ENDPOINT_HANDLER_HPP
#include <iostream>
#include "../request.hpp"
#include "../reply.hpp"

namespace http {
    namespace server {

        class rest_endpoint_handler {
        protected:
            std::string uri_;
            enum method {
                GET,
                POST
            } method_;

        public:
            virtual reply perform_action(const request &req) = 0;

            std::string get_uri() {
                return uri_;
            }

            std::string get_method_string() {
                switch(method_) {
                    case GET:
                        return "GET";
                    case POST:
                        return "POST";
                }
            }

            method get_method() {
                return method_;
            }
        };
    }
}

#endif //SERVER_REST_ENDPOINT_HANDLER_HPP
