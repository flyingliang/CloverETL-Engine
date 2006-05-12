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
package org.jetel.data.primitive;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.NumberFormat;

import org.jetel.data.DecimalDataField;
import org.jetel.data.IntegerDataField;
import org.jetel.data.LongDataField;
import org.jetel.data.Numeric;
import org.jetel.data.NumericDataField;


/**
 *  Clover internal decimal value representation.
 * Implements Decimal interface and use BigDecimal type for store value.
 *
 *@author     Martin Zatopek
 *@since      December 1, 2005
 *@see        org.jetel.data.primitive.Decimal
 */
public class HugeDecimal implements Decimal {
    private static int roundingMode = BigDecimal.ROUND_DOWN;
	private BigDecimal value;
	private final int precision;
	private final int scale;
	private boolean nan;

    private static BigInteger TENPOWERS[] = {BigInteger.ONE,
            BigInteger.valueOf(10),       BigInteger.valueOf(100),
            BigInteger.valueOf(1000),     BigInteger.valueOf(10000),
            BigInteger.valueOf(100000),   BigInteger.valueOf(1000000),
            BigInteger.valueOf(10000000), BigInteger.valueOf(100000000),
            BigInteger.valueOf(1000000000)};

	public HugeDecimal(BigDecimal value, int precision, int scale, boolean nan) {
		this.value = value;
		this.precision = precision;
		this.scale = scale;
		this.nan = nan;
	}
	
