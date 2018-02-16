//
// Created by Sebastian Ratz on 16.02.18.
//

#include "publish_info.hpp"

publish_info::publish_info() {
    uri_="info";
    method_=GET;
}

http::server::reply publish_info::perform_action(const http::server::request &req) {
    // TODO get info state, publish
}
