/* Copyright (C) 2011 Akiban Technologies Inc.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.PrivilegeNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.akiban.sql.parser;

import com.akiban.sql.StandardException;

/**
 * This node represents a set of privileges that are granted or revoked on one object.
 */
public class PrivilegeNode extends QueryTreeNode
{
  public static enum ObjectType {
    TABLE_PRIVILEGES, ROUTINE_PRIVILEGES, SEQUENCE_PRIVILEGES, UDT_PRIVILEGES
  }

  public static final String USAGE_PRIV = "USAGE";

  //
  // State initialized when the node is instantiated
  //
  private ObjectType objectType;
  private TableName objectName;
  private TablePrivilegesNode specificPrivileges; // Null for routine and usage privs
  private RoutineDesignator routineDesignator; // Null for table and usage privs

  private String privilege;  // E.g., USAGE_PRIV
  private boolean restrict;
    
  /**
   * Initialize a PrivilegeNode for use against SYS.SYSTABLEPERMS and SYS.SYSROUTINEPERMS.
   *
   * @param objectType (an Integer)
   * @param objectOfPrivilege (a TableName or RoutineDesignator)
   * @param specificPrivileges null for routines and usage
   */
  public void init(Object objectType, Object objectOfPrivilege, 
                   Object specificPrivileges)
      throws StandardException {
    this.objectType = (ObjectType)objectType;
    switch(this.objectType) {
    case TABLE_PRIVILEGES:
      objectName = (TableName)objectOfPrivilege;
      this.specificPrivileges = (TablePrivilegesNode)specificPrivileges;
      break;
            
    case ROUTINE_PRIVILEGES:
      routineDesignator = (RoutineDesignator)objectOfPrivilege;
      objectName = routineDesignator.name;
      break;
            
    default:
      assert false;
    }
  }

  /**
   * Initialize a PrivilegeNode for use against SYS.SYSPERMS.
   *
   * @param objectType E.g., SEQUENCE
   * @param objectName A possibles schema-qualified name
   * @param privilege A privilege, e.g. USAGE_PRIV
   * @param restrict True if this is a REVOKE...RESTRICT action
   */
  public void init(Object objectType, Object objectName, Object privilege, 
                   Object restrict) {
    this.objectType = (ObjectType)objectType;
    this.objectName = (TableName)objectName;
    this.privilege = (String)privilege;
    this.restrict = ((Boolean)restrict).booleanValue();
  }
    
}