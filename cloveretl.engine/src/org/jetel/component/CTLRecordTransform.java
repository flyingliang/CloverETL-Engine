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
package org.jetel.component;

import java.util.Properties;

import org.jetel.ctl.CTLAbstractTransform;
import org.jetel.ctl.CTLEntryPoint;
import org.jetel.ctl.TransformLangExecutorRuntimeException;
import org.jetel.data.DataRecord;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.TransformException;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.util.MiscUtils;

/**
 * Base class of all Java transforms generated by CTL-to-Java compiler from CTL transforms in Reformat-like and
 * Joiner-like components.
 *
 * @author Michal Tomcanyi, Javlin a.s. &lt;michal.tomcanyi@javlin.cz&gt;
 * @author Martin Janik, Javlin a.s. &lt;martin.janik@javlin.eu&gt;
 *
 * @version 22nd June 2010
 * @created 28th April 2009
 *
 * @see RecordTransform
 */
public abstract class CTLRecordTransform extends CTLAbstractTransform implements RecordTransform {

	/** Input data records used for transform, or <code>null</code> if not accessible. */
	private DataRecord[] inputRecords = null;
	/** Output data records used for transform, or <code>null</code> if not accessible. */
	private DataRecord[] outputRecords = null;

	@Override
	public final boolean init(Properties parameters, DataRecordMetadata[] sourceRecordsMetadata,
			DataRecordMetadata[] targetRecordsMetadata) throws ComponentNotReadyException {
		globalScopeInit();

		return initDelegate();
	}

	/**
	 * Called by {@link #init(Properties, DataRecordMetadata[], DataRecordMetadata[])} to perform user-specific
	 * initialization defined in the CTL transform. The default implementation does nothing, may be overridden
	 * by the generated transform class.
	 *
	 * @return <code>true</code> on success, <code>false</code> otherwise
	 *
	 * @throws ComponentNotReadyException if the initialization fails
	 */
	@CTLEntryPoint(name = RecordTransformTL.INIT_FUNCTION_NAME, required = false)
	protected boolean initDelegate() throws ComponentNotReadyException {
		// does nothing and succeeds by default, may be overridden by generated transform classes
		return true;
	}

	@Override
	public final int transform(DataRecord[] sources, DataRecord[] targets) throws TransformException {
		int result = 0;

		// both input and output records are accessible within the transform() function
		inputRecords = sources;
		outputRecords = targets;

		try {
			result = transformDelegate();
		} catch (ComponentNotReadyException exception) {
			// the exception may be thrown by lookups, sequences, etc.
			throw new TransformException("Generated transform class threw an exception!", exception);
		}

		// make both input and output records inaccessible again
		inputRecords = null;
		outputRecords = null;

		return result;
	}

	/**
	 * Called by {@link #transform(DataRecord[], DataRecord[])} to transform data records in a user-specific way
	 * defined in the CTL transform. Has to be overridden by the generated transform class.
	 *
	 * @throws ComponentNotReadyException if some internal initialization failed
	 * @throws TransformException if an error occurred
	 */
	@CTLEntryPoint(name = "transform", required = true)
	protected abstract int transformDelegate() throws ComponentNotReadyException, TransformException;

	@Override
	public int transformOnError(Exception exception, DataRecord[] sources, DataRecord[] targets)
			throws TransformException {
		int result = 0;

		// both input and output records are accessible within the transformOnError() function
		inputRecords = sources;
		outputRecords = targets;

		try {
			result = transformOnErrorDelegate(exception.getMessage(), MiscUtils.stackTraceToString(exception));
		} catch (UnsupportedOperationException ex) {
			// no custom error handling implemented, throw an exception so the transformation fails
			throw new TransformException("Transform failed!", exception);
		} catch (ComponentNotReadyException ex) {
			// the exception may be thrown by lookups, sequences, etc.
			throw new TransformException("Generated transform class threw an exception!", ex);
		}

		// make both input and output records inaccessible again
		inputRecords = null;
		outputRecords = null;

		return result;
	}

	/**
	 * Called by {@link #transformOnError(Exception, DataRecord[], DataRecord[])} to transform data records in
	 * a user-specific way defined in the CTL transform. May be overridden by the generated transform class.
	 * Throws <code>UnsupportedOperationException</code> by default.
	 *
	 * @param errorMessage an error message of the error message that occurred
	 * @param stackTrace a stack trace of the error message that occurred
	 *
	 * @throws ComponentNotReadyException if some internal initialization failed
	 * @throws TransformException if an error occurred
	 */
	@CTLEntryPoint(name = RecordTransformTL.TRANSFORM_ON_ERROR_FUNCTION_NAME, parameterNames = {
			RecordTransformTL.ERROR_MESSAGE_PARAM_NAME, RecordTransformTL.STACK_TRACE_PARAM_NAME }, required = false)
	protected int transformOnErrorDelegate(String errorMessage, String stackTrace)
			throws ComponentNotReadyException, TransformException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void signal(Object signalObject) {
		// does nothing
	}

	@Override
	public final Object getSemiResult() {
		return null;
	}

	@Override
	protected final DataRecord getInputRecord(int index) {
		if (inputRecords == null) {
			throw new TransformLangExecutorRuntimeException(INPUT_RECORDS_NOT_ACCESSIBLE);
		}

		if (index < 0 || index >= inputRecords.length) {
			throw new TransformLangExecutorRuntimeException(new Object[] { index }, INPUT_RECORD_NOT_DEFINED);
		}

		return inputRecords[index];
	}

	@Override
	protected final DataRecord getOutputRecord(int index) {
		if (outputRecords == null) {
			throw new TransformLangExecutorRuntimeException(OUTPUT_RECORDS_NOT_ACCESSIBLE);
		}

		if (index < 0 || index >= outputRecords.length) {
			throw new TransformLangExecutorRuntimeException(new Object[] { index }, OUTPUT_RECORD_NOT_DEFINED);
		}

		return outputRecords[index];
	}

}
