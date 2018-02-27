//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_LEDCONTROLLER_HPP
#define SERVER_LEDCONTROLLER_HPP

#include <iostream>
#include <vector>
#include <boost/asio.hpp>
#include <boost/bind.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>
#include "Led.hpp"
#include "../controller/UpdateListener.hpp"
#include "../controller/user.hpp"

class LedController : public controller::UpdateListener {
    std::vector<unsigned int> msg_pin_numbers = {4, 17, 27, 22, 5, 6, 13, 19};
    std::vector<unsigned int> user_pin_numbers = {18, 25};
    std::vector<unsigned int> time_pin_numbers = {12, 16, 20, 21};
    std::vector<Led> message_leds;
    std::vector<Led> time_leds;
    std::vector<Led> user_leds;
    std::vector<controller::user> users;
    unsigned long current_displayed_user_idx = 0;

    boost::asio::io_context *io_context;
    int timer_seconds = 2;
    boost::asio::deadline_timer timer;
    bool run = true;
    boost::asio::signal_set signals;

    void stop();

    void assert_valid_led_pins(std::vector<unsigned int> &pin_numbers);
    void onUpdate(controller::user usr) override;
    std::string time2binary(unsigned int time);
    void init_leds();

public:
    explicit LedController(boost::asio::io_context *io_context);
    ~LedController();
    void timed_led_control();
};


#endif //SERVER_LEDCONTROLLER_HPP
