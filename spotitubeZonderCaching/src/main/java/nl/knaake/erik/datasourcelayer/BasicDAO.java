package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.datasourcelayer.database.IDatabase;

import javax.inject.Inject;

/**
 * Offers a database to all children
 */
public abstract class BasicDAO {

    @Inject
    private IDatabase db;

    protected IDatabase getDb() {
        return db;
    }
}
