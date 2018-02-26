//
// Created by Sebastian Ratz on 24.02.18.
//

#include "Led.hpp"


Led::Led(int pin_num) {
    gpio.set_pin(pin_num);
    gpio.export_pin();
    gpio.set_direction(true);
    gpio.set_value(false);
}

Led::~Led() {
    off();
    gpio.set_value(false);
    gpio.unexport_pin();
}

void Led::on() {
    gpio.set_value(true);
}

void Led::off() {
    gpio.set_value(false);
}

void Led::set_state(bool value) {
    gpio.set_value(value);
}

void Led::opposite() {
    std::string curr_value_str = gpio.read_value();
    int curr_value = std::atoi(curr_value_str.c_str());
    if (curr_value == 1) {
        off();
    }
    else if (curr_value == 0) {
        on();
    }
    else {
        std::cerr << "Led did not understand its value" << std::endl;
    }
}

