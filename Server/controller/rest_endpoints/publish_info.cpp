//
// Created by Sebastian Ratz on 16.02.18.
//

#include "../../connection/reply.hpp"
#include "publish_info.hpp"
namespace controller {
    publish_info::publish_info(controller *c) : cont(c) {
        uri_="/status";
        method_=GET;
    }

    http::server::reply publish_info::perform_action(const http::server::request &req) {
        json data;
        try {
            data = cont->get_users();
        }
        catch (json::exception e) {
            std::cerr << e.what() << std::endl;
            return http::server::reply::stock_reply(http::server::reply::json_parse_error);
        }

        http::server::reply rep;
        rep.status = http::server::reply::ok;
        rep.content = data.dump();
        rep.headers.resize(2);
        rep.headers[0].name = "Content-Length";
        rep.headers[0].value = std::to_string(rep.content.size());
        rep.headers[1].name = "Content-Type";
        rep.headers[1].value = "application/json";

        return rep;
    }
} // namespace controller
