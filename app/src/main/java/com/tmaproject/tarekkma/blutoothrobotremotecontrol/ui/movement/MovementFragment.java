package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui.movement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class MovementFragment extends Fragment {

  public MovementFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_movement, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnTouch(R.id.up) public boolean up(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getMovementInstruction(Instruction.FORWARD));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.down) public boolean down(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getMovementInstruction(Instruction.BACKWARD));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.right) public boolean right(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getMovementInstruction(Instruction.RIGHT));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  @OnTouch(R.id.left) public boolean left(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      sendInstruction(Instruction.getMovementInstruction(Instruction.LEFT));
    } else if (event.getAction() == MotionEvent.ACTION_UP) {
      sendInstruction(Instruction.getStopInstruction());
    }
    return true;
  }

  private void sendInstruction(Instruction instruction) {
    EventBus.getDefault().post(new InstructionEvent(instruction));
  }
}
