package miyue.aodong.com.miyue.fragment;

/**
 * Created by syh11 on 2017/12/15.
 */

import java.util.HashMap;

import miyue.aodong.com.miyue.base.BaseFragment;

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int pos) {

        BaseFragment baseFragment = mBaseFragments.get(pos);

        if (baseFragment == null) {
            switch (pos) {
                case 0:
                    baseFragment = new UserfulChooseFragment();//精选
                    break;
                case 1:
                    baseFragment = new ConcernFragment();//关注
                    break;
                case 2:
                    baseFragment = new NewPersonFragment();//新人
                    break;
                case 3:
                    baseFragment = new NearbyFragment();//附近
                    break;
            }
            mBaseFragments.put(pos, baseFragment);
        }
        return baseFragment;
    }

}
