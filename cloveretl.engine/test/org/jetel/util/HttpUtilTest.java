/*
*    jETeL/Clover - Java based ETL application framework.
*    Copyright (C) 2006 Javlin Consulting <info@javlinconsulting>
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
package org.jetel.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author kocik (info@cloveretl.com) (c) Javlin, a.s. (www.cloveretl.com)
 *
 * @created Aug 2, 2017
 */
public class HttpUtilTest {

	@Test
	public void testNullHttpHeader() {
		assertFalse(HttpUtil.isValidHttpHeader(null));
	}

	@Test
	public void testEmptyHttpHeader() {
		assertFalse(HttpUtil.isValidHttpHeader(""));
		assertFalse(HttpUtil.isValidHttpHeader("   "));
		assertFalse(HttpUtil.isValidHttpHeader("	"));
		assertFalse(HttpUtil.isValidHttpHeader(""));
		assertFalse(HttpUtil.isValidHttpHeader("\n"));
	}

	@Test
	public void testCorrectHttpHeader() {
		assertTrue(HttpUtil.isValidHttpHeader("headerName"));
		assertTrue(HttpUtil.isValidHttpHeader("X-name"));
	}

	@Test
	public void testInCorrectHttpHeader() {
		assertFalse(HttpUtil.isValidHttpHeader("test(test"));
		assertFalse(HttpUtil.isValidHttpHeader("test)test"));
		assertFalse(HttpUtil.isValidHttpHeader("test<test"));
		assertFalse(HttpUtil.isValidHttpHeader("test>test"));
		assertFalse(HttpUtil.isValidHttpHeader("test@test"));
		assertFalse(HttpUtil.isValidHttpHeader("test,test"));
		assertFalse(HttpUtil.isValidHttpHeader("test;test"));
		assertFalse(HttpUtil.isValidHttpHeader("test:test"));
		assertFalse(HttpUtil.isValidHttpHeader("test\\test"));
		assertFalse(HttpUtil.isValidHttpHeader("test\"test"));
		assertFalse(HttpUtil.isValidHttpHeader("test/test"));
		assertFalse(HttpUtil.isValidHttpHeader("test[test"));
		assertFalse(HttpUtil.isValidHttpHeader("test]test"));
		assertFalse(HttpUtil.isValidHttpHeader("test?test"));
		assertFalse(HttpUtil.isValidHttpHeader("test=test"));
		assertFalse(HttpUtil.isValidHttpHeader("test{test"));
		assertFalse(HttpUtil.isValidHttpHeader("test}test"));
		assertFalse(HttpUtil.isValidHttpHeader("test test"));
		assertFalse(HttpUtil.isValidHttpHeader("test	test"));
		assertFalse(HttpUtil.isValidHttpHeader(" test"));
		assertFalse(HttpUtil.isValidHttpHeader("test "));
		assertFalse(HttpUtil.isValidHttpHeader("test\ntest"));
		assertFalse(HttpUtil.isValidHttpHeader("test\\u001Btest"));
	}
}