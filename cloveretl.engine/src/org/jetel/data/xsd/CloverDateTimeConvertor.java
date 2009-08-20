
package org.jetel.data.xsd;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;
import org.jetel.exception.DataConversionException;
import org.jetel.metadata.DataFieldMetadata;

/**
 *
 * @author Pavel Pospichal
 */
public class CloverDateTimeConvertor implements IGenericConvertor {

    private static Logger logger = Logger.getLogger(CloverDateTimeConvertor.class);
    
    static {
    	ConvertorRegistry.registerConvertor(new CloverDateTimeConvertor());
    }
    
    public static Date parseXsdDateTimeToDate(String value) throws DataConversionException {
        Date result = null;
        String valueType = Date.class.getName();
        
        try {
            Calendar calendar = DatatypeConverter.parseDateTime(value);
            result = calendar.getTime();    
        } catch(Exception e) {
            if (result != null) valueType = result.getClass().getName();
            logger.fatal("Unable to parse xsd:dateTime to "+valueType+".",e);
            throw new DataConversionException("Unable to parse xsd:dateTime to "+valueType+".", e);
        }
        
        return result;
    }
    
    public static String printDateToXsdDateTime(Date value) throws DataConversionException {
        String result = null;
        String valueType = Date.class.getName();
        
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(value);
            result = DatatypeConverter.printDateTime(calendar);
        } catch(Exception e) {
            if (value != null) valueType = value.getClass().getName();
            logger.fatal("Unable to print "+valueType+" to xsd:dateTime.",e);
            throw new DataConversionException("Unable to print "+valueType+" to xsd:dateTime.", e);
        }
        
        return result;
    }

    public Object parse(String input) throws DataConversionException {
        return parseXsdDateTimeToDate(input);
    }

    public String print(Object obj) throws DataConversionException {
        if (!(obj instanceof Date)) {
            throw new DataConversionException("Unsupported type by convertion: " + obj.getClass().getName());
        }

        return printDateToXsdDateTime((Date) obj);
    }

	public boolean supportsCloverType(String cloverDataTypeCriteria) {
		return DataFieldMetadata.DATETIME_TYPE.equals(cloverDataTypeCriteria) ||
			DataFieldMetadata.DATE_TYPE.equals(cloverDataTypeCriteria) ;
	}

	public boolean supportsExternalSystemType(String externalTypeCriteria) {
		return XSDDataTypes.valueOf(externalTypeCriteria).equals(XSDDataTypes.dateTime);
	}
}
