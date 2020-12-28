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

// if r true 5%

        if (the_final_time_in_minutes <= 30) {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(0 * Fare.CAR_RATE_PER_MINUTE);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(0 * Fare.BIKE_RATE_PER_MINUTE);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        } else {
            double time_less_than_thirty_minutes = the_final_time_in_minutes - 30;
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
}
