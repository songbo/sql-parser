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

   Derby - Class org.apache.derby.impl.sql.compile.JoinNode

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

import java.util.Properties;

/**
 * A JoinNode represents a join result set for either of the basic DML
 * operations: SELECT and INSERT.  For INSERT - SELECT, any of the
 * fields in a JoinNode can be used (the JoinNode represents
 * the (join) SELECT statement in the INSERT - SELECT).  For INSERT,
 * the resultColumns in the selectList will contain the names of the columns
 * being inserted into or updated.
 *
 */

public class JoinNode extends TableOperatorNode
{
  /* Join semantics */
  public static final int INNERJOIN = 1;
  public static final int CROSSJOIN = 2;
  public static final int LEFTOUTERJOIN = 3;
  public static final int RIGHTOUTERJOIN = 4;
  public static final int FULLOUTERJOIN = 5;
  public static final int UNIONJOIN = 6;
  
  /** If this flag is true, this node represents a natural join. */
  private boolean naturalJoin;

  ValueNode joinClause;
  boolean joinClauseNormalized;
  ResultColumnList usingClause;
  //User provided optimizer overrides
  Properties joinOrderStrategyProperties;

  /**
   * Initializer for a JoinNode.
   *
   * @param leftResult The ResultSetNode on the left side of this join
   * @param rightResult The ResultSetNode on the right side of this join
   * @param onClause The ON clause
   * @param usingClause The USING clause
   * @param selectList The result column list for the join
   * @param tableProperties Properties list associated with the table
   * @param joinOrderStrategyProperties User provided optimizer overrides
   *
   * @exception StandardException Thrown on error
   */
  public void init(Object leftResult,
                   Object rightResult,
                   Object onClause,
                   Object usingClause,
                   Object selectList,
                   Object tableProperties,
                   Object joinOrderStrategyProperties)
      throws StandardException {
    super.init(leftResult, rightResult, tableProperties);
    resultColumns = (ResultColumnList)selectList;
    joinClause = (ValueNode)onClause;
    joinClauseNormalized = false;
    this.usingClause = (ResultColumnList)usingClause;
    this.joinOrderStrategyProperties = (Properties)joinOrderStrategyProperties;
  }

  /** 
   * Convert the joinType to a string.
   *
   * @param joinType The joinType as an int.
   *
   * @return String The joinType as a String.
   */
  public static String joinTypeToString(int joinType) {
    switch(joinType) {
    case INNERJOIN:
      return "INNER JOIN";

    case CROSSJOIN:
      return "CROSS JOIN";

    case LEFTOUTERJOIN:
      return "LEFT OUTER JOIN";

    case RIGHTOUTERJOIN:
      return "RIGHT OUTER JOIN";

    case FULLOUTERJOIN:
      return "FULL OUTER JOIN";

    case UNIONJOIN:
      return "UNION JOIN";

    default:
      assert false : "Unexpected joinType";
      return null;
    }
  }

  /**
   * Prints the sub-nodes of this object.  See QueryTreeNode.java for
   * how tree printing is supposed to work.
   *
   * @param depth The depth of this node in the tree
   */

  public void printSubNodes(int depth) {
    super.printSubNodes(depth);

    if (joinClause != null) {
      printLabel(depth, "joinClause: ");
      joinClause.treePrint(depth + 1);
    }

    if (usingClause != null) {
      printLabel(depth, "usingClause: ");
      usingClause.treePrint(depth + 1);
    }
  }

  /**
   * Flag this as a natural join so that an implicit USING clause will
   * be generated in the bind phase.
   */
  void setNaturalJoin() {
    naturalJoin = true;
  }

  /**
   * Return the logical left result set for this qualified
   * join node.
   * (For RIGHT OUTER JOIN, the left is the right
   * and the right is the left and the JOIN is the NIOJ).
   */
  ResultSetNode getLogicalLeftResultSet() {
    return leftResultSet;
  }

  /**
   * Return the logical right result set for this qualified
   * join node.
   * (For RIGHT OUTER JOIN, the left is the right
   * and the right is the left and the JOIN is the NIOJ).
   */
  ResultSetNode getLogicalRightResultSet() {
    return rightResultSet;
  }

  /**
   * Accept the visitor for all visitable children of this node.
   * 
   * @param v the visitor
   *
   * @exception StandardException on error
   */
  void acceptChildren(Visitor v) throws StandardException {
    super.acceptChildren(v);

    if (resultColumns != null) {
      resultColumns = (ResultColumnList)resultColumns.accept(v);
    }

    if (joinClause != null) {
      joinClause = (ValueNode)joinClause.accept(v);
    }

    if (usingClause != null) {
      usingClause = (ResultColumnList)usingClause.accept(v);
    }
  }

}