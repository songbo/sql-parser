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

   Derby - Class org.apache.derby.impl.sql.compile.DeleteNode

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
 * A DeleteNode represents a DELETE statement. It is the top-level node
 * for the statement.
 *
 * For positioned delete, there may be no from table specified.
 * The from table will be derived from the cursor specification of
 * the named cursor.
 *
 */

public class DeleteNode extends DMLModStatementNode
{

  /**
   * Initializer for a DeleteNode.
   *
   * @param targetTableName The name of the table to delete from
   * @param queryExpression The query expression that will generate
   *                        the rows to delete from the given table
   */

  public void init(Object targetTableName,
                   Object queryExpression) {
    super.init(queryExpression);
    this.targetTableName = (TableName)targetTableName;
  }

  public String statementToString() {
    return "DELETE";
  }

  /**
   * Return the type of statement, something from
   * StatementType.
   *
   * @return the type of statement
   */
  protected final int getStatementType() {
    return StatementType.DELETE;
  }

}