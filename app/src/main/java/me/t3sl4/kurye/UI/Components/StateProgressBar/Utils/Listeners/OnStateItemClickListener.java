package me.t3sl4.kurye.UI.Components.StateProgressBar.Utils.Listeners;


import me.t3sl4.kurye.UI.Components.StateProgressBar.StateProgressBar;
import me.t3sl4.kurye.UI.Components.StateProgressBar.Utils.Components.StateItem;

public interface OnStateItemClickListener {

    void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}