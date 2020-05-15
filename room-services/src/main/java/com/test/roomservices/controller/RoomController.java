package com.test.roomservices.controller;

import java.util.Collections;
import java.util.List;

import com.test.roomservices.model.Room;
import com.test.roomservices.repository.RoomRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rooms")
@Api(value = "rooms", description = "Data service operations on rooms", tags = ("rooms"))
public class RoomController {
    
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    @ApiOperation(value = "Get All Rooms", notes = "Gets all rooms in the system", nickname = "getRooms")
    public List<Room> findAll(@RequestParam(name = "roomNumber", required = false) String roomNumber) {
        if (StringUtils.isNotEmpty(roomNumber)) {
            return Collections.singletonList(roomRepository.findByRoomNumber(roomNumber));
        }

        return (List<Room>) roomRepository.findAll();
    }
}