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

   Derby - Class org.apache.derby.impl.sql.compile.SetRoleNode

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

/**
 * A SetRoleNode is the root of a QueryTree that represents a SET ROLE
 * statement.
 */

public class SetRoleNode extends MiscellaneousStatementNode
{
  private String name;
  private int type;

  /**
   * Initializer for a SetRoleNode
   *
   * @param roleName The name of the new role, null if NONE specified
   * @param type Type of role name could be USER or dynamic parameter
   *
   */
  public void init(Object roleName, Object type) {
    this.name = (String)roleName;
    if (type != null) {
      this.type = ((Integer)type).intValue();
    }
  }

  /**
   * Convert this object to a String.  See comments in QueryTreeNode.java
   * for how this should be done for tree printing.
   *
   * @return  This object as a String
   */

  public String toString() {
    return super.toString() +
      (type == StatementType.SET_ROLE_DYNAMIC ?
       "roleName: ?\n" :
       "rolename: " + name + "\n");
  }

  public String statementToString() {
    return "SET ROLE";
  }

}