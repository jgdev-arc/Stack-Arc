package com.tlz.stackarc.security

import com.tlz.stackarc.config.JwtConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtUtils(
    private val jwtConfig: JwtConfig
) {

    init {
        println("🛠 JwtUtils constructed — jwtConfig.secret = ${jwtConfig.secret}")
    }

    @Value("\${jwt.secret}")
    private lateinit var secretJwtString: String

    private lateinit var _key: SecretKey

    val key: SecretKey
        get() = _key

    private val expirationTimeInMillis: Long =
        1000L * 60L * 60L * 24L * 30L * 6L

    @PostConstruct
    fun init() {
        println("✅ JWT CONFIG SECRET: ${jwtConfig.secret}") // Debug print
        val keyBytes = jwtConfig.secret.toByteArray(StandardCharsets.UTF_8)
        _key = SecretKeySpec(keyBytes, "HmacSHA256")
    }

    fun generateToken(email: String): String {
        return Jwts.builder()
            .subject(email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expirationTimeInMillis))
            .signWith(key)
            .compact()
    }

    fun getUsernameFromToken(token: String) : String {
        return extractClaims(token, Claims::getSubject)
    }

    fun <T> extractClaims(token: String, claimsResolver: (Claims) -> T): T
    {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claimsResolver(claims)

    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaims(token) { it.expiration }
        return expiration.before(Date())
    }


}
