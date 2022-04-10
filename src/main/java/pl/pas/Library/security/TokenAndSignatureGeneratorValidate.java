package pl.pas.Library.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import pl.pas.Library.model.SignableEntity;
import pl.pas.Library.model.User;

import java.text.ParseException;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.CredentialValidationResult;

@ApplicationScoped
public class TokenAndSignatureGeneratorValidate {

    //MOVE TO CONFIG!
    private final long TIMEOUT = 15 * 60 * 1000;
    private final String sharedSecret = "9KGt-kr-zlgjYC_WCUDLQG65cRWtxOpMP9oJlLjo5DUDiirg_yxzdXtTjFH5bY5LTMU0tyhUR7DfIHdSiiDtQ1FQ8jnenAUY1dEmz1d7v43m1htP52Ujv_gaYODXoMnELNO26UTRCq3tszelAga6xIyDcrFyBq0d4YYrztOC795uNgwNJGDyrQMSW6b-CA8QlHCxdodWKYVUKT3QCJ4F_22WUNNs0qvraIBeNK9qa-FAWHU5mo1X5ZPCbO0cIRHXZnMK4g_puswFVVBpYFaclxaMO2ugDpEq8kISp69k64mkHp7Ya9Y_OoVxRQaStgibywX_I2um9c2qNO36o6SQKA";

    public String getJWT(CredentialValidationResult cred) {
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(sharedSecret);

            // Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(cred.getCallerPrincipal().getName())
                    .claim("auth", String.join(",", cred.getCallerGroups()))
                    .issuer("PAS")
                    .expirationTime(new Date(new Date().getTime() + TIMEOUT))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Apply the HMAC protection
            signedJWT.sign(signer);

            return signedJWT.serialize();


        } catch (JOSEException e) {
            return "FAILURE";
        }
    }

    public boolean validateJWTSignature(String value) {
        if (value == null || value.isEmpty()) return false;
        try {

            SignedJWT signedJWT = SignedJWT.parse(value);
            JWSVerifier verifier = new MACVerifier(sharedSecret);
            return signedJWT.verify(verifier);

        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    //metody do podpisu usera

    public String calculateUserSignature(SignableEntity signableEntity) {
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(sharedSecret);

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(signableEntity.getPayload()));

            // Apply the HMAC protection
            jwsObject.sign(signer);

            return jwsObject.serialize();

        } catch (JOSEException e) {
            return "FAILURE";
        }
    }

    public boolean validateUserSignature(String value) {
        if (value == null || value.isEmpty()) return false;
        try {
            JWSObject jwsObject = JWSObject.parse(value);
            JWSVerifier jwsVerifier = new MACVerifier(sharedSecret);
            return jwsObject.verify(jwsVerifier);

        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    public boolean verifySignatureIntefrity(String value, SignableEntity signableEntity) {
        if (value == null || value.isEmpty()) return false;
        try {
            System.out.println("dto: "+signableEntity.getPayload());
            String valueFromHeader = JWSObject.parse(value).getPayload().toString();

            String valueFromEntity = signableEntity.getPayload();


            return validateUserSignature(value) && valueFromHeader.equals(valueFromEntity);

        } catch (ParseException e) {
            return false;
        }
    }


}
