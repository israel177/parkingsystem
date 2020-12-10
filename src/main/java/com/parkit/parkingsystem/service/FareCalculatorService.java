package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.util.Date;

public class FareCalculatorService {
    double to_divide = 60 * 1000;
    double to_multiply = to_divide * 30;

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        Date inTime;
        Date outTime;
        inTime = ticket.getInTime();
        outTime = ticket.getOutTime();
        long duration = outTime.getTime() - inTime.getTime();
        double result = duration / to_divide;

        if (result <= to_multiply) {
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
        }
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(result * Fare.CAR_RATE_PER_MINUTE);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(result * Fare.BIKE_RATE_PER_MINUTE);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
        }

    }
}
