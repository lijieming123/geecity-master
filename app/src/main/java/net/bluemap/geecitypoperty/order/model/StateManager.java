package net.bluemap.geecitypoperty.order.model;

import net.bluemap.geecitypoperty.R;

/**
 * 状态管理
 * Created by LiuPeng on 2015/8/10.
 */
public class StateManager {

    static public final String[] STATES = {"未受理","抢单中","已派工","已完工","已截停","已评价","已回访"};

    static public final int[] COLORS = {R.color.ALIZARIN,R.color.ORANGE,R.color.PETER_RIVER,R.color.NEPHRITIS,
    R.color.ASBESTOS,R.color.TURQUOISE,R.color.GREEN_SEA};

    static public final String[] BUTTON_TEXT = {"受理","接单","闭单"};

    /**
     * 获取派单状态文字描述
     * @param state 状态值
     * @return 状态文本
     */
    static public String getStateText(int state){
        return STATES[state];
    }

    /**
     * 获取派单状态UI颜色
     * @param state 状态值
     * @return 颜色的resource id
     */
    static public int getStateColorId(int state){
        return COLORS[state];
    }

    /**
     * 获取状态操作按钮的文本
     * @param state
     * @return
     */
    static public String getStateButtonText(int state){
        return BUTTON_TEXT[state];
    }
}
