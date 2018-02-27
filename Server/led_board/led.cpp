//
// Created by Sebastian Ratz on 24.02.18.
//

#include "led.hpp"

namespace led_board {
    led::led(int pin_num) {
        gpio_.set_pin(pin_num);
        gpio_.export_pin();
        gpio_.set_direction(true);
        gpio_.set_value(false);
    }

    led::~led() {
        off();
        gpio_.set_value(false);
        gpio_.unexport_pin();
    }

    void led::on() {
        gpio_.set_value(true);
    }

    void led::off() {
        gpio_.set_value(false);
    }

    void led::set_state(bool value) {
        gpio_.set_value(value);
    }

    void led::opposite() {
        std::string curr_value_str = gpio_.read_value();
        int curr_value = std::atoi(curr_value_str.c_str());
        if (curr_value == 1) {
            off();
        }
        else if (curr_value == 0) {
            on();
        }
        else {
            std::cerr << "led did not understand its value" << std::endl;
        }
    }
} // namespace led_board

