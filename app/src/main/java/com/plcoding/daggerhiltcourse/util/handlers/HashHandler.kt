package com.plcoding.daggerhiltcourse.util.handlers

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object HashHandler {
    fun sha256(input: String): String? {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(input.toByteArray())
            val hexString = StringBuilder()
            for (b in hashBytes) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }
}