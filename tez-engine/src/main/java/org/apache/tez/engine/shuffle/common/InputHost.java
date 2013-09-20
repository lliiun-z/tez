/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tez.engine.shuffle.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.tez.engine.common.InputAttemptIdentifier;

public class InputHost {

  private final String host;
  private final int port;

  private final List<InputAttemptIdentifier> inputs = new LinkedList<InputAttemptIdentifier>();

  public InputHost(String hostName, int port, ApplicationId appId) {
    this.host = hostName;
    this.port = port;
  }

  public String getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public synchronized int getNumPendingInputs() {
    return inputs.size();
  }
  
  public synchronized void addKnownInput(InputAttemptIdentifier srcAttempt) {
    inputs.add(srcAttempt);
  }

  public synchronized List<InputAttemptIdentifier> clearAndGetPendingInputs() {
    List<InputAttemptIdentifier> inputsCopy = new ArrayList<InputAttemptIdentifier>(
        inputs);
    inputs.clear();
    return inputsCopy;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((host == null) ? 0 : host.hashCode());
    result = prime * result + port;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InputHost other = (InputHost) obj;
    if (host == null) {
      if (other.host != null)
        return false;
    } else if (!host.equals(other.host))
      return false;
    if (port != other.port)
      return false;
    return true;
  }
}
