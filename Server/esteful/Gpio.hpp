//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_GPIO_HPP
#define SERVER_GPIO_HPP
#include <iostream>
#include "boost/filesystem.hpp"
#include <boost/asio.hpp>

class Gpio {
    int pin_number;
    std::string pin_name;
    boost::filesystem::path base_dir = "/sys/class/gpio/";  //"/Users/Sebastian/Desktop/debug";
    void write_to_file(std::string filename, std::string content);
    std::string read_from_file(std::string filename);

public:
    Gpio(int pin_number);
    void export_pin();
    void unexport_pin();
    void set_direction(bool out);
    void set_value(bool value);
    std::string read_value();
};


#endif //SERVER_GPIO_HPP
