package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.util.Date;

public class FareCalculatorService {
    double one_minute_in_millisecondes = 60 * 1000;

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        Date inTime;
        Date outTime;
        inTime = ticket.getInTime();
        outTime = ticket.getOutTime();
        long duration = outTime.getTime() - inTime.getTime();
        double the_final_time_in_minutes = duration / one_minute_in_millisecondes;
        double time_less_than_thirty_minutes = the_final_time_in_minutes - 30;

        if (the_final_time_in_minutes <= 30) {
            time_less_than_thirty_minutes = 0;
            fare(ticket, time_less_than_thirty_minutes);
        } else if (ticket.recurring) {
            time_less_than_thirty_minutes = time_less_than_thirty_minutes - time_less_than_thirty_minutes * 5 / 100;
            fare(ticket, time_less_than_thirty_minutes);
        } else {
            fare(ticket, time_less_than_thirty_minutes);
        }
    }

    private void fare(Ticket ticket, double time_less_than_thirty_minutes) {
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(time_less_than_thirty_minutes * Fare.CAR_RATE_PER_MINUTE);
                break;
            }
            case BIKE: {
                ticket.setPrice(time_less_than_thirty_minutes * Fare.BIKE_RATE_PER_MINUTE);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}



