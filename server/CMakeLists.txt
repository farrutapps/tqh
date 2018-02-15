cmake_minimum_required(VERSION 3.9)
project(server)

set(CMAKE_CXX_STANDARD 11)
find_package(Boost 1.66.0 COMPONENTS system filesystem REQUIRED)

if(Boost_FOUND)
    message(STATUS "Boost_INCLUDE_DIRS: ${Boost_INCLUDE_DIRS}")
    message(STATUS "Boost_LIBRARIES: ${Boost_LIBRARIES}")
    message(STATUS "Boost_VERSION: ${Boost_VERSION}")
    include_directories(${Boost_INCLUDE_DIRS})
endif()

add_executable(server main.cpp utils/json.hpp connection/reply.cpp connection/connection_manager.hpp connection/request.hpp connection/header.hpp connection/request_handler.cpp connection/reply.hpp connection/connection_manager.cpp connection/mime_types.cpp connection/connection.hpp connection/connection.cpp connection/request_handler.hpp connection/server.hpp connection/request_parser.cpp connection/server.cpp connection/request_parser.hpp connection/mime_types.hpp)

if(Boost_FOUND)
    target_link_libraries(server ${Boost_LIBRARIES})
endif()

target_link_libraries(server ${Boost_LIBRARIES})