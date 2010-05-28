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
package org.jetel.ctl;

import org.jetel.ctl.ASTnode.CLVFAddNode;
import org.jetel.ctl.ASTnode.CLVFAnd;
import org.jetel.ctl.ASTnode.CLVFArguments;
import org.jetel.ctl.ASTnode.CLVFArrayAccessExpression;
import org.jetel.ctl.ASTnode.CLVFAssignment;
import org.jetel.ctl.ASTnode.CLVFBlock;
import org.jetel.ctl.ASTnode.CLVFBreakStatement;
import org.jetel.ctl.ASTnode.CLVFCaseStatement;
import org.jetel.ctl.ASTnode.CLVFComparison;
import org.jetel.ctl.ASTnode.CLVFConditionalExpression;
import org.jetel.ctl.ASTnode.CLVFContinueStatement;
import org.jetel.ctl.ASTnode.CLVFDateField;
import org.jetel.ctl.ASTnode.CLVFDeleteDictNode;
import org.jetel.ctl.ASTnode.CLVFDivNode;
import org.jetel.ctl.ASTnode.CLVFDoStatement;
import org.jetel.ctl.ASTnode.CLVFFieldAccessExpression;
import org.jetel.ctl.ASTnode.CLVFForStatement;
import org.jetel.ctl.ASTnode.CLVFForeachStatement;
import org.jetel.ctl.ASTnode.CLVFFunctionCall;
import org.jetel.ctl.ASTnode.CLVFFunctionDeclaration;
import org.jetel.ctl.ASTnode.CLVFIIfNode;
import org.jetel.ctl.ASTnode.CLVFIdentifier;
import org.jetel.ctl.ASTnode.CLVFIfStatement;
import org.jetel.ctl.ASTnode.CLVFImportSource;
import org.jetel.ctl.ASTnode.CLVFInFunction;
import org.jetel.ctl.ASTnode.CLVFIsNullNode;
import org.jetel.ctl.ASTnode.CLVFListOfLiterals;
import org.jetel.ctl.ASTnode.CLVFLiteral;
import org.jetel.ctl.ASTnode.CLVFLogLevel;
import org.jetel.ctl.ASTnode.CLVFLookupNode;
import org.jetel.ctl.ASTnode.CLVFMemberAccessExpression;
import org.jetel.ctl.ASTnode.CLVFModNode;
import org.jetel.ctl.ASTnode.CLVFMulNode;
import org.jetel.ctl.ASTnode.CLVFNVL2Node;
import org.jetel.ctl.ASTnode.CLVFNVLNode;
import org.jetel.ctl.ASTnode.CLVFOr;
import org.jetel.ctl.ASTnode.CLVFParameters;
import org.jetel.ctl.ASTnode.CLVFPostfixExpression;
import org.jetel.ctl.ASTnode.CLVFPrintErrNode;
import org.jetel.ctl.ASTnode.CLVFPrintLogNode;
import org.jetel.ctl.ASTnode.CLVFPrintStackNode;
import org.jetel.ctl.ASTnode.CLVFRaiseErrorNode;
import org.jetel.ctl.ASTnode.CLVFReadDictNode;
import org.jetel.ctl.ASTnode.CLVFReturnStatement;
import org.jetel.ctl.ASTnode.CLVFSequenceNode;
import org.jetel.ctl.ASTnode.CLVFStart;
import org.jetel.ctl.ASTnode.CLVFStartExpression;
import org.jetel.ctl.ASTnode.CLVFSubNode;
import org.jetel.ctl.ASTnode.CLVFSwitchStatement;
import org.jetel.ctl.ASTnode.CLVFType;
import org.jetel.ctl.ASTnode.CLVFUnaryExpression;
import org.jetel.ctl.ASTnode.CLVFVariableDeclaration;
import org.jetel.ctl.ASTnode.CLVFWhileStatement;
import org.jetel.ctl.ASTnode.CLVFWriteDictNode;
import org.jetel.ctl.ASTnode.SimpleNode;

public interface TransformLangParserVisitor extends SyntheticNodeVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(CLVFStart node, Object data);
  public Object visit(CLVFStartExpression node, Object data);
  public Object visit(CLVFImportSource node, Object data);
  public Object visit(CLVFParameters node, Object data);
  public Object visit(CLVFType node, Object data);
  public Object visit(CLVFFunctionDeclaration node, Object data);
  public Object visit(CLVFVariableDeclaration node, Object data);
  public Object visit(CLVFAssignment node, Object data);
  public Object visit(CLVFConditionalExpression node, Object data);
  public Object visit(CLVFOr node, Object data);
  public Object visit(CLVFAnd node, Object data);
  public Object visit(CLVFComparison node, Object data);
  public Object visit(CLVFAddNode node, Object data);
  public Object visit(CLVFSubNode node, Object data);
  public Object visit(CLVFMulNode node, Object data);
  public Object visit(CLVFDivNode node, Object data);
  public Object visit(CLVFModNode node, Object data);
  public Object visit(CLVFUnaryExpression node, Object data);
  public Object visit(CLVFPostfixExpression node, Object data);
  public Object visit(CLVFMemberAccessExpression node, Object data);
  public Object visit(CLVFArrayAccessExpression node, Object data);
  public Object visit(CLVFInFunction node, Object data);
  public Object visit(CLVFFunctionCall node, Object data);
  public Object visit(CLVFIsNullNode node, Object data);
  public Object visit(CLVFNVLNode node, Object data);
  public Object visit(CLVFNVL2Node node, Object data);
  public Object visit(CLVFIIfNode node, Object data);
  public Object visit(CLVFPrintErrNode node, Object data);
  public Object visit(CLVFPrintLogNode node, Object data);
  public Object visit(CLVFPrintStackNode node, Object data);
  public Object visit(CLVFRaiseErrorNode node, Object data);
  public Object visit(CLVFReadDictNode node, Object data);
  public Object visit(CLVFWriteDictNode node, Object data);
  public Object visit(CLVFDeleteDictNode node, Object data);
  public Object visit(CLVFFieldAccessExpression node, Object data);
  public Object visit(CLVFIdentifier node, Object data);
  public Object visit(CLVFArguments node, Object data);
  public Object visit(CLVFDateField node, Object data);
  public Object visit(CLVFLogLevel node, Object data);
  public Object visit(CLVFLiteral node, Object data);
  public Object visit(CLVFListOfLiterals node, Object data);
  public Object visit(CLVFBlock node, Object data);
  public Object visit(CLVFIfStatement node, Object data);
  public Object visit(CLVFSwitchStatement node, Object data);
  public Object visit(CLVFCaseStatement node, Object data);
  public Object visit(CLVFWhileStatement node, Object data);
  public Object visit(CLVFForStatement node, Object data);
  public Object visit(CLVFForeachStatement node, Object data);
  public Object visit(CLVFDoStatement node, Object data);
  public Object visit(CLVFBreakStatement node, Object data);
  public Object visit(CLVFContinueStatement node, Object data);
  public Object visit(CLVFReturnStatement node, Object data);
  public Object visit(CLVFSequenceNode node, Object data);
  public Object visit(CLVFLookupNode node, Object data);
}
/* JavaCC - OriginalChecksum=7c2c6971f8e7937c1c37cefa3a0c4f9d (do not edit this line) */
