package com.tmaproject.tarekkma.blutoothrobotremotecontrol.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.R;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.Instruction;
import com.tmaproject.tarekkma.blutoothrobotremotecontrol.app.events.InstructionEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtilsFragment extends Fragment {


  @BindView(R.id.buzz)ImageView buzz;
  @BindView(R.id.stop)ImageView stop;

  public UtilsFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_utils, container, false);
    ButterKnife.bind(this,inflate);

    return inflate;
  }


  @OnClick(R.id.stop)
  public void stop(){
    sendInstruction(Instruction.getStopInstruction());
  }

  @OnClick(R.id.buzz)
  public void buzz(){
    sendInstruction(Instruction.getBuzzInstrction());
  }

  @OnClick(R.id.incDis)
  public void inc(){
    sendInstruction(Instruction.getDisplayInstrction(Instruction.INCREASE));
  }

  @OnClick(R.id.decDis)
  public void dec(){
    sendInstruction(Instruction.getDisplayInstrction(Instruction.DECREASE));
  }


  @OnClick(R.id.auto)
  public void auto(){
    sendInstruction(Instruction.getAutomaticInstruction());
  }

  @OnClick(R.id.man)
  public void man(){
    sendInstruction(Instruction.getManualInstruction());
  }



  private void sendInstruction(Instruction instruction) {
    EventBus.getDefault().post(new InstructionEvent(instruction));
  }
}
