package com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events;

import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.Instruction;

/**
 * Created by tarekkma on 9/8/17.
 */

public class InstructionEvent {
  private final Instruction instruction;

  public InstructionEvent(Instruction instruction) {
    this.instruction = instruction;
  }

  public Instruction getInstruction() {
    return instruction;
  }
}
