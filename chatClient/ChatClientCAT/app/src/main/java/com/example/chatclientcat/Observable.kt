package com.example.chatclientcat

interface Observable {
    fun registerObserver (observer: Observer)
    fun deregisterObserver (observer: Observer)
    fun notifyObserver()
}