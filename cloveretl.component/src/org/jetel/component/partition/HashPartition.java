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
package org.jetel.component.partition;

import java.nio.ByteBuffer;

import org.jetel.data.DataRecord;
import org.jetel.data.HashKey;
import org.jetel.data.RecordKey;
import org.jetel.graph.TransformationGraph;

/**
 * Partition algorithm based on calculating hash value of
 * specified key. The hash is then split to intervals. Number of
 * intervals is based specified number
 *  
 * 
 * @author david
 * @since  1.3.2005
 */
public class HashPartition implements PartitionFunction{
    int numPorts;
    HashKey hashKey;
    
    public HashPartition(){
    }
    
    public void init(int numPartitions, RecordKey partitionKey){
        this.numPorts=numPartitions;
        hashKey=new HashKey(partitionKey,null);
    }
    
    public int getOutputPort(DataRecord record){
        hashKey.setDataRecord(record);
        //int hash=hashKey.hashCode(); 
        //int value=(hash)&0x0FF;//// take only last 8 bits
        return Math.abs(hashKey.hashCode() % numPorts);
    }
    
    public void setGraph(TransformationGraph graph) {
    	// not used here
    }

    public TransformationGraph getGraph() {
    	// not used here
    	return null;
    }

    public int getOutputPort(ByteBuffer directRecord) {
		throw new UnsupportedOperationException();
	}

	public boolean supportsDirectRecord() {
		return false;
	}
}