	/**
	 * @see org.jetel.data.primitive.Decimal#getPrecision()
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#getScale()
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#createCopy()
	 */
	public Decimal createCopy() {
		return new HugeDecimal(value, precision, scale, nan);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#setValue(org.jetel.data.primitive.Decimal)
	 */
	public void setValue(Decimal decimal) {
		if(decimal == null || decimal.isNaN()) {
			setNaN(true);
			return;
		}

        value = decimal.getBigDecimal();
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + decimal + ")");
        }
        setNaN(false);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#setValue(double)
	 */
	public void setValue(double _value) {
		if(_value == Double.NaN) {
			setNaN(true);
			return;
		}
		value = new BigDecimal(Double.toString(_value)); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + _value + ")");
        }
		setNaN(false);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#setValue(int)
	 */
	public void setValue(int _value) {
		if(_value == Integer.MIN_VALUE) {
			setNaN(true);
			return;
		}
		value = new BigDecimal(_value);
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + _value + ")");
        }
		setNaN(false);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#setValue(long)
	 */
	public void setValue(long _value) {
		if(_value == Long.MIN_VALUE) {
			setNaN(true);
			return;
		}
		value = new BigDecimal(_value);
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + _value + ")");
        }
		setNaN(false);
	}

    /**
     * @see org.jetel.data.Numeric#setValue(org.jetel.data.Numeric)
     */
    public void setValue(Numeric _value) {
        if(_value.isNull()) {
            setNaN(true);
            return;
        }
        value = _value.getBigDecimal();
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + _value + ")");
        }
        setNaN(false);
    }

    /**
     * @see org.jetel.data.primitive.Decimal#setValue(java.math.BigDecimal)
     */
    public void setValue(BigDecimal _value) {
        value = _value;
        if(!satisfyPrecision()) {
            setNaN(true);
            throw new NumberFormatException("Number is out of available precision. (" + _value + ")");
        }
        setNaN(false);
    }
    
	/**
	 * @see org.jetel.data.primitive.Decimal#getDouble()
	 */
	public double getDouble() {
        if(isNaN()) {
            return Double.NaN;
        }
		return value.doubleValue();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#getInt()
	 */
	public int getInt() {
        if(isNaN()) {
            return Integer.MIN_VALUE;
        }
		return value.intValue();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#getLong()
	 */
	public long getLong() {
        if(isNaN()) {
            return Long.MIN_VALUE;
        }
		return value.longValue();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#getBigDecimal()
	 */
	public BigDecimal getBigDecimal() {
		if(isNaN()) {
			return null;
		}
		return value;
	}

    /**
     * @see org.jetel.data.primitive.Decimal#getBigDecimalOutput()
     */
    public BigDecimal getBigDecimalOutput() {
        if(isNaN()) {
            return null;
        }
        if(!satisfyPrecision()) return null;
        
        if(value.scale() == scale)
            return value;
        else
            return value.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * @see org.jetel.data.Numeric#getDecimal()
     */
    public Decimal getDecimal() {
        return createCopy();
    }

    /**
     * @see org.jetel.data.Numeric#getDecimal(int, int)
     */
    public Decimal getDecimal(int precision, int scale) {
        return DecimalFactory.getDecimal(this, precision, scale);
    }

    /**
     * @see org.jetel.data.Numeric#duplicateNumeric()
     */
    public Numeric duplicateNumeric() {
        return createCopy();
    }

    /**
	 * Number of digit in value (BigDecimal).
	 * @return
	 */
	public static int precision(BigInteger intVal) { //TODO move this static method in some utils class
        if (intVal.signum() == 0)       // 0 is one decimal digit
            return 1;
        // we have a nonzero magnitude
        BigInteger work = intVal;
        int digits = 0;                 // counter
        for (;work.bitLength()>32;) {
            // here when more than one integer in the magnitude; divide
            // by a billion (reduce by 9 digits) and try again
            work = work.divide(TENPOWERS[9]);
            digits += 9;
            if (work.signum() == 0)     // the division was exact
                return digits;          // (a power of a billion)
        }
        // down to a simple nonzero integer
        digits += intLength(work.intValue());
        // System.out.println("digitLength... "+this+"  ->  "+digits);
        return digits;
	}
	
    /**
     * Returns the length of an unsigned <tt>int</tt>, in decimal digits.
     * @param i the <tt>int</tt> (treated as unsigned)
     * @return the length of the unscaled value, in decimal digits
     */
    public static int intLength(int i) { //TODO move this static method in some utils class
        int digits;
        if (i < 0) {            // 'negative' is 10 digits unsigned
            i *= -1;
        } 
        // positive integer
        // binary search, weighted low (maximum 4 tests)
        if (i < 10000) {
            if (i < 100) {
                if (i < 10) digits = 1;
                else digits = 2;
            } else {
                if (i < 1000) digits = 3;
                else digits = 4;
            }
        } else {
            if (i < 1000000) {
                if (i < 100000) digits = 5;
                else digits = 6;
            } else {
                if (i < 100000000) {
                    if (i < 10000000) digits = 7;
                    else digits = 8;
                } else {
                    if (i < 1000000000) digits = 9;
                    else digits = 10;
                }
            }
        }
        return digits;
    }
    
    /**
     * Returns the length of an unsigned <tt>long</tt>, in decimal digits.
     * @param i the <tt>long</tt> (treated as unsigned)
     * @return the length of the unscaled value, in decimal digits
     */
    public static int longLength(long l) { //TODO move this static method in some utils class
        int digits;
        if (l < 0) {            // 'negative' is 10 digits unsigned
            l *= -1;
        } 
        // positive long
        // binary search, weighted low (maximum 5 tests)
        if (l < 1000000000) {
            if (l < 10000) {
                if (l < 100) {
                    if (l < 10) digits = 1;
                    else digits = 2;
                }
                else {
                    if (l < 1000) digits = 3;
                    else digits = 4;
                }
            } else {
                if (l < 1000000) {
                    if (l < 100000) digits = 5;
                    else digits = 6;
                } else {
                    if (l < 10000000) digits = 7;
                    else {
                        if (l < 100000000) digits = 8;
                        else digits = 9;
                    }
                }
            }
        } else {
            if (l < 100000000000000L) {
                if (l < 100000000000L) {
                    if (l < 10000000000L) digits = 10;
                    else digits = 11;
                } else {
                    if (l < 1000000000000L) digits = 12;
                    else {
                        if (l < 10000000000000L) digits = 13;
                        else digits = 14;
                    }
                }
            } else {
                if (l < 10000000000000000L) {
                    if (l < 1000000000000000L) digits = 15;
                    else digits = 16;
                } else {
                    if (l < 100000000000000000L) {
                        digits = 17;
                    } else {
                        if (l < 1000000000000000000L) digits = 18;
                        else digits = 19;
                    }
                }
            }
        }
        return digits;
    }

	/**
	 * @see org.jetel.data.primitive.Decimal#setNaN(boolean)
	 */
	public void setNaN(boolean isNaN) {
		nan = isNaN;
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#isNaN()
	 */
	public boolean isNaN() {
		return nan;
	}

    /**
     * @see org.jetel.data.Numeric#isNull()
     */
    public boolean isNull() {
        return nan;
    }

	/**
	 * @see org.jetel.data.primitive.Decimal#add(org.jetel.data.Numeric)
	 */
	public void add(Numeric a) {
        if(isNull()) return;
        if(a.isNull()) setNaN(true);
		if(a instanceof IntegerDataField || a instanceof CloverInteger) {
            value = value.add(BigDecimal.valueOf(a.getInt()));
        } else  if(a instanceof LongDataField || a instanceof CloverLong) {
			value = value.add(BigDecimal.valueOf(a.getLong()));
		} else if(a instanceof NumericDataField || a instanceof CloverDouble) {
			value = value.add(new BigDecimal(Double.toString(a.getDouble()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if(a instanceof DecimalDataField) {
            value = value.add(a.getDecimal().getBigDecimal());
        } else if(a instanceof Decimal) {
            value = value.add(a.getBigDecimal());
		} else {
			throw new RuntimeException("Unsupported class of parameter 'add' operation (" + a.getClass().getName() + ").");
		}
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#sub(org.jetel.data.Numeric)
	 */
	public void sub(Numeric a) {
        if(isNull()) return;
        if(a.isNull()) setNaN(true);
		if(a instanceof IntegerDataField || a instanceof CloverInteger) {
            value = value.subtract(BigDecimal.valueOf(a.getInt()));
        } else if(a instanceof LongDataField || a instanceof CloverLong) {
			value = value.subtract(BigDecimal.valueOf(a.getLong()));
		} else if(a instanceof NumericDataField || a instanceof CloverDouble) {
			value = value.subtract(new BigDecimal(Double.toString(a.getDouble()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if(a instanceof DecimalDataField) {
			value = value.subtract(a.getDecimal().getBigDecimal());
        } else if(a instanceof Decimal) {
            value = value.subtract(a.getBigDecimal());
		} else {
			throw new RuntimeException("Unsupported class of parameter 'sub' operation (" + a.getClass().getName() + ").");
		}
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#mul(org.jetel.data.Numeric)
	 */
	public void mul(Numeric a) {
        if(isNull()) return;
        if(a.isNull()) setNaN(true);
		if(a instanceof IntegerDataField || a instanceof CloverInteger) {
            value = value.multiply(BigDecimal.valueOf(a.getInt()));
        } else if(a instanceof LongDataField || a instanceof CloverLong) {
			value = value.multiply(BigDecimal.valueOf(a.getLong()));
		} else if(a instanceof NumericDataField || a instanceof CloverDouble) {
			value = value.multiply(new BigDecimal(Double.toString(a.getDouble()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if(a instanceof DecimalDataField) {
			value = value.multiply(a.getDecimal().getBigDecimal());
        } else if(a instanceof Decimal) {
            value = value.multiply(a.getBigDecimal());
		} else {
			throw new RuntimeException("Unsupported class of parameter 'mul' operation (" + a.getClass().getName() + ").");
		}
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#div(org.jetel.data.Numeric)
	 */
	public void div(Numeric a) {
        if(isNull()) return;
        if(a.isNull()) setNaN(true);
		if(a instanceof IntegerDataField || a instanceof CloverInteger) {
            value = value.divide(BigDecimal.valueOf(a.getInt()), roundingMode);
        } else if(a instanceof LongDataField || a instanceof CloverLong) {
			value = value.divide(BigDecimal.valueOf(a.getLong()), roundingMode);
		} else if(a instanceof NumericDataField || a instanceof CloverDouble) {
			value = value.divide(new BigDecimal(Double.toString(a.getDouble())), roundingMode); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if(a instanceof DecimalDataField) {
			value = value.divide(a.getDecimal().getBigDecimal(), roundingMode);
        } else if(a instanceof Decimal) {
            value = value.divide(a.getBigDecimal(), roundingMode);
		} else {
			throw new RuntimeException("Unsupported class of parameter 'div' operation (" + a.getClass().getName() + ").");
		}
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#abs()
	 */
	public void abs() {
        if(isNull()) return;
		value = value.abs();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#mod(org.jetel.data.Numeric)
	 */
	public void mod(Numeric a) {
        if(isNull()) return;
        if(a.isNull()) setNaN(true);
		if(a instanceof IntegerDataField || a instanceof CloverInteger) {
            value = remainder(BigDecimal.valueOf(a.getInt()));
        } else if(a instanceof LongDataField || a instanceof CloverLong) {
			value = remainder(BigDecimal.valueOf(a.getLong()));
		} else if(a instanceof NumericDataField || a instanceof CloverDouble) {
			value = remainder(new BigDecimal(Double.toString(a.getDouble()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if(a instanceof DecimalDataField) {
			value = remainder(a.getDecimal().getBigDecimal());
        } else if(a instanceof Decimal) {
            value = remainder(a.getBigDecimal());
		} else {
			throw new RuntimeException("Unsupported class of parameter 'mod' operation (" + a.getClass().getName() + ").");
		}
	}

	/**
	 * Returns a BigDecimal whose value is (this % divisor).
	 * @param divisor value by which this BigDecimal is to be divided
	 * @return this % divisor
	 */
	private BigDecimal remainder(BigDecimal divisor) {
	    return value.subtract(value.divide(divisor, 0, roundingMode).multiply(divisor));
	}
	
	/**
	 * @see org.jetel.data.primitive.Decimal#neg()
	 */
	public void neg() {
        if(isNull()) return;
		value = value.negate();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#serialize(java.nio.ByteBuffer)
	 */
	public void serialize(ByteBuffer byteBuffer) {
		if(isNaN()) {
			byteBuffer.put((byte) 0);
			return;
		}
        byte[] bytes = value.unscaledValue().toByteArray();
		byteBuffer.put((byte) bytes.length);
		byteBuffer.put(bytes);
		byteBuffer.putInt(value.scale());
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#deserialize(java.nio.ByteBuffer)
	 */
	public void deserialize(ByteBuffer byteBuffer) {
		byte size = byteBuffer.get();
		if(size == 0) {
			setNaN(true);
			return;
		}
		byte[] unscaledVal = new byte[size];
		byteBuffer.get(unscaledVal);
		value = new BigDecimal(new BigInteger(unscaledVal), byteBuffer.getInt());
		setNaN(false);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#getSizeSerialized()
	 */
	public int getSizeSerialized() {
		if(isNaN()) {
			return 1;
		}
		return ((value.unscaledValue().bitLength() / 8) + 1) + 4 + 1; //BigInteger size + scale(4) + size of serialized form(1)
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#toString(java.text.NumberFormat)
	 */
	public String toString(NumberFormat numberFormat) {
		if(isNaN()) {
			return "";
		}
		BigDecimal bd = getBigDecimalOutput();
		if(bd == null)
			return "";
		else return bd.toString();
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#toCharBuffer(java.text.NumberFormat)
	 */
	public CharBuffer toCharBuffer(NumberFormat numberFormat) {
		if(isNaN()) {
			return CharBuffer.allocate(0);
		}
		return CharBuffer.wrap(toString(numberFormat));
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#fromString(java.lang.String, java.text.NumberFormat)
	 */
	public void fromString(String string, NumberFormat numberFormat) {
		if(string == null || string.length() == 0) {
			setNaN(true);
            return;
		}
		value = new BigDecimal(string);
		setNaN(false);
	}

	/**
	 * @see org.jetel.data.primitive.Decimal#fromCharBuffer(java.nio.CharBuffer, java.text.NumberFormat)
	 */
	public void fromCharBuffer(CharBuffer buffer, NumberFormat numberFormat) {
		if(buffer == null || buffer.length() == 0) {
			setNaN(true);
            return;
		}
		value = new BigDecimal(buffer.toString());
		setNaN(false);
	}

    public int compareTo(Numeric value) {
        if (isNull()) {
            return -1;
        }else if (value.isNull()) {
            return 1;
        }else {
            return compareTo((Object) value.getDecimal());
        }
    }

	/**
	 * @see org.jetel.data.primitive.Decimal#compareTo(java.lang.Object)
	 */
	public int compareTo(Object obj) {
		if (obj==null) return 1;
		if (isNaN()) return -1;
	    
		if (obj instanceof Decimal) {
			return value.compareTo(((Decimal) obj).getBigDecimal());
		} else if (obj instanceof Integer) {
            return value.compareTo(BigDecimal.valueOf(((Integer) obj).intValue()));
        } else if(obj instanceof Long) {
			return value.compareTo(BigDecimal.valueOf(((Long) obj).longValue()));
		} else if (obj instanceof Double) {
			return value.compareTo(new BigDecimal(Double.toString(((Double) obj).doubleValue()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
		} else if (obj instanceof IntegerDataField) {
            return value.compareTo(BigDecimal.valueOf(((IntegerDataField) obj).getInt()));
        } else if(obj instanceof LongDataField) {
			return value.compareTo(BigDecimal.valueOf(((LongDataField) obj).getLong()));
		} else if (obj instanceof NumericDataField) {
			return value.compareTo(new BigDecimal(Double.toString(((NumericDataField) obj).getDouble()))); //FIXME in java 1.5 call BigDecimal.valueof(a.getDouble()) - in actual way may be in result some inaccuracies
        } else if (obj instanceof DecimalDataField) {
            return compareTo(((DecimalDataField) obj).getValue());
		} else throw new ClassCastException("Can't compare this DecimalDataField and " + obj.getClass().getName());
	}

    /**
     * Check if stored value is in dimension defined by precision.
     * @return true if value is shorter than precision; false else
     */
    public boolean satisfyPrecision() {
        if(isNaN()) return true;
        return !(HugeDecimal.precision(value.unscaledValue()) > precision);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Numeric)
            return compareTo((Numeric) obj) == 0;
        else return false;
    }
    
    public int hashCode() {
        if(isNaN()) return Integer.MIN_VALUE; //FIXME ???
        return value.hashCode();
    }
}
