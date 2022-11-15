package com.maeteno.study.java.base;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.VerificationJwkSelector;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JwtTest {

    @Test
    public void test1() throws JoseException {
        //
        // An example of signature verification using JSON Web Signature (JWS)
        // where the verification key is obtained from a JSON Web Key Set document.
        //

        // A JSON Web Key (JWK) is a JavaScript Object Notation (JSON) data structure that represents a
        // cryptographic key (often but not always a public key). A JSON Web Key Set (JWK Set) document
        // is a JSON data structure for representing one or more JSON Web Keys (JWK). A JWK Set might,
        // for example, be obtained from an HTTPS endpoint controlled by the signer but this example
        // presumes the JWK Set JSONhas already been acquired by some secure/trusted means.
        String jsonWebKeySetJson = "{\"keys\":[{\"kty\":\"RSA\",\"kid\":\"PMIcdjpwdv0-cLiwVcksE0Gxunw\",\"use\":\"sig\",\"n\":\"zHwXh91BNog3eZbiFtVOUYdwnyREzV6SqPH-FDgWwG7WWyHkVWjjM8uJYVT2bLY7mIW8pYJ4bFXUDmVxCMncHQNdF4vHCGP5Q2lFei7iZjarY0SZalyaxD0QBuWBwexdaQp8m68KPV9BUfhp6rV0-WDYUTqlRPleWEno8BkY8adPXkJmXjS4ggs-1Oo-FezdSIaOQimkCdCZHxZlotWE0Qucz-7eXJ_h8iEXcQ4Y90m73mXXDOx94i_jgTyWCdMPwW5fbzxQ7x0Mgr4vg2_st6zCH3z1ikCQGHRkk784-Hbsy4hduG9WjiKqDNQG0gzX5jVnKjbfSpuJ5qUFA61IYQ\",\"e\":\"AQAB\",\"x5c\":[\"MIIEHzCCAwegAwIBAgIJAJra2vrHzcggMA0GCSqGSIb3DQEBCwUAMIGlMQswCQYDVQQGEwJGUjENMAsGA1UECAwETm9yZDEOMAwGA1UEBwwFTGlsbGUxDTALBgNVBAoMBE94aXQxDTALBgNVBAsMBE94aXQxKjAoBgNVBAMMIXByZXByb2RpZHBkZWNhdGhsb24uZGVjYXRobG9uLmNvbTEtMCsGCSqGSIb3DQEJARYec2ltb24uZGVsZXBpZXJyZUBkZWNhdGhsb24uY29tMB4XDTE1MDgxMjE1MDcyOFoXDTQ1MDgwNDE1MDcyOFowgaUxCzAJBgNVBAYTAkZSMQ0wCwYDVQQIDAROb3JkMQ4wDAYDVQQHDAVMaWxsZTENMAsGA1UECgwET3hpdDENMAsGA1UECwwET3hpdDEqMCgGA1UEAwwhcHJlcHJvZGlkcGRlY2F0aGxvbi5kZWNhdGhsb24uY29tMS0wKwYJKoZIhvcNAQkBFh5zaW1vbi5kZWxlcGllcnJlQGRlY2F0aGxvbi5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMfBeH3UE2iDd5luIW1U5Rh3CfJETNXpKo8f4UOBbAbtZbIeRVaOMzy4lhVPZstjuYhbylgnhsVdQOZXEIydwdA10Xi8cIY/lDaUV6LuJmNqtjRJlqXJrEPRAG5YHB7F1pCnybrwo9X0FR+GnqtXT5YNhROqVE+V5YSejwGRjxp09eQmZeNLiCCz7U6j4V7N1Iho5CKaQJ0JkfFmWi1YTRC5zP7t5cn+HyIRdxDhj3SbveZdcM7H3iL+OBPJYJ0w/Bbl9vPFDvHQyCvi+Db+y3rMIffPWKQJAYdGSTvzj4duzLiF24b1aOIqoM1AbSDNfmNWcqNt9Km4nmpQUDrUhhAgMBAAGjUDBOMB0GA1UdDgQWBBTVnKt2Znbrwe0EgXr6W0jtY52VCTAfBgNVHSMEGDAWgBTVnKt2Znbrwe0EgXr6W0jtY52VCTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQCS/dnmCmtgjV7nlmu0vrDeyWHkSI4weGKvhnqIgUXhbsbtbr+AwrMhe0vKHHcKeQpr/IV77dxfLhRsylg2XiLp5hdnGhPnfZ/tTIyeAPloeeWb08AMrUCm71boOinBGikyF/kbkyokul7vcLvbO2XAmVAtYPttdKOXLF1GqU2B9DGORmOIZshS6pRunAchYfsklCdHeNnugHkk8PrqXU+0WaSLsAg65W24bfmVeDBy9el8S0nYDIpc3DGvf8Ldu51pIUd2UpuH0Ss1PTizOiD4DXjSAZUaO9O59dvdHez2wWgXrT9tNsZqHvyyEnNzJC3Mw4KdsRFEnMwy10lCnNe2\"]}]}";

        // The complete JWS representation, or compact serialization, is string consisting of
        // three dot ('.') separated base64url-encoded parts in the form Header.Payload.Signature
        String compactSerialization = "eyJhbGciOiJSUzI1NiIsImtpZCI6IlBNSWNkanB3ZHYwLWNMaXdWY2tzRTBHeHVudyJ9.eyJzdWIiOiJaMDFBRUFTWSIsImF1ZCI6IkNmOTg4MTg3OWI0ODM2ZDAxY2ZmMjZjZGExZDUzZTZjMzllMjc3NzUzIiwianRpIjoiMVpSczZEVkNWU2xoaDRTRVBxM3IyZSIsImlzcyI6Imh0dHBzOi8vcHJlcHJvZC5pZHBkZWNhdGhsb24ub3h5bGFuZS5jb20iLCJpYXQiOjE2NTc1Mjk4NDQsImV4cCI6MTY1NzUzMDE0NCwiZW50cnlVVUlEIjoiMGFlMjA3NjgtODFkMi0xMjQ3LTgxODEtZWM1ODQxMjgwMDc1IiwicHJlZmVycmVkTGFuZ3VhZ2UiOiJaSCIsInJvbGUiOlsiaWQ9VkFMSURBVEUsaWQ9RlJBTkNISVNFLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9R2xvYmFsLGlkPVNob3BwZXJ0cmFrLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9VXNlcklULGlkPUNvbmZsdWVuY2UsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1DT00yVV9Db250UmV2LGlkPUNPTTJVLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9RVhUX0RBVEFURUFNLGlkPVZQTl9CUkFaSUwsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1SRUFERVIsaWQ9UE9TREFUQSxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVNwb3J0TGVhZGVyLGlkPU15R2FtZSxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVN0cm9uZ0F1dGgsaWQ9UGluZ0lkLGlkPWFwcFJvbGUsb3U9cm9sZSxvdT1yZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9U3RhbmRhcmQsaWQ9ZVRDTyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPWFjY2VzcyxpZD1wZHMsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1Vc2VyLGlkPUJpcmQtT2ZmaWNlLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9QUNDRVNTLGlkPUlQQUMsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1ERUNBU1RPUkVfbmF0aW9uYWxfdXNlcixpZD1ERUNBU1RPUkUsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1iaUl0VG9vbHMsaWQ9T0JJRUUsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1MRUNUVVJFLGlkPUNBVEFMT0dVRUFHRU5DRU1FTlQsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1BZG1pbixpZD1UQVRUT08saWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1XZWJBcHBfY2hpZmZyZXNfc2VjdTEsaWQ9V2ViYXBwX2NoaWZmcmVzLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9Q1JDU3RvcmVVc2VyLGlkPUNSQyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVBJWExfVmlzdUJPLGlkPVBJWEwsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1CYWNrb2ZmaWNlLGlkPURFQ0FHTyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPUJPQWNjZXNzLGlkPURpZ2l0YWxTY3JlZW4saWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iXSwiYyI6IkNOIiwibWFpbCI6ImFkbWluLmVhc3ltZXJjaC5wYXJ0bmVyQGRlY2F0aGxvbi5jb20iLCJkaXNwbGF5TmFtZSI6IkVBU1lNRVJDSCBBZG1pbiIsImdpdmVuTmFtZSI6IkFkbWluIiwiY24iOiJFQVNZTUVSQ0ggQWRtaW4iLCJzaXRldHlwZSI6IkhRIiwidGl0bGUiOiJTZXJ2aWNlIFByb3ZpZGVyIGZvciBJVCIsIm9iamVjdGNsYXNzIjpbInRvcCIsIlBlcnNvbiIsIm9yZ2FuaXphdGlvbmFsUGVyc29uIiwiaW5ldE9yZ1BlcnNvbiIsIm9jRXh0ZW5kZWRQZXJzb24iXSwidXVpZCI6IjU1OWY4OTY5LTIyZDMtNDc5ZC04ZWMxLWI0MzY3MzY3Zjk1YSIsImFsbHNpdGVzIjoiQ05IUUNITEIiLCJ1aWQiOiJaMDFBRUFTWSIsInNpdGUiOiJDTkhRQ0hMQiIsImZlZGVyYXRpb25faWRwIjoiZDEiLCJmYW1pbHlOYW1lIjoiRUFTWU1FUkNIIiwic2l0ZW5hbWUiOiJDSElOQSBMQUIiLCJzbiI6IkVBU1lNRVJDSCIsImNvc3RjZW50ZXIiOiIwMDUwMTA1ODUwMDZDMTA1Iiwiam9ibmFtZSI6IlBSRVNULklORk8iLCJwaS5zcmkiOiJhcWhhSWxON0J3VzhNd3B5ZktkeGhWc3lEN1EuUlZVLnktWDAifQ.dDhsg0GVKtq1NgEJf3ptIIXkX1KgZH4hNBYXjRYCnln_vvE-coY6o6zrXxkK2cvDM9a1L0IC6pYZymPNW7q7PcZkX1br0EwguBMf2pDnoCi_9Ngi-T9TISrISYOekn2BSrSsXq5OEWAG4xso7NWqTNcIudmcIUEd95J1tfHZVZ-4M77kZ2Vn3MnOv4X2LaZoA_g8hTmhkSAJRnmC2bPuK_K0vcwsaFmTh9etkOyYllYw7smcUiXyoOn-o2DvET3_2r69KpFmBhWnBe_0QQI5zYBuTauwWErxrF1ig7ex1f3DDiVHME3Bz7OKr9pJa01XwsyI8oDnTn8wB5XNmq8_qg";

        // Create a new JsonWebSignature object
        JsonWebSignature jws = new JsonWebSignature();

        // Set the algorithm constraints based on what is agreed upon or expected from the sender
        jws.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256));

        // Set the compact serialization on the JWS
        jws.setCompactSerialization(compactSerialization);

        // Create a new JsonWebKeySet object with the JWK Set JSON
        JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jsonWebKeySetJson);

        // The JWS header contains information indicating which key was used to secure the JWS.
        // In this case (as will hopefully often be the case) the JWS Key ID
        // corresponds directly to the Key ID in the JWK Set.
        // The VerificationJwkSelector looks at Key ID, Key Type, designated use (signatures vs. encryption),
        // and the designated algorithm in order to select the appropriate key for verification from
        // a set of JWKs.
        VerificationJwkSelector jwkSelector = new VerificationJwkSelector();
        JsonWebKey jwk = jwkSelector.select(jws, jsonWebKeySet.getJsonWebKeys());

        // The verification key on the JWS is the public key from the JWK we pulled from the JWK Set.
        jws.setKey(jwk.getKey());

        // Check the signature
        boolean signatureVerified = jws.verifySignature();

        // Do something useful with the result of signature verification
        System.out.println("JWS Signature is valid: " + signatureVerified);

        // Get the payload, or signed content, from the JWS
        String payload = jws.getPayload();

        // Do something useful with the content
        System.out.println("JWS payload: " + payload);
    }

    @Test
    void test2() throws JwkException {
        Map<String, Object> values = new HashMap<>();
        values.put("kty", "RSA");
        values.put("kid", "PMIcdjpwdv0-cLiwVcksE0Gxunw");
        values.put("use", "sig");
        values.put("n", "zHwXh91BNog3eZbiFtVOUYdwnyREzV6SqPH-FDgWwG7WWyHkVWjjM8uJYVT2bLY7mIW8pYJ4bFXUDmVxCMncHQNdF4vHCGP5Q2lFei7iZjarY0SZalyaxD0QBuWBwexdaQp8m68KPV9BUfhp6rV0-WDYUTqlRPleWEno8BkY8adPXkJmXjS4ggs-1Oo-FezdSIaOQimkCdCZHxZlotWE0Qucz-7eXJ_h8iEXcQ4Y90m73mXXDOx94i_jgTyWCdMPwW5fbzxQ7x0Mgr4vg2_st6zCH3z1ikCQGHRkk784-Hbsy4hduG9WjiKqDNQG0gzX5jVnKjbfSpuJ5qUFA61IYQ");
        values.put("e", "AQAB");
        values.put("x5c", List.of("MIIEHzCCAwegAwIBAgIJAJra2vrHzcggMA0GCSqGSIb3DQEBCwUAMIGlMQswCQYDVQQGEwJGUjENMAsGA1UECAwETm9yZDEOMAwGA1UEBwwFTGlsbGUxDTALBgNVBAoMBE94aXQxDTALBgNVBAsMBE94aXQxKjAoBgNVBAMMIXByZXByb2RpZHBkZWNhdGhsb24uZGVjYXRobG9uLmNvbTEtMCsGCSqGSIb3DQEJARYec2ltb24uZGVsZXBpZXJyZUBkZWNhdGhsb24uY29tMB4XDTE1MDgxMjE1MDcyOFoXDTQ1MDgwNDE1MDcyOFowgaUxCzAJBgNVBAYTAkZSMQ0wCwYDVQQIDAROb3JkMQ4wDAYDVQQHDAVMaWxsZTENMAsGA1UECgwET3hpdDENMAsGA1UECwwET3hpdDEqMCgGA1UEAwwhcHJlcHJvZGlkcGRlY2F0aGxvbi5kZWNhdGhsb24uY29tMS0wKwYJKoZIhvcNAQkBFh5zaW1vbi5kZWxlcGllcnJlQGRlY2F0aGxvbi5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMfBeH3UE2iDd5luIW1U5Rh3CfJETNXpKo8f4UOBbAbtZbIeRVaOMzy4lhVPZstjuYhbylgnhsVdQOZXEIydwdA10Xi8cIY/lDaUV6LuJmNqtjRJlqXJrEPRAG5YHB7F1pCnybrwo9X0FR+GnqtXT5YNhROqVE+V5YSejwGRjxp09eQmZeNLiCCz7U6j4V7N1Iho5CKaQJ0JkfFmWi1YTRC5zP7t5cn+HyIRdxDhj3SbveZdcM7H3iL+OBPJYJ0w/Bbl9vPFDvHQyCvi+Db+y3rMIffPWKQJAYdGSTvzj4duzLiF24b1aOIqoM1AbSDNfmNWcqNt9Km4nmpQUDrUhhAgMBAAGjUDBOMB0GA1UdDgQWBBTVnKt2Znbrwe0EgXr6W0jtY52VCTAfBgNVHSMEGDAWgBTVnKt2Znbrwe0EgXr6W0jtY52VCTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQCS/dnmCmtgjV7nlmu0vrDeyWHkSI4weGKvhnqIgUXhbsbtbr+AwrMhe0vKHHcKeQpr/IV77dxfLhRsylg2XiLp5hdnGhPnfZ/tTIyeAPloeeWb08AMrUCm71boOinBGikyF/kbkyokul7vcLvbO2XAmVAtYPttdKOXLF1GqU2B9DGORmOIZshS6pRunAchYfsklCdHeNnugHkk8PrqXU+0WaSLsAg65W24bfmVeDBy9el8S0nYDIpc3DGvf8Ldu51pIUd2UpuH0Ss1PTizOiD4DXjSAZUaO9O59dvdHez2wWgXrT9tNsZqHvyyEnNzJC3Mw4KdsRFEnMwy10lCnNe2"));

        Jwk jwk = Jwk.fromValues(values);
        PublicKey publicKey = jwk.getPublicKey();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ik1BSU4iLCJwaS5hdG0iOiI2In0.eyJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIiwiZW1haWwiXSwiY2xpZW50X2lkIjoiQ2Y5ODgxODc5YjQ4MzZkMDFjZmYyNmNkYTFkNTNlNmMzOWUyNzc3NTMiLCJpc3MiOiJpZHBkZWNhdGhsb24ucHJlcHJvZC5vcmciLCJqdGkiOiJSUnF0cXY1dHJXIiwic3ViIjoiWjAxQUVBU1kiLCJ1aWQiOiJaMDFBRUFTWSIsIm9yaWdpbiI6ImNvcnBvcmF0ZSIsInV1aWQiOiIwYWUyMDc2OC04MWQyLTEyNDctODE4MS1lYzU4NDEyODAwNzUiLCJleHAiOjE2NTc2MTEwNDN9.yIjOlI22_ulj5FbfMUeCF6YnsHAlSPTvsQDnLsBtP2lbB5eGd67fYjn5GtyS-bzVeWZrvr1oDu_GC4kzywzjvXitbfcL76chIppovhPa68NQKI3f5RftS3Qs2tWykRFKSYqxt2KBt8rSc18PyFRP5Kn9JwRinELzsaPMOq_2ilmHs7ihcwtaENrnegd6xHffSYArsC-ghmDLuVauoXf50qnnabVAATIwKLIiADmQuV0AscO2NQeKQbRiR04beMA4CWdQ1A0Ib_n8HcFHHwMz6sf40JvlwJT0lMS1PCfCuviWRJxgSYzcdDEHkqP-rAG6wA2YDOFRC-Qz036O7spWPw";

        JWTSigner jwtSigner = JWTSignerUtil.rs256(publicKey);

        boolean verify = JWTUtil.verify(token, jwtSigner);

        System.out.println("verify: " + verify);
    }

    @Test
    void test3() throws JwkException {
        Map<String, Object> values = new HashMap<>();
        values.put("kty", "RSA");
        values.put("kid", "PMIcdjpwdv0-cLiwVcksE0Gxunw");
        values.put("use", "sig");
        values.put("n", "zHwXh91BNog3eZbiFtVOUYdwnyREzV6SqPH-FDgWwG7WWyHkVWjjM8uJYVT2bLY7mIW8pYJ4bFXUDmVxCMncHQNdF4vHCGP5Q2lFei7iZjarY0SZalyaxD0QBuWBwexdaQp8m68KPV9BUfhp6rV0-WDYUTqlRPleWEno8BkY8adPXkJmXjS4ggs-1Oo-FezdSIaOQimkCdCZHxZlotWE0Qucz-7eXJ_h8iEXcQ4Y90m73mXXDOx94i_jgTyWCdMPwW5fbzxQ7x0Mgr4vg2_st6zCH3z1ikCQGHRkk784-Hbsy4hduG9WjiKqDNQG0gzX5jVnKjbfSpuJ5qUFA61IYQ");
        values.put("e", "AQAB");
        values.put("x5c", List.of("MIIEHzCCAwegAwIBAgIJAJra2vrHzcggMA0GCSqGSIb3DQEBCwUAMIGlMQswCQYDVQQGEwJGUjENMAsGA1UECAwETm9yZDEOMAwGA1UEBwwFTGlsbGUxDTALBgNVBAoMBE94aXQxDTALBgNVBAsMBE94aXQxKjAoBgNVBAMMIXByZXByb2RpZHBkZWNhdGhsb24uZGVjYXRobG9uLmNvbTEtMCsGCSqGSIb3DQEJARYec2ltb24uZGVsZXBpZXJyZUBkZWNhdGhsb24uY29tMB4XDTE1MDgxMjE1MDcyOFoXDTQ1MDgwNDE1MDcyOFowgaUxCzAJBgNVBAYTAkZSMQ0wCwYDVQQIDAROb3JkMQ4wDAYDVQQHDAVMaWxsZTENMAsGA1UECgwET3hpdDENMAsGA1UECwwET3hpdDEqMCgGA1UEAwwhcHJlcHJvZGlkcGRlY2F0aGxvbi5kZWNhdGhsb24uY29tMS0wKwYJKoZIhvcNAQkBFh5zaW1vbi5kZWxlcGllcnJlQGRlY2F0aGxvbi5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDMfBeH3UE2iDd5luIW1U5Rh3CfJETNXpKo8f4UOBbAbtZbIeRVaOMzy4lhVPZstjuYhbylgnhsVdQOZXEIydwdA10Xi8cIY/lDaUV6LuJmNqtjRJlqXJrEPRAG5YHB7F1pCnybrwo9X0FR+GnqtXT5YNhROqVE+V5YSejwGRjxp09eQmZeNLiCCz7U6j4V7N1Iho5CKaQJ0JkfFmWi1YTRC5zP7t5cn+HyIRdxDhj3SbveZdcM7H3iL+OBPJYJ0w/Bbl9vPFDvHQyCvi+Db+y3rMIffPWKQJAYdGSTvzj4duzLiF24b1aOIqoM1AbSDNfmNWcqNt9Km4nmpQUDrUhhAgMBAAGjUDBOMB0GA1UdDgQWBBTVnKt2Znbrwe0EgXr6W0jtY52VCTAfBgNVHSMEGDAWgBTVnKt2Znbrwe0EgXr6W0jtY52VCTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQCS/dnmCmtgjV7nlmu0vrDeyWHkSI4weGKvhnqIgUXhbsbtbr+AwrMhe0vKHHcKeQpr/IV77dxfLhRsylg2XiLp5hdnGhPnfZ/tTIyeAPloeeWb08AMrUCm71boOinBGikyF/kbkyokul7vcLvbO2XAmVAtYPttdKOXLF1GqU2B9DGORmOIZshS6pRunAchYfsklCdHeNnugHkk8PrqXU+0WaSLsAg65W24bfmVeDBy9el8S0nYDIpc3DGvf8Ldu51pIUd2UpuH0Ss1PTizOiD4DXjSAZUaO9O59dvdHez2wWgXrT9tNsZqHvyyEnNzJC3Mw4KdsRFEnMwy10lCnNe2"));

        Jwk jwk = Jwk.fromValues(values);
        PublicKey publicKey = jwk.getPublicKey();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ik1BSU4iLCJwaS5hdG0iOiI2In0.eyJzdWIiOiJaMDFBRUFTWSIsImF1ZCI6IkNmOTg4MTg3OWI0ODM2ZDAxY2ZmMjZjZGExZDUzZTZjMzllMjc3NzUzIiwianRpIjoiMVpSczZEVkNWU2xoaDRTRVBxM3IyZSIsImlzcyI6Imh0dHBzOi8vcHJlcHJvZC5pZHBkZWNhdGhsb24ub3h5bGFuZS5jb20iLCJpYXQiOjE2NTc1Mjg4NDQsImV4cCI6MTY1NzUzMDE0NCwiZW50cnlVVUlEIjoiMGFlMjA3NjgtODFkMi0xMjQ3LTgxODEtZWM1ODQxMjgwMDc1IiwicHJlZmVycmVkTGFuZ3VhZ2UiOiJaSCIsInJvbGUiOlsiaWQ9VkFMSURBVEUsaWQ9RlJBTkNISVNFLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9R2xvYmFsLGlkPVNob3BwZXJ0cmFrLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9VXNlcklULGlkPUNvbmZsdWVuY2UsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1DT00yVV9Db250UmV2LGlkPUNPTTJVLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9RVhUX0RBVEFURUFNLGlkPVZQTl9CUkFaSUwsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1SRUFERVIsaWQ9UE9TREFUQSxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVNwb3J0TGVhZGVyLGlkPU15R2FtZSxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVN0cm9uZ0F1dGgsaWQ9UGluZ0lkLGlkPWFwcFJvbGUsb3U9cm9sZSxvdT1yZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9U3RhbmRhcmQsaWQ9ZVRDTyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPWFjY2VzcyxpZD1wZHMsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1Vc2VyLGlkPUJpcmQtT2ZmaWNlLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9QUNDRVNTLGlkPUlQQUMsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1ERUNBU1RPUkVfbmF0aW9uYWxfdXNlcixpZD1ERUNBU1RPUkUsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1iaUl0VG9vbHMsaWQ9T0JJRUUsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1MRUNUVVJFLGlkPUNBVEFMT0dVRUFHRU5DRU1FTlQsaWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1BZG1pbixpZD1UQVRUT08saWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1XZWJBcHBfY2hpZmZyZXNfc2VjdTEsaWQ9V2ViYXBwX2NoaWZmcmVzLGlkPWFwcFJvbGUsb3U9cm9sZSxPVT1SZXBvc2l0b3J5LG89ZGVjYXRobG9uIiwiaWQ9Q1JDU3RvcmVVc2VyLGlkPUNSQyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPVBJWExfVmlzdUJPLGlkPVBJWEwsaWQ9YXBwUm9sZSxvdT1yb2xlLE9VPVJlcG9zaXRvcnksbz1kZWNhdGhsb24iLCJpZD1CYWNrb2ZmaWNlLGlkPURFQ0FHTyxpZD1hcHBSb2xlLG91PXJvbGUsT1U9UmVwb3NpdG9yeSxvPWRlY2F0aGxvbiIsImlkPUJPQWNjZXNzLGlkPURpZ2l0YWxTY3JlZW4saWQ9YXBwUm9sZSxvdT1yb2xlLG91PXJlcG9zaXRvcnksbz1kZWNhdGhsb24iXSwiYyI6IkNOIiwibWFpbCI6ImFkbWluLmVhc3ltZXJjaC5wYXJ0bmVyQGRlY2F0aGxvbi5jb20iLCJkaXNwbGF5TmFtZSI6IkVBU1lNRVJDSCBBZG1pbiIsImdpdmVuTmFtZSI6IkFkbWluIiwiY24iOiJFQVNZTUVSQ0ggQWRtaW4iLCJzaXRldHlwZSI6IkhRIiwidGl0bGUiOiJTZXJ2aWNlIFByb3ZpZGVyIGZvciBJVCIsIm9iamVjdGNsYXNzIjpbInRvcCIsIlBlcnNvbiIsIm9yZ2FuaXphdGlvbmFsUGVyc29uIiwiaW5ldE9yZ1BlcnNvbiIsIm9jRXh0ZW5kZWRQZXJzb24iXSwidXVpZCI6IjU1OWY4OTY5LTIyZDMtNDc5ZC04ZWMxLWI0MzY3MzY3Zjk1YSIsImFsbHNpdGVzIjoiQ05IUUNITEIiLCJ1aWQiOiJaMDFBRUFTWSIsInNpdGUiOiJDTkhRQ0hMQiIsImZlZGVyYXRpb25faWRwIjoiZDEiLCJmYW1pbHlOYW1lIjoiRUFTWU1FUkNIIiwic2l0ZW5hbWUiOiJDSElOQSBMQUIiLCJzbiI6IkVBU1lNRVJDSCIsImNvc3RjZW50ZXIiOiIwMDUwMTA1ODUwMDZDMTA1Iiwiam9ibmFtZSI6IlBSRVNULklORk8iLCJwaS5zcmkiOiJhcWhhSWxON0J3VzhNd3B5ZktkeGhWc3lEN1EuUlZVLnktWDAifQ==.yIjOlI22_ulj5FbfMUeCF6YnsHAlSPTvsQDnLsBtP2lbB5eGd67fYjn5GtyS-bzVeWZrvr1oDu_GC4kzywzjvXitbfcL76chIppovhPa68NQKI3f5RftS3Qs2tWykRFKSYqxt2KBt8rSc18PyFRP5Kn9JwRinELzsaPMOq_2ilmHs7ihcwtaENrnegd6xHffSYArsC-ghmDLuVauoXf50qnnabVAATIwKLIiADmQuV0AscO2NQeKQbRiR04beMA4CWdQ1A0Ib_n8HcFHHwMz6sf40JvlwJT0lMS1PCfCuviWRJxgSYzcdDEHkqP-rAG6wA2YDOFRC-Qz036O7spWPw";

        JWTSigner jwtSigner = JWTSignerUtil.rs256(publicKey);

        boolean verify = JWTUtil.verify(token, jwtSigner);

        System.out.println("verify: " + verify);
    }

    @Test
    public void test4() {
        var key = "-----BEGIN RSA PUBLIC KEY-----\r\n" +
                "MIIBCgKCAQEA2JIa6JwNSy3cwpKdOlPLScP1dPErGpQHr6m9+iv3Go0GOuX8lCOv\r\n" +
                "IBHtT6p8JWwLiev6KdJaqjKcqCVuQ7Jkk7PraArjsAwgOo9UtUO29fxXlejeBdtY\n\n" +
                "nb8aHulPBhiTLXH2ULvlRMWag7M64EmiH/TpSc8D9Ko27BVwmJ2L2i+NOlRshod9\n\n" +
                "KLx0hhQPC5t+w9k2QzuIk6mPypPRr50gu4Ys8reByB0Qwjb3LUaNGyJWQGlFK3Kh\n\n" +
                "/6InmYVoJIjsnJyHCSgouWGiWNGofuvDYZelzgnmz/HMo31FomcVWETp8c5mdosY\n\n" +
                "7DE4Ey7d4DwDkbxKrNqAnzY558JHsOYi+wIDAQAB\n\n" +
                "-----END RSA PUBLIC KEY-----";


    }
}
