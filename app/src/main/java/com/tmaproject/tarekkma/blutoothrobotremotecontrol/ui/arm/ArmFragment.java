package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.arm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.Instruction;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.InstructionEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArmFragment extends Fragment {

  public ArmFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_arm, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnTouch(R.id.right) public boolean right(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmBaseInstruction(Instruction.RIGHT));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.left) public boolean left(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmBaseInstruction(Instruction.LEFT));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.grap) public boolean grap(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmGripperInstruction(Instruction.GRAB));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.relese) public boolean reselse(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmGripperInstruction(Instruction.RELEASE));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.up1) public boolean up1(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmJointInstruction(Instruction.EXTEND, 1));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.up2) public boolean up2(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmJointInstruction(Instruction.EXTEND, 2));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.down1) public boolean down1(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmJointInstruction(Instruction.COLLAPSE, 1));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.down2) public boolean down2(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getArmJointInstruction(Instruction.COLLAPSE, 2));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  private void sendInstruction(Instruction instruction) {
    EventBus.getDefault().post(new InstructionEvent(instruction));
  }
}
