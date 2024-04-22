package com.gdi.conferenceplanner.util.handlers

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.Base64

object TokenHandler {

    private const val SECRET_KEY = "secret" // Same secret key used for signing
    fun validateAndExtractClaims(token: String): Map<String, Any>? {
        return try {
            val signingKey: Key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray())

            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decodeToken(jwt: String): String {
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            "$header"
            "$payload"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}
