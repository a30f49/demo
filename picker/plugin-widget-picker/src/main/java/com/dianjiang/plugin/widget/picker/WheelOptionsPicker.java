package com.dianjiang.plugin.widget.picker;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

import com.dianjiang.app.pickerdialog.R;
import com.dianjiang.plugin.widget.picker.internal.ArrayWheelAdapter;
import com.dianjiang.plugin.widget.picker.internal.OnWheelChangedListener;

public class WheelOptionsPicker {
	private View view;
	private WheelView wv_option1;
	private WheelView wv_option2;
	private WheelView wv_option3;

	private ArrayList<String> mOptions1Items;
	private ArrayList<ArrayList<String>> mOptions2Items;
	private ArrayList<ArrayList<ArrayList<String>>> mOptions3Items;
	public int textSize;  // in dp

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelOptionsPicker(View view) {
		super();
		this.view = view;
		setView(view);
		textSize = 0;

		Context context = view.getContext();
		if(context!=null){

		}
	}

	/**
	 * text size in db
	 */
	public void setTextSize(int textSize){
		this.textSize = textSize;

		/// update text size immediately
		if(wv_option1!=null) {
			wv_option1.setTextSize(textSize);
		}
		if(wv_option2!=null) {
			wv_option2.setTextSize(textSize);
		}
		if(wv_option3!=null) {
			wv_option3.setTextSize(textSize);
		}
	}

	public void setPicker(ArrayList<String> optionsItems) {
		setPicker(optionsItems, null, null, false);
	}

	public void setPicker(ArrayList<String> options1Items,
			ArrayList<ArrayList<String>> options2Items, boolean linkage) {
		setPicker(options1Items, options2Items, null, linkage);
	}

	public void setPicker(ArrayList<String> options1Items,
			ArrayList<ArrayList<String>> options2Items,
			ArrayList<ArrayList<ArrayList<String>>> options3Items,
			boolean linkage) {
		this.mOptions1Items = options1Items;
		this.mOptions2Items = options2Items;
		this.mOptions3Items = options3Items;
		int len = ArrayWheelAdapter.DEFAULT_LENGTH;
		//if (this.mOptions3Items == null)
		//	len = 8;
		//if (this.mOptions2Items == null)
		//	len = 12;

		// option 1
		wv_option1 = (WheelView) view.findViewById(R.id.options1);
		wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items, len));// 设置显示数据
		wv_option1.setCurrentItem(0);// 初始化时显示的数据
		wv_option1.setTextSize(textSize);

		// option 2
		wv_option2 = (WheelView) view.findViewById(R.id.options2);
		if (mOptions2Items != null) {
			wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(0)));// 设置显示数据
		}
		wv_option2.setCurrentItem(wv_option1.getCurrentItem());// 初始化时显示的数据
		wv_option2.setTextSize(textSize);

		// option 3
		wv_option3 = (WheelView) view.findViewById(R.id.options3);
		if (mOptions3Items != null) {
			wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(0).get(0)));// 设置显示数据
		}
		wv_option3.setCurrentItem(wv_option3.getCurrentItem());// 初始化时显示的数据
		wv_option3.setTextSize(textSize);

		if (this.mOptions2Items == null) {
			wv_option2.setVisibility(View.GONE);
		}
		if (this.mOptions3Items == null) {
			wv_option3.setVisibility(View.GONE);
		}

		// 联动监听器
		OnWheelChangedListener wheelListener_option1 = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mOptions2Items != null) {
					wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items
							.get(wv_option1.getCurrentItem())));
					wv_option2.setCurrentItem(0);
				}
				if (mOptions3Items != null) {
					wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items
							.get(wv_option1.getCurrentItem())
							.get(wv_option2.getCurrentItem())));
					wv_option3.setCurrentItem(0);
				}
			}
		};
		OnWheelChangedListener wheelListener_option2 = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mOptions3Items != null) {
					wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items
						.get(wv_option1.getCurrentItem())
						.get(wv_option2.getCurrentItem())));
					wv_option3.setCurrentItem(0);
				}
			}
		};

		// 添加联动监听
		if (options2Items != null && linkage) {
			wv_option1.addChangingListener(wheelListener_option1);
		}
		if (options3Items != null && linkage) {
			wv_option2.addChangingListener(wheelListener_option2);
		}
	}

	/**
	 * 设置选项的单位
	 * 
	 * @param label1
	 * @param label2
	 * @param label3
	 */
	public void setLabels(String label1, String label2, String label3) {
		if (label1 != null) {
			wv_option1.setLabel(label1);
		}
		if (label2 != null) {
			wv_option2.setLabel(label2);
		}
		if (label3 != null) {
			wv_option3.setLabel(label3);
		}
	}

	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wv_option1.setCyclic(cyclic);
		wv_option2.setCyclic(cyclic);
		wv_option3.setCyclic(cyclic);
	}

	/**
	 * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
	 * 
	 * @return
	 */
	public int[] getCurrentItems() {
		int[] currentItems = new int[3];
		currentItems[0] = wv_option1.getCurrentItem();
		currentItems[1] = wv_option2.getCurrentItem();
		currentItems[2] = wv_option3.getCurrentItem();
		return currentItems;
	}

	public void setCurrentItems(int option1, int option2, int option3) {
		wv_option1.setCurrentItem(option1);
		wv_option2.setCurrentItem(option2);
		wv_option3.setCurrentItem(option3);
	}
}
