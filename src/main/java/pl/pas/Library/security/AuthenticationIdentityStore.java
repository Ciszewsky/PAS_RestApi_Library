package pl.pas.Library.security;
import pl.pas.Library.model.User;
import pl.pas.Library.repositories.UserRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApplicationScoped
public class AuthenticationIdentityStore implements IdentityStore {

    @Inject
    private UserRepository userRepository;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            User user = userRepository.get(x -> x.getLogin().equals(usernamePasswordCredential.getCaller())
                    && x.getPassword().equals(usernamePasswordCredential.getPasswordAsString()) && x.isActive());

            return (null != user ? new CredentialValidationResult(user.getLogin(), new HashSet<>(Arrays.asList(user.getAccessLevel()))) : CredentialValidationResult.INVALID_RESULT);
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
}
}