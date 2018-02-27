//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_GPIO_HPP
#define SERVER_GPIO_HPP
#include <iostream>
#include "boost/filesystem.hpp"
#include <boost/asio.hpp>

namespace led_board {
    class gpio {
        int pin_number_=-1;
        std::string pin_name_;
        boost::filesystem::path base_dir_ ="/sys/class/gpio/"; // /sys/class/gpio_/"  "/Users/Sebastian/Desktop/debug";

        void init_vars(int pin_num);
        void write_to_file(std::string filename, std::string content);
        std::string read_from_file(std::string filename);

    public:
        gpio();
        explicit gpio(int pin_number);
        void set_pin(int pin_number);
        void export_pin();
        void unexport_pin();
        void set_direction(bool out);
        void set_value(bool value);
        std::string read_value();
    };
} // namespace led_board
#endif //SERVER_GPIO_HPP
