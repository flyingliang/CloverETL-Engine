/*
 * jETeL/CloverETL - Java based ETL application framework.
 * Copyright (c) Javlin, a.s. (info@cloveretl.com)
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jetel.connection.jdbc.driver;

import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetel.connection.jdbc.specific.JdbcSpecific;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.util.classloader.GreedyURLClassLoader;
import org.jetel.util.string.StringUtils;

/**
 * JDBC driver represents a JDBC driver. Can be create based on JDBC driver description or by all
 * necessary attributes. Method getDriver() is root knowledge of this class.
 * 
 * @author Martin Zatopek (martin.zatopek@javlinconsulting.cz)
 *         (c) Javlin Consulting (www.javlinconsulting.cz)
 *
 * @created 14.9.2007
 */
public class JdbcDriver {
    private static Log logger = LogFactory.getLog(JdbcDriver.class);
    
    private static Map<JdbcDriverDescription, JdbcDriver> driversCache = new HashMap<JdbcDriverDescription, JdbcDriver>();
    
    /**
     * Identifier of the JDBC driver.
     */
    private String database;
    
    /**
     * Name of this JDBC driver is used for communication with user - for example in the clover.GUI.
     */
    private String name;
    
    /**
     * Name of the class, which implements java.sql.Driver interface.
     */
    private String dbDriver;
    
    /**
     * Semicolon delimited list of jar/zip libraries of this JDBC driver.
     */
    private URL[] driverLibraries;

    /**
     * Jdbc specific associated by default with this jdbc driver.
     */
    private JdbcSpecific jdbcSpecific;

    /**
     * Custom connection properties.  
     */
    private Properties properties;

    /**
     * Class loader used to get instance of java.sql.Driver class.
     */
    private ClassLoader classLoader;
    private Driver driver;
        
    /**
     * Constructor.
     * @param jdbcDriverDescription
     * @throws ComponentNotReadyException
     */
    private JdbcDriver(JdbcDriverDescription jdbcDriverDescription) throws ComponentNotReadyException {
    	this(jdbcDriverDescription.getDatabase(),
    			jdbcDriverDescription.getName(),
    			jdbcDriverDescription.getDbDriver(),
    			jdbcDriverDescription.getDriverLibraryURLs(),
    			jdbcDriverDescription.getJdbcSpecific(),
    			jdbcDriverDescription.getProperties());
    }

    /**
     * Constructor.
     * @param database
     * @param name
     * @param dbDriver
     * @param driverLibraries
     * @param jdbcSpecific
     * @throws ComponentNotReadyException
     */
    public JdbcDriver(String database, String name, String dbDriver, URL[] driverLibraries, JdbcSpecific jdbcSpecific, Properties properties) throws ComponentNotReadyException {
    	this.database = database;
    	this.name = name;
    	this.dbDriver = dbDriver;
    	this.driverLibraries = driverLibraries;
    	this.jdbcSpecific = jdbcSpecific;
    	this.properties = properties;
    	
    	prepareClassLoader();
    	prepareDriver();
    }

    /**
     * @return identifier of this jdbc driver. It is usually name of targeted database.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @return human readable name of jdbc driver.
     */
    public String getName() {
        return !StringUtils.isEmpty(name) ? name : database;
    }

    /**
     * @return jdbc specific associated by default with this jdbc driver
     */
    public JdbcSpecific getJdbcSpecific() {
    	return jdbcSpecific;
    }
    
    /**
     * @return custom connection properties
     */
    public Properties getProperties() {
    	Properties result = new Properties();
    	if (properties != null) {
    		result.putAll(properties);
    	}
    	return result;
    }
    
    /**
     * @return class loader, which is used to create java.sql.Driver instance, created in getDriver() method
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }
    
    /**
     * @return type of associated result set
     * @throws ComponentNotReadyException
     */
    public int getResultSetType() throws ComponentNotReadyException {
		Class<?> typesClass;
		try {
			typesClass = getClassLoader().loadClass(getJdbcSpecific().getTypesClassName());
		} catch (ClassNotFoundException e) {
			throw new ComponentNotReadyException("Invalid Types class name in jdbc specific: " + getJdbcSpecific().getTypesClassName(), e);
		}
		try {
			return typesClass.getField(getJdbcSpecific().getResultSetParameterTypeField()).getInt(null);
		} catch (Exception e) {
			throw new ComponentNotReadyException("Invalid ResultSet type field name in jdbc specific: " + getJdbcSpecific().getResultSetParameterTypeField(), e);
		}
    }
    
    /**
     * That is major method of this class. Returns a java.sql.Driver represented by this entity.
     */
    public Driver getDriver() {
    	return driver;
    }

    private void prepareClassLoader() throws ComponentNotReadyException {
        if(driverLibraries != null && driverLibraries.length > 0) {
            classLoader = new GreedyURLClassLoader(driverLibraries, JdbcDriver.class.getClassLoader());
        } else {
            classLoader = JdbcDriver.class.getClassLoader();
        }
    }
    
    private void prepareDriver() throws ComponentNotReadyException {
        try {
            driver = (Driver) Class.forName(dbDriver, true, getClassLoader()).newInstance();
        } catch (ClassNotFoundException ex1) {
            throw new ComponentNotReadyException("Cannot create JDBC driver '" + getName() + "'. Can not find class: " + ex1.getMessage(), ex1);
        } catch (Exception ex1) {
            throw new ComponentNotReadyException("Cannot create JDBC driver '" + getName() + "'. General exception: " + ex1.getMessage(), ex1);
        }
        if (driver == null)
            throw new ComponentNotReadyException("Cannot create JDBC driver '" + getName() + "'. No driver found. " + dbDriver );
    }

    /**
     * Explicitly removes drivers loaded by classLoader created by this instance.
     * Prevents from "java.lang.OutOfMemoryError: PermGen space"
     */
	@SuppressWarnings("unchecked")
	public void free() {
		if (this.classLoader == JdbcDriver.class.getClassLoader())
			return;
		// process only classLoaders created by this instance
		// unload each driver loaded by my classLoader
		if (driver.getClass().getClassLoader() == classLoader){
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			} catch (SecurityException e2) {
				// thrown by Sybase driver
				logger.warn("SecurityException while DriverManager.deregisterDriver() message:" + e2.getMessage());
			}
		}
		/*
		for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
			Driver driver = (Driver) e.nextElement();
			if (driver.getClass().getClassLoader() == classLoader) {
				try {
					DriverManager.deregisterDriver(driver);
				} catch (SQLException e1) {
					logger.error(e1.getMessage(), e1);
				}
			}
		}// for
		*/
	}

	/**
	 * Factory method for creating a JdbcDriver based on a JdbcDriverDescription.
	 * @param jdbcDriverDescription
	 * @return
	 * @throws ComponentNotReadyException
	 */
	public static JdbcDriver createInstance(JdbcDriverDescription jdbcDriverDescription) throws ComponentNotReadyException {
		JdbcDriver result = driversCache.get(jdbcDriverDescription);
		if (result != null) {
			return result;
		} else {
			result = new JdbcDriver(jdbcDriverDescription);
			driversCache.put(jdbcDriverDescription, result);
			return result;
		}
	}
	
}
