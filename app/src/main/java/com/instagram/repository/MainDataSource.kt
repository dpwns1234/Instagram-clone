package com.instagram.repository

import com.instagram.model.Main

interface MainDataSource {
    fun getMainData(): Main?
}