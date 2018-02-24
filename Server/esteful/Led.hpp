//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_LED_HPP
#define SERVER_LED_HPP

#include "Gpio.hpp"

class Led {
    Gpio gpio;
public:
    Led(int pin_num);
    ~Led();
    void on();
    void off();
    void opposite();
};


#endif //SERVER_LED_HPP
