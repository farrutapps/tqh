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
#include "led.hpp"
#include "../controller/update_listener.hpp"
#include "../controller/user.hpp"

namespace led_board {

    class led_controller : public controller::update_listener {
        std::vector<unsigned int> msg_pin_numbers_ = {4, 17, 27, 22, 5, 6, 13, 19, 26};
        std::vector<unsigned int> user_pin_numbers_ = {18, 25};
        std::vector<unsigned int> time_pin_numbers_ = {12, 16, 20, 21};

        std::vector<led> message_leds_;
        std::vector<led> time_leds_;
        std::vector<led> user_leds_;

        std::vector<controller::user> users_;
        unsigned long current_displayed_user_idx_ = 0;

        boost::asio::io_context *io_context_;
        int timer_seconds_ = 2;
        boost::asio::deadline_timer timer_;
        bool run_ = true;

        boost::asio::signal_set signals_;

        void stop();

        void assert_valid_led_pins(std::vector<unsigned int> &pin_numbers);
        void on_update(controller::user usr) override;
        std::string time2binary(unsigned int time);
        void init_leds();

    public:
        explicit led_controller(boost::asio::io_context *io_context);
        ~led_controller();

        void timed_led_control();
    };
} // namespace led_board

#endif //SERVER_LEDCONTROLLER_HPP
