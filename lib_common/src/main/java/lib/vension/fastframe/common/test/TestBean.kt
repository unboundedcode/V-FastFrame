package lib.vension.fastframe.common.test

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:01.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:01
 * @desc:
 * ===================================================================
 */ 
data class TestBean(var type:Int,var title:String):MultiItemEntity {
    override fun getItemType(): Int {
        return type
    }
}