//
// Created by Sebastian Ratz on 24.02.18.
//

#ifndef SERVER_UPDATELISTENER_HPP
#define SERVER_UPDATELISTENER_HPP

#include <vector>
#include "user.hpp"

namespace controller {

    class update_listener {
    public:
    virtual void on_update(user usr) = 0;
    };

} // namespace controller

#endif //SERVER_UPDATELISTENER_HPP
