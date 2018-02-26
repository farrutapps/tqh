//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_LEDCONTROLLER_HPP
#define SERVER_LEDCONTROLLER_HPP

#include <iostream>
#include <vector>
#include "Led.hpp"
#include "../controller/UpdateListener.hpp"
#include "../controller/user.hpp"

class LedController : public controller::UpdateListener {
    std::vector<unsigned int> msg_pin_numbers = {4,5,6};
    std::vector<unsigned int> user_pin_numbers = {4,5,6};
    std::vector<unsigned int> time_pin_numbers = {4,5,6};
    std::vector<Led> message_leds;
    std::vector<Led> time_leds;
    std::vector<Led> user_leds;
    std::vector<controller::user> users;
    unsigned long current_displayed_user_idx = 0;
    std::thread *led_thread;
    bool stop = false;

    void timed_led_control();
    void assert_valid_led_pins(std::vector<unsigned int> &pin_numbers);
    void onUpdate(controller::user usr) override;
    std::string time2binary(unsigned int time);
    void init_leds();
public:
    LedController();
    ~LedController();
    void set_msg_leds(std::vector<unsigned int> &pin_numbers);
    void set_user_leds(std::vector<unsigned int> &pin_numbers);
    void set_time_leds(std::vector<unsigned int> &pin_numbers);
};


#endif //SERVER_LEDCONTROLLER_HPP
