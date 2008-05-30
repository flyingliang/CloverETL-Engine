/*
*    jETeL/Clover - Java based ETL application framework.
*    Copyright (C) 2002-04  David Pavlis <david_pavlis@hotmail.com>
*    
*    This library is free software; you can redistribute it and/or
*    modify it under the terms of the GNU Lesser General Public
*    License as published by the Free Software Foundation; either
*    version 2.1 of the License, or (at your option) any later version.
*    
*    This library is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU    
*    Lesser General Public License for more details.
*    
*    You should have received a copy of the GNU Lesser General Public
*    License along with this library; if not, write to the Free Software
*    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*
*/
package org.jetel.connection.jdbc.specific.impl;

import java.sql.Connection;
import java.sql.Types;

import org.jetel.connection.jdbc.DBConnection;
import org.jetel.connection.jdbc.specific.conn.PostgreConnection;
import org.jetel.exception.JetelException;
import org.jetel.metadata.DataFieldMetadata;

public class PostgreSpecific extends AbstractJdbcSpecific {

	public static PostgreSpecific INSTANCE = new PostgreSpecific();
	
	@Override
	public Connection createSQLConnection(DBConnection connection, OperationType operationType) throws JetelException {
		return new PostgreConnection(connection, operationType);
	}

	public char sqlType2jetel(int sqlType) {
		switch (sqlType) {
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.TINYINT:
			    return DataFieldMetadata.INTEGER_FIELD;
			//-------------------
			case Types.BIGINT:
			    return DataFieldMetadata.LONG_FIELD;
			//-------------------
			case Types.DECIMAL:
				return DataFieldMetadata.DECIMAL_FIELD;
			case Types.DOUBLE:
			case Types.FLOAT:
			case Types.REAL:
			case Types.NUMERIC:
				return DataFieldMetadata.NUMERIC_FIELD;
			//------------------
			case Types.CHAR:
			case Types.LONGVARCHAR:
			case Types.VARCHAR:
			case Types.CLOB:
				return DataFieldMetadata.STRING_FIELD;
			//------------------
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				return DataFieldMetadata.DATE_FIELD;
            //-----------------
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
			case Types.OTHER:
                return DataFieldMetadata.BYTE_FIELD;
			//-----------------
			case Types.BOOLEAN:
			case Types.BIT:
				return DataFieldMetadata.BOOLEAN_FIELD;
			default:
				throw new IllegalArgumentException("Can't handle JDBC.Type :"+sqlType);
		}
	}

}
