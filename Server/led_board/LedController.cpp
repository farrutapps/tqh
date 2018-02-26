//
// Created by Sebastian Ratz on 24.02.18.
//

#include "LedController.hpp"
#include <bitset>
#include <thread>
LedController::LedController() {
    init_leds();


    std::thread led_threads([this](){
        timed_led_control();
    });
}
LedController::~LedController() { stop = true; }

void LedController::init_leds() {

    for (int i=0; i<msg_pin_numbers.size(); ++i) {
        message_leds.push_back(Led(msg_pin_numbers[i]));
    }
    for (int i=0; i<time_pin_numbers.size(); ++i) {
        message_leds.push_back(Led(time_pin_numbers[i]));
    }
    for (int i=0; i<user_pin_numbers.size(); ++i) {
        message_leds.push_back(Led(time_pin_numbers[i]));
    }

}

void LedController::set_msg_leds(std::vector<unsigned int> &pin_numbers) {
    assert_valid_led_pins(pin_numbers);
    msg_pin_numbers = pin_numbers;
    init_leds();
}

void LedController::set_user_leds(std::vector<unsigned int> &pin_numbers) {
    assert_valid_led_pins(pin_numbers);
    user_pin_numbers = pin_numbers;
    init_leds();
}

void LedController::set_time_leds(std::vector<unsigned int> &pin_numbers) {
    time_pin_numbers = pin_numbers;
    assert_valid_led_pins(pin_numbers);
    init_leds();
}


void LedController::timed_led_control() {
    while(true) {
        if (stop)
            break;

        current_displayed_user_idx = (current_displayed_user_idx + 1) % users.size();

        const controller::user usr = users[current_displayed_user_idx];

        for (int i=0; i<message_leds.size(); ++i) {
            message_leds[i].set_state(usr.led_states[i]);
        }

        for (int i=0; i<user_leds.size(); ++i) {
            if (i == current_displayed_user_idx) {
                user_leds[i].set_state(true);
            }
            else {
                user_leds[i].set_state(false);
            }
        }

        std::string time_binary = time2binary(usr.time);
        for (int i=0; i<time_leds.size(); ++i) {
            time_leds[i].set_state((bool)time_binary[i]);
        }
    }
}

void LedController::onUpdate(controller::user usr) {
    int id = controller::find(users, usr.user_id);
    if (id == -1) {
        users.push_back(usr);
    }
    else {
        users[id] = usr;
    }
}

std::string LedController::time2binary(unsigned int time) {
    std::string binary = std::bitset<8>(time).to_string();
}

void LedController::assert_valid_led_pins(std::vector<unsigned int> &pin_numbers) {
    std::vector<bool> valid_led_pins(27, false);
    valid_led_pins[4] = true;
    valid_led_pins[5] = true;
    valid_led_pins[6] = true;
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
    valid_led_pins[26] = true;
    valid_led_pins[27] = true;

    bool are_valid = true;

    for (int i=0; i<pin_numbers.size(); ++i) {
        const unsigned int pin = pin_numbers[i];

        if (pin >= pin_numbers.size()) {
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

