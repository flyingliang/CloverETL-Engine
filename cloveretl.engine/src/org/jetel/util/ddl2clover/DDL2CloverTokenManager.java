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
package org.jetel.util.ddl2clover;
import java.util.*;
import java.io.*;
import org.jetel.metadata.*;
import java.math.*;
import org.jetel.data.*;

public class DDL2CloverTokenManager implements DDL2CloverConstants
{
  public  java.io.PrintStream debugStream = System.out;
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
{
   switch (pos)
   {
      case 0:
         if ((active1 & 0x10L) != 0L)
            return 7;
         if ((active0 & 0x7ffffffffffe000L) != 0L)
         {
            jjmatchedKind = 63;
            return 26;
         }
         if ((active0 & 0x640L) != 0L)
         {
            jjmatchedKind = 72;
            return -1;
         }
         return -1;
      case 1:
         if ((active0 & 0x20000000000L) != 0L)
            return 26;
         if ((active0 & 0x7fffdffffffe000L) != 0L)
         {
            jjmatchedKind = 63;
            jjmatchedPos = 1;
            return 26;
         }
         if ((active0 & 0x640L) != 0L)
         {
            if (jjmatchedPos == 0)
            {
               jjmatchedKind = 72;
               jjmatchedPos = 0;
            }
            return -1;
         }
         return -1;
      case 2:
         if ((active0 & 0x7fffdd1f9ffe000L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 63;
               jjmatchedPos = 2;
            }
            return 26;
         }
         if ((active0 & 0x2e06000000L) != 0L)
            return 26;
         return -1;
      case 3:
         if ((active0 & 0x1c6040018f0000L) != 0L)
            return 26;
         if ((active0 & 0x7e39d95fc70e000L) != 0L)
         {
            if (jjmatchedPos != 3)
            {
               jjmatchedKind = 63;
               jjmatchedPos = 3;
            }
            return 26;
         }
         return -1;
      case 4:
         if ((active0 & 0x2001040000000L) != 0L)
            return 26;
         if ((active0 & 0x7f19d85bd74e000L) != 0L)
         {
            jjmatchedKind = 63;
            jjmatchedPos = 4;
            return 26;
         }
         return -1;
      case 5:
         if ((active0 & 0x7709d048d248000L) != 0L)
         {
            jjmatchedKind = 63;
            jjmatchedPos = 5;
            return 26;
         }
         if ((active0 & 0x81008130506000L) != 0L)
            return 26;
         return -1;
      case 6:
         if ((active0 & 0x64009048c008000L) != 0L)
            return 26;
         if ((active0 & 0x130940001240000L) != 0L)
         {
            if (jjmatchedPos != 6)
            {
               jjmatchedKind = 63;
               jjmatchedPos = 6;
            }
            return 26;
         }
         return -1;
      case 7:
         if ((active0 & 0x840001000000L) != 0L)
            return 26;
         if ((active0 & 0x130100000240000L) != 0L)
         {
            jjmatchedKind = 63;
            jjmatchedPos = 7;
            return 26;
         }
         if ((active0 & 0x400000000000000L) != 0L)
            return 25;
         return -1;
      case 8:
         if ((active0 & 0x100000200000L) != 0L)
         {
            jjmatchedKind = 63;
            jjmatchedPos = 8;
            return 26;
         }
         if ((active0 & 0x130000000040000L) != 0L)
            return 26;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0, long active1)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 9:
         return jjStopAtPos(0, 2);
      case 10:
         jjmatchedKind = 3;
         return jjMoveStringLiteralDfa1_0(0x20L);
      case 13:
         return jjStopAtPos(0, 4);
      case 32:
         return jjStopAtPos(0, 1);
      case 34:
         return jjStopAtPos(0, 71);
      case 40:
         return jjStopAtPos(0, 69);
      case 41:
         return jjStopAtPos(0, 66);
      case 44:
         return jjStopAtPos(0, 67);
      case 45:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 46:
         return jjStartNfaWithStates_0(0, 68, 7);
      case 47:
         return jjMoveStringLiteralDfa1_0(0x240L);
      case 59:
         return jjStopAtPos(0, 70);
      case 66:
      case 98:
         return jjMoveStringLiteralDfa1_0(0x1e000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa1_0(0x7e0000L);
      case 68:
      case 100:
         return jjMoveStringLiteralDfa1_0(0x3f800000L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa1_0(0xc0000000L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa1_0(0x100000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa1_0(0x600000000L);
      case 75:
      case 107:
         return jjMoveStringLiteralDfa1_0(0x800000000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa1_0(0x1e000000000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa1_0(0x20000000000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa1_0(0xc0000000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa1_0(0x700000000000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa1_0(0x1800000000000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa1_0(0x7e000000000000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa1_0(0x80000000000000L);
      case 86:
      case 118:
         return jjMoveStringLiteralDfa1_0(0x700000000000000L);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0, 0L);
      return 1;
   }
   switch(curChar)
   {
      case 13:
         if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(1, 5);
         break;
      case 42:
         if ((active0 & 0x40L) != 0L)
            return jjStopAtPos(1, 6);
         break;
      case 45:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(1, 10);
         break;
      case 47:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(1, 9);
         break;
      case 65:
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x702000001800000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x2430081e000000L);
      case 72:
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x60000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x58000000006000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x140090000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000000000L);
      case 78:
      case 110:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStartNfaWithStates_0(1, 41, 26);
         return jjMoveStringLiteralDfa2_0(active0, 0x80000600000000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x4030a0308000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0xc0000400000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000000000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x1c000000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0, 0L);
}
private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0, 0L);
      return 2;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0xa00000060000L);
      case 66:
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000000L);
      case 67:
      case 99:
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x1004000000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000400000L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x100008000000L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x80080000000000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x4010000000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa3_0(active0, 0x38018000100000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x40000000204000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x140098000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x701000080000000L);
      case 84:
      case 116:
         if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 2;
         }
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 37, 26);
         return jjMoveStringLiteralDfa3_0(active0, 0x401800000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      case 87:
      case 119:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000000000L);
      case 88:
      case 120:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000000000L);
      case 89:
      case 121:
         if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(2, 35, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0, 0L);
}
private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0, 0L);
      return 3;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x1048404000L);
      case 66:
      case 98:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(3, 16, 26);
         else if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(3, 19, 26);
         return jjMoveStringLiteralDfa4_0(active0, 0x100008120000000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x600000000000000L);
      case 69:
      case 101:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 3;
         }
         else if ((active0 & 0x8000000000000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x10110491000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000004002000L);
      case 76:
      case 108:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 38, 26);
         else if ((active0 & 0x200000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 45, 26);
         return jjMoveStringLiteralDfa4_0(active0, 0x2800000008000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000100000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000000000L);
      case 81:
      case 113:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000000000L);
      case 82:
      case 114:
         if ((active0 & 0x20000L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x40000L);
      case 83:
      case 115:
         if ((active0 & 0x400000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 46, 26);
         return jjMoveStringLiteralDfa4_0(active0, 0x40000200000L);
      case 84:
      case 116:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 50, 26);
         break;
      case 89:
      case 121:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0, 0L);
}
private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0, 0L);
      return 4;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x80100040000L);
      case 69:
      case 101:
         if ((active0 & 0x2000000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 49, 26);
         return jjMoveStringLiteralDfa5_0(active0, 0x48000008000L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa5_0(active0, 0x400000000L);
      case 72:
      case 104:
         return jjMoveStringLiteralDfa5_0(active0, 0x600000000000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x140000080100000L);
      case 76:
      case 108:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 36, 26);
         return jjMoveStringLiteralDfa5_0(active0, 0x800020000000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000002000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x20000000000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x110000004000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000000000000L);
      case 84:
      case 116:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(4, 30, 26);
         return jjMoveStringLiteralDfa5_0(active0, 0x11600000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000008000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0, 0L);
}
private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0, 0L);
      return 5;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x600000004008000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa6_0(active0, 0x40000L);
      case 69:
      case 101:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(5, 22, 26);
         else if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(5, 28, 26);
         else if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(5, 29, 26);
         else if ((active0 & 0x80000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 55, 26);
         return jjMoveStringLiteralDfa6_0(active0, 0x100400000000L);
      case 71:
      case 103:
         if ((active0 & 0x1000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 48, 26);
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x810001000000L);
      case 76:
      case 108:
         if ((active0 & 0x100000000L) != 0L)
            return jjStartNfaWithStates_0(5, 32, 26);
         return jjMoveStringLiteralDfa6_0(active0, 0x8000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x140000000000000L);
      case 82:
      case 114:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 39, 26);
         return jjMoveStringLiteralDfa6_0(active0, 0x200c0000200000L);
      case 84:
      case 116:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(5, 13, 26);
         else if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(5, 20, 26);
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000000000L);
      case 89:
      case 121:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(5, 14, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0, 0L);
}
private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0, 0L);
      return 6;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x130000000200000L);
      case 67:
      case 99:
         if ((active0 & 0x10000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 40, 26);
         break;
      case 76:
      case 108:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(6, 26, 26);
         break;
      case 77:
      case 109:
         return jjMoveStringLiteralDfa7_0(active0, 0x1000000L);
      case 78:
      case 110:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(6, 15, 26);
         else if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(6, 31, 26);
         return jjMoveStringLiteralDfa7_0(active0, 0x900000000000L);
      case 82:
      case 114:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(6, 34, 26);
         else if ((active0 & 0x200000000000000L) != 0L)
         {
            jjmatchedKind = 57;
            jjmatchedPos = 6;
         }
         return jjMoveStringLiteralDfa7_0(active0, 0x400000000000000L);
      case 84:
      case 116:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(6, 27, 26);
         else if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 54, 26);
         return jjMoveStringLiteralDfa7_0(active0, 0x40000L);
      case 86:
      case 118:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000000L);
      case 89:
      case 121:
         if ((active0 & 0x80000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 43, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0, 0L);
}
private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0, 0L);
      return 7;
   }
   switch(curChar)
   {
      case 50:
         if ((active0 & 0x400000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 58, 25);
         break;
      case 67:
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000000L);
      case 69:
      case 101:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(7, 24, 26);
         else if ((active0 & 0x40000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 42, 26);
         return jjMoveStringLiteralDfa8_0(active0, 0x40000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa8_0(active0, 0x10000000000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x120000000000000L);
      case 84:
      case 116:
         if ((active0 & 0x800000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 47, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0, 0L);
}
private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0, 0L);
      return 8;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         return jjMoveStringLiteralDfa9_0(active0, 0x100000000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000L);
      case 80:
      case 112:
         if ((active0 & 0x10000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 52, 26);
         break;
      case 82:
      case 114:
         if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(8, 18, 26);
         break;
      case 89:
      case 121:
         if ((active0 & 0x20000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 53, 26);
         else if ((active0 & 0x100000000000000L) != 0L)
            return jjStartNfaWithStates_0(8, 56, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(7, active0, 0L);
}
private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0, 0L);
      return 9;
   }
   switch(curChar)
   {
      case 83:
      case 115:
         if ((active0 & 0x100000000000L) != 0L)
            return jjStartNfaWithStates_0(9, 44, 26);
         break;
      case 84:
      case 116:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(9, 21, 26);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0, 0L);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static final long[] jjbitVec1 = {
   0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 26;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (kind > 72)
                     kind = 72;
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 60)
                        kind = 60;
                     jjCheckNAddStates(0, 4);
                  }
                  else if (curChar == 46)
                     jjCheckNAdd(7);
                  else if (curChar == 39)
                     jjCheckNAddStates(5, 7);
                  break;
               case 26:
               case 25:
                  if ((0x3ff001800000000L & l) == 0L)
                     break;
                  if (kind > 63)
                     kind = 63;
                  jjCheckNAdd(25);
                  break;
               case 1:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddStates(5, 7);
                  break;
               case 2:
                  if (curChar == 39)
                     jjCheckNAddStates(8, 10);
                  break;
               case 3:
                  if (curChar == 39)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 4:
                  if ((0xffffff7fffffffffL & l) != 0L)
                     jjCheckNAddStates(8, 10);
                  break;
               case 5:
                  if (curChar == 39 && kind > 59)
                     kind = 59;
                  break;
               case 6:
                  if (curChar == 46)
                     jjCheckNAdd(7);
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAddTwoStates(7, 8);
                  break;
               case 9:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(10);
                  break;
               case 10:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAdd(10);
                  break;
               case 11:
                  if (kind > 72)
                     kind = 72;
                  break;
               case 12:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  jjCheckNAddStates(0, 4);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  jjCheckNAdd(13);
                  break;
               case 14:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(14, 15);
                  break;
               case 15:
                  if (curChar == 46)
                     jjCheckNAdd(16);
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAddTwoStates(16, 17);
                  break;
               case 18:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(19);
                  break;
               case 19:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAdd(19);
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAddTwoStates(20, 21);
                  break;
               case 22:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(23);
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAdd(23);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (kind > 72)
                     kind = 72;
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 63)
                        kind = 63;
                     jjCheckNAddTwoStates(24, 25);
                  }
                  break;
               case 26:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 63)
                        kind = 63;
                     jjCheckNAdd(25);
                  }
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 63)
                        kind = 63;
                     jjCheckNAddTwoStates(24, 25);
                  }
                  break;
               case 1:
                  jjCheckNAddStates(5, 7);
                  break;
               case 4:
                  jjCheckNAddStates(8, 10);
                  break;
               case 8:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(11, 12);
                  break;
               case 11:
                  if (kind > 72)
                     kind = 72;
                  break;
               case 17:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(13, 14);
                  break;
               case 21:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(15, 16);
                  break;
               case 24:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 63)
                     kind = 63;
                  jjCheckNAddTwoStates(24, 25);
                  break;
               case 25:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 63)
                     kind = 63;
                  jjCheckNAdd(25);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int hiByte = (int)(curChar >> 8);
         int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte & 077);
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (jjCanMove_1(hiByte, i1, i2, l1, l2) && kind > 72)
                     kind = 72;
                  break;
               case 1:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(5, 7);
                  break;
               case 4:
                  if (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     jjCheckNAddStates(8, 10);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 26 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjMoveStringLiteralDfa0_2()
{
   switch(curChar)
   {
      case 10:
         return jjStopAtPos(0, 11);
      default :
         return 1;
   }
}
private final int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 42:
         return jjMoveStringLiteralDfa1_1(0x80L);
      default :
         return 1;
   }
}
private final int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 47:
         if ((active0 & 0x80L) != 0L)
            return jjStopAtPos(1, 7);
         break;
      default :
         return 2;
   }
   return 2;
}
static final int[] jjnextStates = {
   13, 14, 15, 20, 21, 1, 3, 5, 3, 4, 5, 9, 10, 18, 19, 22, 
   23, 
};
private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec0[i2] & l2) != 0L);
      default : 
         return false;
   }
}
private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)
{
   switch(hiByte)
   {
      case 0:
         return ((jjbitVec0[i2] & l2) != 0L);
      default : 
         if ((jjbitVec1[i1] & l1) != 0L)
            return true;
         return false;
   }
}
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, "\51", "\54", 
"\56", "\50", "\73", "\42", null, };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "WithinComment", 
   "WithinLineComment", 
};
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, 2, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xbfffffffffffe001L, 0x1ffL, 
};
static final long[] jjtoSkip = {
   0xefeL, 0x0L, 
};
static final long[] jjtoSpecial = {
   0xec0L, 0x0L, 
};
static final long[] jjtoMore = {
   0x1100L, 0x0L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[26];
private final int[] jjstateSet = new int[52];
StringBuffer image;
int jjimageLen;
int lengthOfMatch;
protected char curChar;
public DDL2CloverTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public DDL2CloverTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 26; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 3 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }
   image = null;
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 8)
         {
            jjmatchedKind = 8;
         }
         break;
       case 2:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_2();
         if (jjmatchedPos == 0 && jjmatchedKind > 12)
         {
            jjmatchedKind = 12;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else 
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        jjimageLen += jjmatchedPos + 1;
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
}
