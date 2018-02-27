//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_LED_HPP
#define SERVER_LED_HPP

#include "gpio.hpp"

namespace led_board {
    class led {
        gpio gpio_;

    public:
        led(int pin_num);
        ~led();
        void on();
        void off();
        void set_state(bool value);
        void opposite();
    };
 } // namespace led_board

#endif //SERVER_LED_HPP
