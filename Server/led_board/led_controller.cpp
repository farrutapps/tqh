//
// Created by Sebastian Ratz on 24.02.18.
//

#include "led_controller.hpp"
#include <bitset>
#include <vector>

namespace led_board {

    led_controller::led_controller(boost::asio::io_context *io_context)
            : io_context(io_context),
              signals(*io_context, SIGINT, SIGTERM),
              timer(*io_context, boost::posix_time::seconds(timer_seconds))
    {
        assert_valid_led_pins(msg_pin_numbers);
        assert_valid_led_pins(user_pin_numbers);
        assert_valid_led_pins(time_pin_numbers);
        init_leds();

        // calls timed_led_control after timer expired. returns immediately.
        timer.async_wait(boost::bind(&led_controller::timed_led_control, this));


        // Start an asynchronous wait for one of the signals to occur.
        signals.async_wait([this](boost::system::error_code /*ec*/, int /*signo*/)
                           {
                               stop();
                           });
    }

    led_controller::~led_controller() {
        stop();
    }

    void led_controller::init_leds() {

        message_leds.reserve(msg_pin_numbers.size());
        for (auto pin: msg_pin_numbers) {
            message_leds.emplace_back(pin);
        }

        time_leds.reserve(msg_pin_numbers.size());
        for (auto pin: time_pin_numbers) {
            time_leds.emplace_back(pin);
        }

        user_leds.reserve(msg_pin_numbers.size());
        for (auto pin: user_pin_numbers) {
            user_leds.emplace_back(pin);
        }
    }

    void led_controller::stop() {
        std::cout << "Stopping led_controller" << std::endl;
        run = false;
    }

    void led_controller::timed_led_control() {
        if (users.size() > 0) {
            current_displayed_user_idx = (current_displayed_user_idx + 1) % users.size();
            const controller::user usr = users[current_displayed_user_idx];

            for (int i = 0; i < message_leds.size(); ++i) {
                message_leds[i].set_state(usr.states[i]);
            }

            for (int i = 0; i < user_leds.size(); ++i) {
                if (i == current_displayed_user_idx) {
                    user_leds[i].set_state(true);
                } else {
                    user_leds[i].set_state(false);
                }
            }

            std::string time_binary = time2binary(usr.time);
            for (int i = 0; i < time_leds.size(); ++i) {
                time_leds[i].set_state((bool) time_binary[i]);
            }
        }
        if (run) {
            // calls timed_led_control after timer expired. returns immediately.
            timer.expires_at(timer.expires_at() + boost::posix_time::seconds(timer_seconds));
            timer.async_wait(boost::bind(&led_controller::timed_led_control, this));
        }
    }

    void led_controller::on_update(controller::user usr) {
        int id = controller::user::find(users, usr.user_id);
        if (id == -1) {
            users.push_back(usr);
        }
        else {
            users[id] = usr;
        }
    }

    std::string led_controller::time2binary(unsigned int time) {
        std::bitset<4> bits(time);
        std::string binary = bits.to_string();
        return binary;
    }

    void led_controller::assert_valid_led_pins(std::vector<unsigned int> &pin_numbers) {
        std::vector<bool> valid_led_pins(27, false);
        valid_led_pins[4] = true;
        valid_led_pins[5] = true;
        valid_led_pins[6] = true;
        valid_led_pins[12] = true;
        valid_led_pins[13] = true;
        valid_led_pins[16] = true;
        valid_led_pins[17] = true;
        valid_led_pins[18] = true;
        valid_led_pins[19] = true;
        valid_led_pins[20] = true;
        valid_led_pins[21] = true;
        valid_led_pins[22] = true;
        valid_led_pins[23] = true;
        valid_led_pins[24] = true;
        valid_led_pins[25] = true;
        valid_led_pins[26] = true;
        valid_led_pins[27] = true;

        bool are_valid = true;

        for (int i=0; i<pin_numbers.size(); ++i) {
            const unsigned int pin = pin_numbers[i];

            if (pin > valid_led_pins.size()) {
                are_valid = false;
                break;
            }
            else if (!valid_led_pins[pin]) {
                are_valid = false;
                break;
            }
        }

        if (!are_valid) {
            throw std::invalid_argument("Not all of your LED pin numbers are valid.");
        }
    }
} // namespace led_board

