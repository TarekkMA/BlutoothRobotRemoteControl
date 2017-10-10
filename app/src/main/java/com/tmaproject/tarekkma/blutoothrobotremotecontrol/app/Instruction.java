package com.tmaproject.tarekkma.blutoothrobotremotecontrol.app;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by tarekkma on 9/7/17.
 */

public class Instruction {

  @Retention(SOURCE) @StringDef({
      MOVEMENT, ARM,
  }) public @interface Part {
  }

  @Retention(SOURCE) @StringDef({
      FORWARD, BACKWARD, RIGHT, LEFT,
  }) public @interface MovementCommand {
  }

  @Retention(SOURCE) @StringDef({
      RIGHT, LEFT
  }) public @interface BaseCommand {
  }

  @Retention(SOURCE) @StringDef({
      GRAB, RELEASE
  }) public @interface GripperCommand {
  }

  @Retention(SOURCE) @StringDef({
      EXTEND, COLLAPSE
  }) public @interface JointCommand {
  }

  @Retention(SOURCE) @StringDef({
      INCREASE, DECREASE
  }) public @interface DisplayCommand {
  }

  @Retention(SOURCE) @IntDef({
      1, 2
  }) public @interface JointNum {
  }

  public static final String MOVEMENT = "M";
  public static final String ARM = "A";

  public static final String FORWARD = "F";
  public static final String BACKWARD = "B";
  public static final String RIGHT = "R";
  public static final String LEFT = "L";

  public static final String GRAB = "G";
  public static final String RELEASE = "S";

  public static final String COLLAPSE = "C";
  public static final String EXTEND = "E";

  public static final String INCREASE = "AA";
  public static final String DECREASE = "LK";



  public static final char FOW = 'a';
  public static final char BAK = 'b';
  public static final char RIT = 'd';
  public static final char LFT = 'c';

  public static final char A_B_L = 'e';
  public static final char A_B_R = 'f';

  public static final char A_J1_E = 'g';
  public static final char A_J1_C = 'h';
  public static final char A_J2_E = 'i';
  public static final char A_J2_C = 'j';
  public static final char A_J3_E = 'k';
  public static final char A_J3_C = 'l';

  public static final char A_G_G = 'm';
  public static final char A_G_R = 'n';

  public static final char STOP = 'o';

  public static final char AUTO = 'p';
  public static final char MAN = 'q';

  public static final char INC_COUNTER  = 's';
  public static final char DEC_COUNTER  = 't';


  public static final char BUZZ = 'r';

  private char cmd;

  private Instruction(char cmd) {
    this.cmd = cmd;
  }

  public static Instruction getMovementInstruction(@MovementCommand String command) {
    switch (command) {
      case FORWARD:
        return new Instruction(FOW);
      case BACKWARD:
        return new Instruction(BAK);
      case RIGHT:
        return new Instruction(RIT);
      case LEFT:
        return new Instruction(LFT);
    }
    throw new RuntimeException("OPS");
  }

  public static Instruction getArmBaseInstruction(@BaseCommand String command) {
    switch (command) {
      case RIGHT:
        return new Instruction(A_B_R);
      case LEFT:
        return new Instruction(A_B_L);
    }
    throw new RuntimeException("OPS");
  }

  public static Instruction getArmGripperInstruction(@GripperCommand String command) {
    switch (command) {
      case GRAB:
        return new Instruction(A_G_G);
      case RELEASE:
        return new Instruction(A_G_R);
    }
    throw new RuntimeException("OPS");
  }

  public static Instruction getArmJointInstruction(@JointCommand String command,
      @JointNum int num) {
    switch (command) {
      case EXTEND:
        switch (num) {
          case 1:
            return new Instruction(A_J1_E);
          case 2:
            return new Instruction(A_J2_E);
          case 3:
            return new Instruction(A_J3_E);
        }
        break;
      case COLLAPSE:
        switch (num) {
          case 1:
            return new Instruction(A_J1_C);
          case 2:
            return new Instruction(A_J2_C);
          case 3:
            return new Instruction(A_J3_C);
        }
    }
    throw new RuntimeException("OPS");
  }

  public static Instruction getStopInstruction() {
    return new Instruction(STOP);
  }

  public static Instruction getBuzzInstrction() {
    return new Instruction(BUZZ);
  }

  public static Instruction getManualInstruction() {
    return new Instruction(MAN);
  }

  public static Instruction getAutomaticInstruction() {
    return new Instruction(AUTO);
  }


  public static Instruction getDisplayInstrction(@DisplayCommand String command) {
    switch (command){
      case INCREASE: return new Instruction(INC_COUNTER);
      case DECREASE: return new Instruction(DEC_COUNTER);
    }
    throw new RuntimeException("OPS");
  }



  public byte getCommand() {
    return (byte) cmd;
  }
}
