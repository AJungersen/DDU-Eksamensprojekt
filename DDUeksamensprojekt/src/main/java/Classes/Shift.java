/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDateTime;

/**
 *
 * @author Christoffer
 */
public class Shift {

    int shift_ID;
    LocalDateTime date;
    float period;
    String descreption;
    Worker workerAssigned;

    public Shift(int shift_ID, LocalDateTime date, float period, String descreption, Worker workerAssigned) {
        this.shift_ID = shift_ID;
        this.date = date;
        this.period = period;
        this.descreption = descreption;
        this.workerAssigned = workerAssigned;
    }

    //create
    public Shift(LocalDateTime date, float period, String descreption, Worker workerAssigned) {
        this.date = date;
        this.period = period;
        this.descreption = descreption;
        this.workerAssigned = workerAssigned;
    }

    public int getShift_ID() {
        return shift_ID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getPeriod() {
        return period;
    }

    public void setPeriod(float period) {
        this.period = period;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public Worker getWorkerAssigned() {
        return workerAssigned;
    }

    public void setWorkerAssigned(Worker workerAssigned) {
        this.workerAssigned = workerAssigned;
    }
}
