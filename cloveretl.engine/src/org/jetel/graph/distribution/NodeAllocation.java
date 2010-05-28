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
package org.jetel.graph.distribution;

import org.jetel.exception.JetelException;
import org.jetel.util.string.StringUtils;


/**
 * @author Martin Zatopek (info@cloveretl.com)
 *         (c) Opensys TM by Javlin, a.s. (www.cloveretl.com)
 *
 * @created 11.1.2010
 */
public class NodeAllocation {

	private static final String SANDBOX_PREFIX = "sandbox:";
	
	private static final String NODE_PREFIX = "node:";
	
	private static final NodeAllocation INFERED_FROM_NEIGHBOURS;
	
	static {
		INFERED_FROM_NEIGHBOURS = new NodeAllocation();
		INFERED_FROM_NEIGHBOURS.setNeighbours(true);
	}
	
	private boolean neighbours;

	private String sandboxId;
	
	private String nodeId;
	
	//is not supported yet
	private String[] clusterNodes;
	
	public static NodeAllocation createBasedOnNeighbours() {
		return INFERED_FROM_NEIGHBOURS;
	}

	public static NodeAllocation createBasedOnSandbox(String sandboxId) {
		if (StringUtils.isEmpty(sandboxId)) {
			throw new IllegalArgumentException("Sandbox id cannot be empty.");
		}
		NodeAllocation nodeAllocation = new NodeAllocation();
		nodeAllocation.setSandboxId(sandboxId);
		return nodeAllocation;
	}

	public static NodeAllocation createBasedOnNodeId(String nodeId) {
		if (StringUtils.isEmpty(nodeId)) {
			throw new IllegalArgumentException("Node id cannot be empty.");
		}
		NodeAllocation nodeAllocation = new NodeAllocation();
		nodeAllocation.setNodeId(nodeId);
		return nodeAllocation;
	}

	public static NodeAllocation createBasedOnClusterNodes(String[] clusterNodes) {
		if (clusterNodes == null || clusterNodes.length == 0) {
			throw new IllegalArgumentException("Cluster nodes cannot be empty array.");
		}
		NodeAllocation nodeAllocation = new NodeAllocation();
		nodeAllocation.setClusterNodes(clusterNodes);
		return nodeAllocation;
	}

	public static NodeAllocation createFromString(String rawAllocation) throws JetelException {
		if (rawAllocation.startsWith(SANDBOX_PREFIX)) {
			String sandboxId = rawAllocation.substring(SANDBOX_PREFIX.length());
			if (StringUtils.isEmpty(sandboxId)) {
				throw new JetelException("Ivalid node allocation format: " + rawAllocation + ".");
			}
			return NodeAllocation.createBasedOnSandbox(sandboxId);
		} else if (rawAllocation.startsWith(NODE_PREFIX)) {
			String nodeId = rawAllocation.substring(NODE_PREFIX.length());
			if (StringUtils.isEmpty(nodeId)) {
				throw new JetelException("Ivalid node allocation format: " + rawAllocation + ".");
			}
			return NodeAllocation.createBasedOnNodeId(nodeId);
		}
		throw new JetelException("Ivalid node allocation format: " + rawAllocation + ".");
	}
	
	/**
	 * Only private constructor. 
	 */
	private NodeAllocation() {
		//DO NOTHING
	}
	
	public boolean isInferedFromNeighbours() {
		return neighbours;
	}
	
	public boolean isInferedFromSandbox() {
		return sandboxId != null;
	}
	
	public boolean isInferedFromNode() {
		return nodeId != null;
	}
	
	public boolean isInferedFromClusterNodes() {
		return clusterNodes != null;
	}

	//GETTERS & SETTERS
	
	private void setNeighbours(boolean neighbours) {
		this.neighbours = neighbours;
	}

	public String getSandboxId() {
		return sandboxId;
	}

	private void setSandboxId(String sandboxId) {
		this.sandboxId = sandboxId;
	}

	public String getNodeId() {
		return nodeId;
	}

	private void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String[] getClusterNodes() {
		return clusterNodes;
	}

	private void setClusterNodes(String[] clusterNodes) {
		this.clusterNodes = clusterNodes;
	}

}
