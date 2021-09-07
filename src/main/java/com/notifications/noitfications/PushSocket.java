package com.notifications.noitfications;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


//Bill simulator for testing

@ServerEndpoint("/push/")
public class PushSocket {

    int month=1;
    float bill;

    @OnMessage
    public void onMessage(String message, final Session session) {

        System.out.println("Message from " + session.getId() + ": " + message);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String date= whatMonth();
                genRandomBill();
                try {
                    session.getBasicRemote().sendText("Your bill for " + date + " is: " + bill);
                } catch (IOException ex) {
                    Logger.getLogger(PushSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 3 * 1000);
    }
    private void genRandomBill() {
        double random = 200f + Math.random() * (700f - 200f);
        bill = (float) random;
    }

    private String whatMonth(){
        String monthString;
        switch (month) {
            case 1:  monthString = "January";
                break;
            case 2:  monthString = "February";
                break;
            case 3:  monthString = "March";
                break;
            case 4:  monthString = "April";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "June";
                break;
            case 7:  monthString = "July";
                break;
            case 8:  monthString = "August";
                break;
            case 9:  monthString = "September";
                break;
            case 10: monthString = "October";
                break;
            case 11: monthString = "November";
                break;
            case 12: monthString = "December";
                break;
            default: monthString = "Invalid month";
                break;
        }
        if(month<12){
            month++;
        }
        else{
            month=1;
        }
        return monthString;
    }
}