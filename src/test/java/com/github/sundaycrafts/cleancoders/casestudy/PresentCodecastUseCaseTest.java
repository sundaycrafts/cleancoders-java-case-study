package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PresentCodecastUseCaseTest {
    private User user;
    private Codecast codecast;
    private PresentCodecastUseCase useCase;

    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
        user = Context.gateway.save(new User("User"));
        codecast = Context.gateway.save(new Codecast());
        useCase = new PresentCodecastUseCase();
    }

    @Test
    public void userWithoutViewLicense_cannotViewCodecast() throws Exception {
        assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
    }

    @Test
    public void userWithViewLicense_canViewCodecast() throws Exception {
        License viewLicense = new License(user, codecast);
        Context.gateway.save(viewLicense);
        assertTrue(useCase.isLicensedToViewCodecast(user, codecast));
    }

    @Test
    public void userWithViewLicense_cannotViewOtherUsersCodecast() throws Exception {
        User otherUser = Context.gateway.save(new User("otherUser"));
        License viewLicense = new License(user, codecast);
        Context.gateway.save(viewLicense);
        assertFalse(useCase.isLicensedToViewCodecast(otherUser, codecast));
    }
}
