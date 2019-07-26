package com.vension.fastframe.module_course.widget.section;


import com.vension.fastframe.module_course.R;

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 9:57
 * 更 新：2019/7/26 9:57
 * 描 述：增加头部或者尾部
 * ========================================================
 */

public class HeadOrFooterSection extends StatelessSection {

    public ViewHolder holder;

    public HeadOrFooterSection(int headerResourceId) {
        super(headerResourceId, R.layout.layout_section_empty);
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        this.holder = holder;
    }
}
