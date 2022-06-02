package com.bikotron.data.network

interface JsonNetwork<out T> {
    fun getValue(): T
}