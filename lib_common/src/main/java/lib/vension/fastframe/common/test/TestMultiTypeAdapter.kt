package lib.vension.fastframe.common.test

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import lib.vension.fastframe.common.R

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:08
 * @desc:
 * ===================================================================
 */
class TestMultiTypeAdapter(data: MutableList<TestBean>?) : BaseMultiItemQuickAdapter<TestBean, BaseViewHolder>(data) {


    init {
        addItemType(0, R.layout.item_multi_type_0)
        addItemType(1, R.layout.item_multi_type_1)
        addItemType(2, R.layout.item_multi_type_2)
    }

    override fun convert(helper: BaseViewHolder, item: TestBean?) {
        val adapterPosition = helper.adapterPosition
        when (item?.itemType) {
            0 -> {
                bindType0Data(helper, item, adapterPosition)
            }
            1 -> {
                bindType1Data(helper, item, adapterPosition)
            }
            2 -> {
                bindType2Data(helper, item, adapterPosition)
            }
        }
    }

    private fun bindType0Data(helper: BaseViewHolder, item: TestBean, adapterPosition: Int) {

    }
    private fun bindType1Data(helper: BaseViewHolder, item: TestBean, adapterPosition: Int) {

    }
    private fun bindType2Data(helper: BaseViewHolder, item: TestBean, adapterPosition: Int) {

    }

}