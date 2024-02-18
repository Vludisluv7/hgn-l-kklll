package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private final Lock leftFork;
    private final Lock rightFork;
    private final String name;
    private int eatingCount;

    public Philosopher(String name, Lock leftFork, Lock rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.eatingCount = 0;
    }

    private void eat() {
        System.out.println(name + " is eating");
    }

    private void think() {
        System.out.println(name + " is thinking");
    }

    public void run() {
        while (eatingCount < 3) {
            think();

            leftFork.lock();
            rightFork.lock();

            eat();

            leftFork.unlock();
            rightFork.unlock();

            eatingCount++;
        }
    }

    public static void main(String[] args) {
        Lock[] forks = new Lock[5];
        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher("Philosopher" + (i + 1), forks[i], forks[(i + 1) % 5]);
        }

        for (int i = 0; i < 5; i++) {
            philosophers[i].start();
        }
    }
}