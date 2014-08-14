package com.rengwuxian.daggerdemo.model;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 老板：要求每个员工写够一定的代码
 */
@Singleton
public class Boss {
    private static final int GOAL = 10;
    private static final String[] ALTERNATIVE_NAMES = {"Jeff", "老王", "扔物线"}; // 老板的姓名将从三个备选中选出

    public String name;

    @Inject
    public Boss() {
        name = ALTERNATIVE_NAMES[new Random().nextInt(3)]; // 随机选出一个名字
    }

    /**
     * 老板查看程序员写的代码行数后，给出评价
     * @param codeCount 代码行数
     * @return 老板的评价
     */
    public String check(int codeCount) {
        if (codeCount < GOAL) {
            return String.format("老板%s：快点写！不写够%s行代码不准下班！", name, GOAL);
        } else {
            return String.format("老板%s：嗯，不错，下班吧！", name);
        }
    }
}
