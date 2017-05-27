package android.support.v4.app;

import android.view.View;
import android.view.ViewGroup;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 15:05.
 */

public class FragmentHelper {

    public static FragmentViewHolder getFragmentContainerId(int layoutId, Fragment fragment){
        FragmentViewHolder viewHolder = new FragmentViewHolder();
        fragment.mView = fragment.getActivity().getLayoutInflater().inflate(layoutId,
                fragment.mContainer, false);
        viewHolder.mView = fragment.mView;
        viewHolder.mContainer = fragment.mContainer;
        viewHolder.mContainerId = fragment.mContainerId;
        return viewHolder;
    }

    public static class FragmentViewHolder{
        public ViewGroup mContainer;
        public View mView;
        public int mContainerId;
    }
}
