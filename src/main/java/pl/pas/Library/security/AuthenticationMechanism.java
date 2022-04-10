package pl.pas.Library.security;

import com.nimbusds.jwt.SignedJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
public class AuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private TokenAndSignatureGeneratorValidate tokenAndSignatureGeneratorValidate;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        if (request.getRequestURL().toString().endsWith("/api/authenticate")) {
            return httpMessageContext.doNothing();
        }

        String headerAuth = request.getHeader("Authorization");
        if (headerAuth == null || !headerAuth.startsWith("Bearer")) {
            return httpMessageContext.responseUnauthorized();
        }

        String jwtToken = headerAuth.substring("Bearer ".length()).trim();

        if (tokenAndSignatureGeneratorValidate.validateJWTSignature(jwtToken)) {
            try {
                SignedJWT token = SignedJWT.parse(jwtToken);
                String login = token.getJWTClaimsSet().getSubject();
                String groups = token.getJWTClaimsSet().getStringClaim("auth");
                Date exp = (Date) (token.getJWTClaimsSet().getClaim("exp"));

                if (new Date().after(exp)) {
                    return httpMessageContext.responseUnauthorized();
                }

                return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
            } catch (ParseException e) {
                e.printStackTrace();
                return httpMessageContext.responseUnauthorized();
            }
        }
        return httpMessageContext.responseUnauthorized();
    }
}
