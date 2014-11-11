/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package org.apache.uima.ducc.container.jd.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.uima.ducc.container.common.files.json.JsonConverter;
import org.apache.uima.ducc.container.common.files.json.JsonWorkItemState;
import org.junit.Test;

public class TestJson extends ATest {
	
	private void compare(JsonWorkItemState jwisA, JsonWorkItemState jwisB) {
		assertTrue(jwisA.getNodeName().equals(jwisB.getNodeName()));
		assertTrue(jwisA.getPid() == jwisB.getPid());
		assertTrue(jwisA.getTid() == jwisB.getTid());
		assertTrue(jwisA.getProcessingTime() == jwisB.getProcessingTime());
		assertTrue(jwisA.getTransferTime() == jwisB.getTransferTime());
		assertTrue(jwisA.getSystemKey().equals(jwisB.getSystemKey()));
		assertTrue(jwisA.getUserKey().equals(jwisB.getUserKey()));
		assertTrue(jwisA.getStatus().equals(jwisB.getStatus()));
	}
	
	@Test
	public void test_01() {
		if(isDisabled(this.getClass().getName())) {
			return;
		}
		try {
			JsonWorkItemState jwis1 = new JsonWorkItemState();
			jwis1.setNodeName("node01");
			jwis1.setPid(23);
			jwis1.setTid(45);
			jwis1.setProcessingTime(5000);
			jwis1.setTransferTime(1000);
			jwis1.setSystemKey("sys0001");
			jwis1.setUserKey("usr0001");
			jwis1.setStatus("running");
			JsonWorkItemState jwis2 = new JsonWorkItemState();
			jwis2.setNodeName("node01");
			jwis2.setPid(23);
			jwis2.setTid(46);
			jwis2.setProcessingTime(0000);
			jwis2.setTransferTime(0500);
			jwis2.setSystemKey("sys0002");
			jwis2.setUserKey("usr0002");
			jwis2.setStatus("transfer");
			ConcurrentHashMap<String,JsonWorkItemState> map1 = new ConcurrentHashMap<String,JsonWorkItemState>();
			map1.put(jwis1.getSystemKey(),jwis1);
			map1.put(jwis2.getSystemKey(),jwis2);
			String json = JsonConverter.workItemStateMapToJson(map1);
			asExpected(json);
			ConcurrentHashMap<String,JsonWorkItemState> map2 = JsonConverter.workItemStateMapFromJson(json);
			compare(jwis1, map2.get(jwis1.getSystemKey()));
			compare(jwis2, map2.get(jwis2.getSystemKey()));
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Exception");
		}
	}
	
	@Test
	public void test_02() {
		if(isDisabled(this.getClass().getName())) {
			return;
		}
		try {
			JsonWorkItemState jwis1 = new JsonWorkItemState();
			jwis1.setNodeName("node01");
			jwis1.setPid(23);
			jwis1.setTid(45);
			jwis1.setProcessingTime(5000);
			jwis1.setTransferTime(1000);
			jwis1.setSystemKey("sys0001");
			jwis1.setUserKey("usr0001");
			jwis1.setStatus("running");
			JsonWorkItemState jwis2 = null;
			compare(jwis1, jwis2);
			debug(jwis1.getSystemKey());
			fail("No Exception?");
		}
		catch(Exception e) {
			asExpected(e);
		}
	}
}
