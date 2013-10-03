/**
 * 
 */
package com.yeshu.app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 
 * @author yeshu
 * @date 2013-7-20
 * 
 */

public class MyGridView extends GridView {

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
