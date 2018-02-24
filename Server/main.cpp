//
// main.cpp
// ~~~~~~~~
//
// Copyright (cont) 2003-2017 Christopher M. Kohlhoff (chris at kohlhoff dot com)
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
#include "esteful/Led.hpp"

int main(int argc, char* argv[])
{

    try
    {
        // Check command line arguments.
        if (argc != 3)
        {
            std::cerr << "Usage: http_server <address> <port>\n";
            return 1;
        }

        controller::controller state_controller = controller::controller(2, 7);
        std::vector<http::server::rest_endpoint_handler*> handlers =state_controller.get_endpoint_handlers();
        // Initialise the server.
        http::server::server s(argv[1], argv[2], handlers );

        Led led(4);
        led.on();
        led.off();
        led.opposite();

        // Run the server until stopped.
        s.run();
    }
    catch (std::exception& e)
    {
        std::cerr << "exception: " << e.what() << "\n";
    }

    return 0;
}
