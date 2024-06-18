package me.t3sl4.kurye.UI.Components.StateProgressBar.listeners;


import me.t3sl4.kurye.UI.Components.StateProgressBar.StateProgressBar;
import me.t3sl4.kurye.UI.Components.StateProgressBar.components.StateItem;

public interface OnStateItemClickListener {

    void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState);

}