package com.rengwuxian.daggerdemo.model;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 老板：要求每个员工写够一定的代码
 */
@Singleton
public class Boss {
    private static final int GOAL = 10;

    @Inject
    public Boss() {

    }

    public String check(int codeCount) {
        if (codeCount < GOAL) {
            return String.format("快点搬！不搬够%s块砖不准下班！", GOAL);
        } else {
            return "嗯，回家吧！";
        }
    }
}
