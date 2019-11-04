package nl.knaake.erik.restservicelayer;

import javax.inject.Inject;

/**
 * Basic class that contains a sessionHandler, children of this class can query sessions
 * */
public class EndpointWithSession {
    @Inject
    private ISessionHandler sessionHandler;

    /**
     * Get a sessionhandler that can validate and generate sessions
     * @return a sessionhandler that can validate and generate sessions
     */
    protected ISessionHandler getSessionHandler() {
        return sessionHandler;
    }
}
