package lib.vension.fastframe.common.test

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.fastframe.core.mvp.AbsCompatMVPRefreshFragment
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:03.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:03
 * @desc:
 * ===================================================================
 */
class TestMultiTypeFragment : AbsCompatMVPRefreshFragment<TestBean, TestRefreshMultiContract.View, TestRefreshMultiPresenter>(),
    TestRefreshMultiContract.View {

    private val datas = ArrayList<TestBean>()

    companion object{
        fun newInstance() : TestMultiTypeFragment{
            return TestMultiTypeFragment()
        }
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, "测试多类型列表")
    }

    override fun createRecyAdapter(): BaseQuickAdapter<TestBean, BaseViewHolder> {
        return TestMultiTypeAdapter(datas)
    }

    override fun createPresenter(): TestRefreshMultiPresenter {
        return TestRefreshMultiPresenter()
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter?.getTestMultiDatas()
    }

    override fun setTestMultiData(testDatas: MutableList<TestBean>) {
        setRefreshData(testDatas)
    }




}
