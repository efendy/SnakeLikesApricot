package com.appikot.games.snakelikesapricot.views;

public interface OnBoardEventListener {
    void onStartEvent();
    void onPauseEvent();
    void onStopEvent();
    void onScoreHitEvent(int score);
}
