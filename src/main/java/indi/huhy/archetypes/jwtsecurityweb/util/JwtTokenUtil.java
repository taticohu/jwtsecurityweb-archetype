package indi.huhy.archetypes.jwtsecurityweb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class JwtTokenUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("secret");
    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_ROLES = "roles";
    private static final Integer EXPIRES_TIME = 30;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String createToken(String username, String[] roles) {
        try {
            return JWT.create()
                    .withClaim(CLAIM_USERNAME, username)
                    .withArrayClaim(CLAIM_ROLES, roles)
                    .withExpiresAt(DateUtils.addMinutes(new Date(), EXPIRES_TIME))
                    .sign(ALGORITHM);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return StringUtils.EMPTY;
    }

    public static boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
        return false;
    }

    public static String getUsernameByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_USERNAME)
                    .asString();
        } catch (JWTDecodeException exception) {
            //Invalid token
        }
        return StringUtils.EMPTY;
    }

    public static String[] getRolesByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_ROLES)
                    .asArray(String.class);
        } catch (JWTDecodeException exception) {
            //Invalid token
        }
        return EMPTY_STRING_ARRAY;
    }
}
