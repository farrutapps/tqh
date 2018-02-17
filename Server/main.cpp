//
// main.cpp
// ~~~~~~~~
//
// Copyright (c) 2003-2017 Christopher M. Kohlhoff (chris at kohlhoff dot com)
//
// Distributed under the Boost Software License, Version 1.0. (See accompanying
// file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//

#include <iostream>
#include <boost/asio.hpp>
#include "connection/server.hpp"
#include <vector>
#include "controller/user.hpp"
#include "connection/rest_endpoint_handler.hpp"
#include "controller/controller.hpp"

using json = nlohmann::json;

int main(int argc, char* argv[])
{

    try
    {
        // Check command line arguments.
        if (argc != 4)
        {
            std::cerr << "Usage: http_server <address> <port> <doc_root>\n";
            std::cerr << "  For IPv4, try:\n";
            std::cerr << "    receiver 0.0.0.0 80 .\n";
            std::cerr << "  For IPv6, try:\n";
            std::cerr << "    receiver 0::0 80 .\n";
            return 1;
        }


        controller c = controller();
        std::vector<http::server::rest_endpoint_handler*> h =c.get_endpoint_handlers();
        // Initialise the server.
        http::server::server s(argv[1], argv[2], h );

        // Run the server until stopped.
        s.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "exception: " << e.what() << "\n";
    }

    return 0;
}