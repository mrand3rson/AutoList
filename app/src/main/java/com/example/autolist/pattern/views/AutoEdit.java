package com.example.autolist.pattern.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Andrei on 08.08.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface AutoEdit extends MvpView {
    void onComplete(Long itemId);
}
