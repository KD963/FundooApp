package com.bridgelabz.fundoo.utility;

//import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

/*import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;*/

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken implements JwtTokenInterface {

	String secretPin = "kalyani963";

	/*
	 * @Override public String generateToken(String userid) {
	 * 
	 * Algorithm algorithm = Algorithm.HMAC256(secretPin); String token =
	 * JWT.create().withClaim("ID", userid).sign(algorithm);
	 * 
	 * return token; }
	 * 
	 * @Override public String verifyToken(String token) {
	 * 
	 * String id; Verification verification =
	 * JWT.require(Algorithm.HMAC256(secretPin)); JWTVerifier jwtVerifier =
	 * verification.build(); DecodedJWT decodedJWT = jwtVerifier.verify(token);
	 * Claim claim = decodedJWT.getClaim("ID"); id = claim.asString(); return id;
	 * 
	 * }
	 */

	@Override
	public String generateToken(String userid) {
		String token = Jwts.builder().setSubject("fundoo")
				.setExpiration(new Date(System.currentTimeMillis() + 10000000*100)).setId(userid)
				.signWith(SignatureAlgorithm.HS256, secretPin).compact();
		return token;
	}

	@Override
	public String verifyToken(String token) {

		Jws<Claims> claims = Jwts.parser().setSigningKey(secretPin).parseClaimsJws(token);
		String userId = (String) claims.getBody().getId();

		return userId;
	}

}
