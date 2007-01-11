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
// FILE: c:/projects/jetel/org/jetel/graph/Node.java

package org.jetel.graph;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.jetel.data.DataRecord;
import org.jetel.enums.EnabledEnum;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.ConfigurationProblem;
import org.jetel.exception.ConfigurationStatus;
import org.jetel.exception.TransformException;
import org.jetel.exception.XMLConfigurationException;
import org.jetel.exception.ConfigurationStatus.Priority;
import org.jetel.exception.ConfigurationStatus.Severity;
import org.jetel.graph.runtime.CloverRuntime;
import org.jetel.graph.runtime.ErrorMsgBody;
import org.jetel.graph.runtime.Message;
import org.jetel.metadata.DataRecordMetadata;
import org.w3c.dom.Element;



/**
 *  A class that represents atomic transformation task. It is a base class for
 *  all kinds of transformation components.
 *
 *@author      D.Pavlis
 *@created     January 31, 2003
 *@since       April 2, 2002
 *@see         org.jetel.component
 *@revision    $Revision$
 */
public abstract class Node extends GraphElement implements Runnable {

    protected Thread nodeThread;
    protected EnabledEnum enabled;
    protected int passThroughInputPort;
    protected int passThroughOutputPort;
    
    
	protected TreeMap outPorts;
	protected TreeMap inPorts;

	protected OutputPort logPort;

	protected volatile boolean runIt = true;

	protected Result runResult;
    protected Throwable resultException;
	
    protected Phase phase;

    // buffered values
    protected List outPortList;
    protected OutputPort[] outPortsArray;
    protected int outPortsSize;
    
    
	/**
	 *  Various PORT kinds identifiers
	 *
	 *@since    August 13, 2002
	 */
	public final static char OUTPUT_PORT = 'O';
	/**  Description of the Field */
	public final static char INPUT_PORT = 'I';
	/**  Description of the Field */
	public final static char LOG_PORT = 'L';

	/**
	 * XML attributes of every cloverETL component
	 */
	public final static String XML_TYPE_ATTRIBUTE="type";
    public final static String XML_ENABLED_ATTRIBUTE="enabled";

	/**
	 *  Standard constructor.
	 *
	 *@param  id  Unique ID of the Node
	 *@since      April 4, 2002
	 */
	public Node(String id, TransformationGraph graph) {
		super(id,graph);
		outPorts = new TreeMap();
		inPorts = new TreeMap();
		logPort = null;
        phase = null;
        runResult=Result.N_A; // result is not known yet
	}

    /**
     *  Standard constructor.
     *
     *@param  id  Unique ID of the Node
     *@since      April 4, 2002
     */
    public Node(String id){
        this(id,null);
    }

    @Override public void init() throws ComponentNotReadyException{
        super.init();
        runResult=Result.READY;
    }
    
    
    
