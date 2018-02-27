//
// Created by Sebastian Ratz on 24.02.18.
//

#include "gpio.hpp"
#include <fstream>

namespace led_board {
    gpio::gpio() {}

    gpio::gpio(int pin_num) {
        init_vars(pin_num);
    }

    void gpio::init_vars(int pin_num) {
        pin_number = pin_num;

        pin_name = "gpio";
        pin_name.append(std::to_string(pin_number));
    }

    void gpio::write_to_file(std::string filename, std::string content) {
        boost::filesystem::path filename_fs = filename;
        boost::filesystem::path file_path = base_dir / filename_fs;
        std::ofstream file(file_path.c_str());
        if (file.is_open()) {
            file << content;
            file.close();
        }
        else {
            std::string msg = "Failed to open gpio file: ";
            msg.append(file_path.string());
            std::cerr << msg << std::endl;
        }
    }

    std::string gpio::read_from_file(std::string filename) {
        boost::filesystem::path filename_fs = filename;
        boost::filesystem::path file_path = base_dir / filename_fs;
        std::ifstream file(file_path.c_str());

        std::string output;
        if (file.is_open()) {
            std::getline(file, output);
            file.close();
        }
        else {
            std::string msg = "Failed to open gpio file: ";
            msg.append(file_path.string());
            std::cerr << msg << std::endl;
        }
        return output;
    }

    void gpio::set_pin(int pin_num) {
        init_vars(pin_num);
    }

    void gpio::export_pin() {
        write_to_file("export", std::to_string(pin_number));
    }

    void gpio::unexport_pin() {
        write_to_file("unexport", std::to_string(pin_number));
    }

    void gpio::set_direction(bool out) {
        std::string filename = pin_name;
        filename.append("/direction");

        if(out) {
            write_to_file(filename, "out");
        }
        else {
            write_to_file(filename, "in");
        }
    }

    void gpio::set_value(bool value) {
        std::string filename = pin_name;
        filename.append("/value");
        write_to_file(filename, std::to_string((int)value));
    }

    std::string gpio::read_value() {
        std::string filename = pin_name;
        filename.append("/value");
        return read_from_file(filename);
    }
} // namespace led_board

