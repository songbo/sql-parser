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

   Derby - Class org.apache.derby.impl.sql.compile.ConstraintDefinitionNode

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

import java.util.Properties;

/**
 * A ConstraintDefintionNode is a class for all nodes that can represent
 * constraint definitions.
 *
 */

public class ConstraintDefinitionNode extends TableElementNode
{
  private TableName constraintName;
  protected int constraintType;
  protected Properties properties;
  ResultColumnList columnList;
  String constraintText;
  ValueNode checkCondition;
  private int behavior;
  private int verifyType = StatementType.DROP_CONSTRAINT; // By default do not check the constraint type

  public void init(Object constraintName,
                   Object constraintType,
                   Object rcl,
                   Object properties,
                   Object checkCondition,
                   Object constraintText,
                   Object behavior) {
    this.constraintName = (TableName)constraintName;

    /* We need to pass null as name to TableElementNode's constructor 
     * since constraintName may be null.
     */
    super.init(null);
    if (this.constraintName != null) {
      this.name = this.constraintName.getTableName();
    }
    this.constraintType = ((Integer)constraintType).intValue();
    this.properties = (Properties)properties;
    this.columnList = (ResultColumnList)rcl;
    this.checkCondition = (ValueNode)checkCondition;
    this.constraintText = (String)constraintText;
    this.behavior = ((Integer)behavior).intValue();
  }
  
  public void init(Object constraintName,
                   Object constraintType,
                   Object rcl,
                   Object properties,
                   Object checkCondition,
                   Object constraintText) {
    init(constraintName,
         constraintType,
         rcl,
         properties, 
         checkCondition,
         constraintText,
         StatementType.DROP_DEFAULT);
  }

  public void init(Object constraintName,
                   Object constraintType,
                   Object rcl,
                   Object properties,
                   Object checkCondition,
                   Object constraintText,
                   Object behavior,
                   Object verifyType) {
    init(constraintName, constraintType, rcl, properties, checkCondition, 
         constraintText, behavior);
    this.verifyType = ((Integer)verifyType).intValue();
  }
    
  /**
   * Get the constraint type
   *
   * @return constraintType The constraint type.
   */
  int getConstraintType() {
    return constraintType;
  }

  /**
   * Set the optional properties for the backing index to this constraint.
   *
   * @param properties The optional Properties for this constraint.
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /** 
   * Get the optional properties for the backing index to this constraint.
   *
   *
   * @return The optional properties for the backing index to this constraint
   */
  public Properties getProperties()
  {
    return properties;
  }


  /**
   * Convert this object to a String.  See comments in QueryTreeNode.java
   * for how this should be done for tree printing.
   *
   * @return This object as a String
   */

  public String toString() {
    return "constraintName: " + 
      ( ( constraintName != null) ?
        constraintName.toString() : "null" ) + "\n" +
      "constraintType: " + constraintType + "\n" + 
      "properties: " +
      ((properties != null) ? properties.toString() : "null") + "\n" +
      super.toString();
  }

}