	/**
	 *  Sets the EOF for particular output port. EOF indicates that no more data
	 * will be sent throught the output port.
	 *
	 *@param  portNum  The new EOF value
	 *@since           April 18, 2002
	 */
	public void setEOF(int portNum) {
		try {
			((OutputPort) outPorts.get(new Integer(portNum))).close();
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
	}


	/**
	 *  Returns the type of this Node (subclasses/Components should override
	 * this method to return appropriate type).
	 *
	 *@return    The Type value
	 *@since     April 4, 2002
	 */
	public abstract String getType();


	/**
	 *  Returns True if this Node is Leaf Node - i.e. only consumes data (has only
	 * input ports connected to it)
	 *
	 *@return    True if Node is a Leaf
	 *@since     April 4, 2002
	 */
	public boolean isLeaf() {
		if (outPorts.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns True if this Node is Phase Leaf Node - i.e. only consumes data within
	 * phase it belongs to (has only input ports connected or any connected output ports
	 * connects this Node with Node in different phase)
	 * 
	 * @return True if this Node is Phase Leaf
	 */
	public boolean isPhaseLeaf(){
		Iterator iterator=getOutPorts().iterator();
		while(iterator.hasNext()){
			if (phase!=((OutputPort)(iterator.next())).getReader().getPhase())
				return true;
		}
		return false;
	}


	/**
	 *  Returns True if this node is Root Node - i.e. it produces data (has only output ports
	 * connected to id).
	 *
	 *@return    True if Node is a Root
	 *@since     April 4, 2002
	 */
	public boolean isRoot() {
		if (inPorts.size() == 0) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 *  Sets the processing phase of the Node object.<br>
	 *  Default is 0 (ZERO).
	 *
	 *@param  phase  The new phase number
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}


	/**
	 *  Gets the processing phase of the Node object
	 *
	 *@return    The phase value
	 */
	public Phase getPhase() {
		return phase;
	}

    
    public int getPhaseNum(){
        return phase.getPhaseNum();
    }
    
	/**
	 *  Gets the OutPorts attribute of the Node object
	 *
	 *@return    Collection of OutPorts
	 *@since     April 18, 2002
	 */
	public Collection<OutputPort> getOutPorts() {
		return outPorts.values();
	}


	/**
	 *  Gets the InPorts attribute of the Node object
	 *
	 *@return    Collection of InPorts
	 *@since     April 18, 2002
	 */
	public Collection<InputPort> getInPorts() {
		return inPorts.values();
	}

	/**
	 *  Gets the metadata on output ports of the Node object
	 *
	 *@return    Collection of output ports metadata
	 */
	public Collection<DataRecordMetadata> getOutMetadata() {
		List<DataRecordMetadata> ret = new ArrayList<DataRecordMetadata>(outPorts.size());
		for(Iterator it = getOutPorts().iterator(); it.hasNext();) {
		    ret.add(((OutputPort) (it.next())).getMetadata());
		}
	    return ret;
	}

	/**
	 *  Gets the metadata on input ports of the Node object
	 *
	 *@return    Collection of input ports metadata
	 */
	public Collection getInMetadata() {
		List ret = new ArrayList(inPorts.size());
		for(Iterator it = getInPorts().iterator(); it.hasNext();) {
		    ret.add(((InputPort) (it.next())).getMetadata());
		}
	    return ret;
	}

	/**
	 *  Gets the number of records passed through specified port type and number
	 *
	 *@param  portType  Port type (IN, OUT, LOG)
	 *@param  portNum   port number (0...)
	 *@return           The RecordCount value
	 *@since            May 17, 2002
	 */
	public int getRecordCount(char portType, int portNum) {
		int count;
        // Integer used as key to TreeMap containing ports
		Integer port = new Integer(portNum);
		try {
			switch (portType) {
				case OUTPUT_PORT:
					count = ((OutputPort) outPorts.get(port)).getRecordCounter();
					break;
				case INPUT_PORT:
					count = ((InputPort) inPorts.get(port)).getRecordCounter();
					break;
				case LOG_PORT:
					if (logPort != null) {
						count = logPort.getRecordCounter();
					} else {
						count = -1;
					}
					break;
				default:
					count = -1;
			}
		} catch (Exception ex) {
			count = -1;
		}

		return count;
	}


	/**
	 *  Gets the result code of finished Node.<br>
	 *
	 *@return    The Result value
	 *@since     July 29, 2002
     *@see       org.jetel.graph.Node.Result
	 */
	public Result getResultCode() {
		return runResult;
	}


	/**
	 *  Gets the ResultMsg of finished Node.<br>
	 *  This message briefly describes what caused and error (if there was any).
	 *
	 *@return    The ResultMsg value
	 *@since     July 29, 2002
	 */
	public String getResultMsg() {
		return runResult!=null ? runResult.message() : null;
	}

    /**
     * Gets exception which caused Node to fail execution - if
     * there was such failure.
     * 
     * @return
     * @since 13.12.2006
     */
    public Throwable getResultException(){
        return resultException;
    }


	// Operations
	/**
	 *  main execution method of Node (calls in turn execute())
	 *
	 *@since    April 2, 2002
	 */
	public void run() {
        runResult=Result.RUNNING; // set running result, so we know run() method was started
        try {
            runResult = execute();
        } catch (IOException ex) {  // may be handled differently later
            runResult=Result.ERROR;
            resultException = ex;
            Message msg = Message.createErrorMessage(this,
                    new ErrorMsgBody(runResult.code(), runResult.message(), ex));
            getCloverRuntime().sendMessage(msg);
            return;
        } catch (InterruptedException ex) {
            runResult=Result.ABORTED;
            return;
        } catch (TransformException ex){
            runResult=Result.ERROR;
            resultException = ex;
            Message msg = Message.createErrorMessage(this,
                    new ErrorMsgBody(runResult.code(), "Error occurred in nested transformation: " + runResult.message(), ex));
            getCloverRuntime().sendMessage(msg);
            return;
        } catch (SQLException ex){
            runResult=Result.ERROR;
            resultException = ex;
            Message msg = Message.createErrorMessage(this,
                    new ErrorMsgBody(runResult.code(), runResult.message(), ex));
            getCloverRuntime().sendMessage(msg);
            return;
        } catch (Exception ex) { // may be handled differently later
            runResult=Result.ERROR;
            resultException = ex;
            Message msg = Message.createErrorMessage(this,
                    new ErrorMsgBody(runResult.code(), runResult.message(), ex));
            getCloverRuntime().sendMessage(msg);
            return;
        }

    }
    
    public abstract Result execute() throws Exception;

	/**
	 *  Abort execution of Node - brutal force
	 *
	 *@since    April 4, 2002
	 */
	public void abort() {
		runIt = false;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            //EMPTY INTENTIONALLY
        }
        
		if (runResult==Result.RUNNING){
			runResult = Result.ABORTED;
			getNodeThread().interrupt();
		}
	}

    /**
     * @return thread of running node; <b>null</b> if node does not runnig
     */
    public Thread getNodeThread() {
        if(nodeThread == null) {
            ThreadGroup defaultThreadGroup = Thread.currentThread().getThreadGroup();
            Thread[] activeThreads = new Thread[defaultThreadGroup.activeCount() * 2];
            int numThreads = defaultThreadGroup.enumerate(activeThreads, false);
            
            for(int i = 0; i < numThreads; i++) {
                if(activeThreads[i].getName().equals(getId())) {
                    nodeThread = activeThreads[i];
                }
            }
        }
        
        return nodeThread;
    }

    /**
     * Sets actual thread in which this node current running.
     * @param nodeThread
     */
    public void setNodeThread(Thread nodeThread) {
        this.nodeThread = nodeThread;
    }
    
	/**
	 *  End execution of Node - let Node finish gracefully
	 *
	 *@since    April 4, 2002
	 */
	public void end() {
		runIt = false;
	}


    /**
     * Provides CloverRuntime - object providing
     * various run-time services
     * 
     * @return
     * @since 13.12.2006
     */
    public CloverRuntime getCloverRuntime(){
        return getGraph().getRuntime();
    }
    
	/**
	 *  An operation that adds port to list of all InputPorts
	 *
	 *@param  port   Port (Input connection) to be added
	 *@since         April 2, 2002
	 *@deprecated    Use the other method which takes 2 arguments (portNum, port)
	 */

	public void addInputPort(InputPort port) {
		Integer portNum;
		int keyVal;
		try {
			portNum = (Integer) inPorts.lastKey();
			keyVal = portNum.intValue() + 1;
		} catch (NoSuchElementException ex) {
			keyVal = 0;
		}
		inPorts.put(new Integer(keyVal), port);
		port.connectReader(this, keyVal);
	}


	/**
	 *  An operation that adds port to list of all InputPorts
	 *
	 *@param  portNum  Number to be associated with this port
	 *@param  port     Port (Input connection) to be added
	 *@since           April 2, 2002
	 */
	public void addInputPort(int portNum, InputPort port) {
		inPorts.put(new Integer(portNum), port);
		port.connectReader(this, portNum);
	}


	/**
	 *  An operation that adds port to list of all OutputPorts
	 *
	 *@param  port   Port (Output connection) to be added
	 *@since         April 4, 2002
	 *@deprecated    Use the other method which takes 2 arguments (portNum, port)
	 */

	public void addOutputPort(OutputPort port) {
		Integer portNum;
		int keyVal;
		try {
			portNum = (Integer) inPorts.lastKey();
			keyVal = portNum.intValue() + 1;
		} catch (NoSuchElementException ex) {
			keyVal = 0;
		}
		outPorts.put(new Integer(keyVal), port);
		port.connectWriter(this, keyVal);
        resetBufferedValues();
	}


	/**
	 *  An operation that adds port to list of all OutputPorts
	 *
	 *@param  portNum  Number to be associated with this port
	 *@param  port     The feature to be added to the OutputPort attribute
	 *@since           April 4, 2002
	 */
	public void addOutputPort(int portNum, OutputPort port) {
		outPorts.put(new Integer(portNum), port);
		port.connectWriter(this, portNum);
        resetBufferedValues();
	}


	/**
	 *  Gets the port which has associated the num specified
	 *
	 *@param  portNum  number associated with the port
	 *@return          The outputPort
	 */
	public OutputPort getOutputPort(int portNum) {
        Object outPort=outPorts.get(new Integer(portNum));
        if (outPort instanceof OutputPort) { 
            return (OutputPort)outPort ;
        }else if (outPort==null) {
            return null;
        }
        throw new RuntimeException("Port number \""+portNum+"\" does not implement OutputPort interface "+outPort.getClass().getName());
	}

    /**
     *  Gets the port which has associated the num specified
     *
     *@param  portNum  number associated with the port
     *@return          The outputPort
     */
    public OutputPortDirect getOutputPortDirect(int portNum) {
        Object outPort=outPorts.get(new Integer(portNum));
        if (outPort instanceof OutputPortDirect) {
            return (OutputPortDirect)outPort ;
        }else if (outPort==null) {
            return null;
        }
        throw new RuntimeException("Port number \""+portNum+"\" does not implement OutputPortDirect interface");
    }

	/**
	 *  Gets the port which has associated the num specified
	 *
	 *@param  portNum  portNum number associated with the port
	 *@return          The inputPort
	 */
	public InputPort getInputPort(int portNum) {
        Object inPort=inPorts.get(new Integer(portNum));
        
        if (inPort instanceof InputPort) {
            return (InputPort)inPort ;
        }else if (inPort==null){
            return null;
        }
        throw new RuntimeException("Port number \""+portNum+"\" does not implement InputPort interface");
	}

    /**
     *  Gets the port which has associated the num specified
     *
     *@param  portNum  portNum number associated with the port
     *@return          The inputPort
     */
    public InputPortDirect getInputPortDirect(int portNum) {
        Object inPort=inPorts.get(new Integer(portNum));
        if (inPort instanceof InputPortDirect) {
            return (InputPortDirect)inPort ;
        }else if (inPort==null){
            return null;
        }
        throw new RuntimeException("Port number \""+portNum+"\" does not implement InputPortDirect interface");
    }
    
    /**
     * Removes input port.
     * @param inputPort
     */
    public void removeInputPort(InputPort inputPort) {
        inPorts.remove(new Integer(inputPort.getInputPortNumber()));
        
    }

    /**
     * Removes output port.
     * @param outputPort
     */
    public void removeOutputPort(OutputPort outputPort) {
        outPorts.remove(new Integer(outputPort.getOutputPortNumber()));
        resetBufferedValues();
    }

	/**
	 *  Adds a feature to the LogPort attribute of the Node object
	 *
	 *@param  port  The feature to be added to the LogPort attribute
	 *@since        April 4, 2002
	 */
	public void addLogPort(OutputPort port) {
		logPort = port;
		port.connectWriter(this, -1);
	}


	/**
	 *  An operation that does removes/unregisteres por<br>
	 *  Not yet implemented.
	 *
	 *@param  _portNum   Description of Parameter
	 *@param  _portType  Description of Parameter
	 *@since             April 2, 2002
	 */
	public void deletePort(int _portNum, char _portType) {
        throw new UnsupportedOperationException("Deleting port is not supported !");
	}


	/**
	 *  An operation that writes one record through specified output port.<br>
     *  As this operation gets the Port object from TreeMap, don't use it in loops
     *  or when time is critical. Instead obtain the Port object directly and
     *  use it's writeRecord() method.
	 *
	 *@param  _portNum                  Description of Parameter
	 *@param  _record                   Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  InterruptedException  Description of Exception
	 *@since                            April 2, 2002
	 */
	public void writeRecord(int _portNum, DataRecord _record) throws IOException, InterruptedException {
			((OutputPort) outPorts.get(new Integer(_portNum))).writeRecord(_record);
	}


	/**
	 *  An operation that reads one record through specified input port.<br>
     *  As this operation gets the Port object from TreeMap, don't use it in loops
     *  or when time is critical. Instead obtain the Port object directly and
     *  use it's readRecord() method.
	 *
	 *@param  _portNum                  Description of Parameter
	 *@param  record                    Description of Parameter
	 *@return                           Description of the Returned Value
	 *@exception  IOException           Description of Exception
	 *@exception  InterruptedException  Description of Exception
	 *@since                            April 2, 2002
	 */
	public DataRecord readRecord(int _portNum, DataRecord record) throws IOException, InterruptedException {
		return	((InputPort) inPorts.get(new Integer(_portNum))).readRecord(record);
	}


	/**
	 *  An operation that writes record to Log port
	 *
	 *@param  record                    Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  InterruptedException  Description of Exception
	 *@since                            April 2, 2002
	 */
	public void writeLogRecord(DataRecord record) throws IOException, InterruptedException {
			logPort.writeRecord(record);
	}


	/**
	 *  An operation that does ...
	 *
	 *@param  record                    Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  InterruptedException  Description of Exception
	 *@since                            April 2, 2002
	 */
	public void writeRecordBroadcast(DataRecord record) throws IOException, InterruptedException {
		if (outPortsArray == null) {
            refreshBufferedValues();
		}
		
        for(int i=0;i<outPortsSize;i++){
				outPortsArray[i].writeRecord(record);
		}
	}


	/**
	 *  Converts the collection of ports into List (ArrayList)<br>
	 *  This is auxiliary method which "caches" list of ports for faster access
	 *  when we need to go through all ports sequentially. Namely in
	 *  RecordBroadcast situations
	 *
	 *@param  ports  Collection of Ports
	 *@return        List (LinkedList) of ports
	 */
	private List getPortList(Collection ports) {
		List portList = new ArrayList();
		portList.addAll(ports);
		return portList;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  recordBuffer              Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  InterruptedException  Description of Exception
	 *@since                            August 13, 2002
	 */
    public void writeRecordBroadcastDirect(ByteBuffer recordBuffer) throws IOException, InterruptedException {
        if (outPortsArray == null) {
            refreshBufferedValues();
        }

        for(int i=0;i<outPortsSize;i++){
            ((OutputPortDirect) outPortsArray[i]).writeRecordDirect(recordBuffer);
            recordBuffer.rewind();
        }
    }


	/**
	 *  Closes all output ports - sends EOF signal to them.
	 *
	 *@since    April 11, 2002
	 */
	public void closeAllOutputPorts() {
		Iterator iterator = getOutPorts().iterator();
		OutputPort port;

		while (iterator.hasNext()) {
			port = (OutputPort) iterator.next();
			port.close();
		}
	}


	/**
	 *  Send EOF (no more data) to all connected output ports
	 *
	 *@since    April 18, 2002
	 */
	public void broadcastEOF() {
		closeAllOutputPorts();
	}


	/**
	 *  Closes specified output port - sends EOF signal. 
	 *
	 *@param  portNum  Which port to close
	 *@since           April 11, 2002
	 */
	public void closeOutputPort(int portNum) {
        OutputPort port = (OutputPort) outPorts.get(new Integer(portNum));
        if (port == null) {
            throw new RuntimeException(this.getId()+" - can't close output port \"" + portNum
                    + "\" - does not exists!");
        }
        port.close();
    }

	/* (non-Javadoc)
	 * @see org.jetel.graph.GraphElement#free()
	 */
	public void free() {
        super.free();
	}
    
	/**
	 *  Compares this Node to specified Object
	 *
	 *@param  obj  Node to compare with
	 *@return      True if obj represents node with the same ID
	 *@since       April 18, 2002
	 */
	@Override public boolean equals(Object obj) {
		if (getId().equals(((Node) obj).getId())) {
			return true;
		} else {
			return false;
		}
	}

    @Override public int hashCode(){
        return getId().hashCode();
    }

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 *@since     May 21, 2002
	 */
	public void toXML(Element xmlElement) {
		// set basic XML attributes of all graph components
		xmlElement.setAttribute(XML_ID_ATTRIBUTE, getId());
		xmlElement.setAttribute(XML_TYPE_ATTRIBUTE, getType());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  nodeXML  Description of Parameter
	 *@return          Description of the Returned Value
	 *@since           May 21, 2002
	 */
	public static Node fromXML(TransformationGraph graph, Element xmlElement)throws XMLConfigurationException {
        throw new  UnsupportedOperationException("not implemented in org.jetel.graph.Node"); 
	}


    /**
     * @return <b>true</b> if node is enabled; <b>false</b> else
     */
    public EnabledEnum getEnabled() {
        return enabled;
    }


    /**
     * @param enabled whether node is enabled
     */
    public void setEnabled(String enabledStr) {
        enabled = EnabledEnum.fromString(enabledStr);
    }


    /**
     * @return index of "pass through" input port
     */
    public int getPassThroughInputPort() {
        return passThroughInputPort;
    }


    /**
     * Sets "pass through" input port.
     * @param passThroughInputPort
     */
    public void setPassThroughInputPort(int passThroughInputPort) {
        this.passThroughInputPort = passThroughInputPort;
    }


    /**
     * @return index of "pass through" output port
     */
    public int getPassThroughOutputPort() {
        return passThroughOutputPort;
    }


    /**
     * Sets "pass through" output port
     * @param passThroughOutputPort
     */
    public void setPassThroughOutputPort(int passThroughOutputPort) {
        this.passThroughOutputPort = passThroughOutputPort;
    }
    
    protected void resetBufferedValues(){
        outPortList = null;
        outPortsArray=null;
        outPortsSize=0;
    }
    
    protected void refreshBufferedValues(){
        Collection op = getOutPorts();
        outPortsArray = (OutputPort[]) op.toArray(new OutputPort[op.size()]);
        outPortsSize = outPortsArray.length;
    }
    
    protected ConfigurationStatus checkInputPorts(ConfigurationStatus status, int min, int max) {
        if(getInPorts().size() < min) {
            status.add(new ConfigurationProblem("At least " + min + " input port can be defined!", Severity.ERROR, this, Priority.NORMAL));
        }
        if(getInPorts().size() > max) {
            status.add(new ConfigurationProblem("At most " + max + " input ports can be defined!", Severity.ERROR, this, Priority.NORMAL));
        }

        return status;
    }

    protected ConfigurationStatus checkOutputPorts(ConfigurationStatus status, int min, int max) {
        if(getOutPorts().size() < min) {
            status.add(new ConfigurationProblem("At least " + min + " output port can be defined!", Severity.ERROR, this, Priority.NORMAL));
        }
        if(getOutPorts().size() > max) {
            status.add(new ConfigurationProblem("At most " + max + " output ports can be defined!", Severity.ERROR, this, Priority.NORMAL));
        }

        return status;
    }
}
/*
 *  end class Node
 */

