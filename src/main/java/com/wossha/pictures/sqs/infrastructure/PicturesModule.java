package com.wossha.pictures.sqs.infrastructure;

import com.google.inject.AbstractModule;
import com.wossha.pictures.sqs.infrastructure.s3.DeleteObject;
import com.wossha.pictures.sqs.infrastructure.sqs.removePictureEvent.RemovePictureEventListener;
import com.wossha.pictures.sqs.infrastructure.sqs.savePictureEvent.SavePictureEventListener;

import oracle.jdbc.pool.OracleDataSource;
import com.google.inject.*;
import java.sql.SQLException;
import com.google.common.eventbus.EventBus;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;

public class PicturesModule extends AbstractModule {

	private static final String DATABASE_NAME = System.getenv("DATABASE_NAME");
	private static final String DATABASE_URL = System.getenv("DATABASE_URL");
	private static final String DATABASE_USER = System.getenv("DATABASE_USER");
	private static final String DATABASE_PASSWORD= System.getenv("DATABASE_PASSWORD");
	
    @Override
    protected void configure() {
    	OracleDataSource ds;
		try {
			ds = new oracle.jdbc.pool.OracleDataSource();
			ds.setDatabaseName(DATABASE_NAME);
			ds.setURL(DATABASE_URL);
			ds.setUser(DATABASE_USER);
			ds.setPassword(DATABASE_PASSWORD);
			
	        bind(IDBI.class).toInstance(new DBI(ds));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Provides
    @Singleton
    protected EventBus provideEventBus() {
        EventBus eb = new EventBus("EventBus");
        Injector injector = Guice.createInjector(new PicturesModule());
        
        SavePictureEventListener savePictureEventListenerInstance = injector.getInstance(SavePictureEventListener.class);
        RemovePictureEventListener removePictureEventListenerInstance = injector.getInstance(RemovePictureEventListener.class);
        DeleteObject deleteObject = injector.getInstance(DeleteObject.class);
        
        eb.register(savePictureEventListenerInstance);
        eb.register(removePictureEventListenerInstance);
        eb.register(deleteObject);
        return eb;
    }
}