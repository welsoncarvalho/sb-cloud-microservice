package com.test.reservationbusinessservices.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.reservationbusinessservices.client.GuestService;
import com.test.reservationbusinessservices.client.ReservationService;
import com.test.reservationbusinessservices.client.RoomService;
import com.test.reservationbusinessservices.domain.Guest;
import com.test.reservationbusinessservices.domain.Reservation;
import com.test.reservationbusinessservices.domain.Room;
import com.test.reservationbusinessservices.domain.RoomReservation;

@Service
public class RoomReservationBusinessProcess {

	@Autowired
	private GuestService guestService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ReservationService reservationService;
	
	public List<RoomReservation> getRoomReservationsForDate(String dateString){
        List<Room> rooms = this.roomService.findAll(null);
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room->{
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationService.findAll(dateString);
        if(null!=reservations){
            reservations.forEach(reservation -> {
                Guest guest = this.guestService.findOne(reservation.getGuestId());
                if(null!=guest) {
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
                    roomReservation.setDate(reservation.getReservationDate());
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservation.setGuestId(guest.getId());
                }

            });
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long roomId:roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }

	
}
