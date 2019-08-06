package lib.vension.fastframe.common.test

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.fastframe.core.mvp.AbsCompatMVPRefreshFragment
import kv.vension.fastframe.utils.DensityUtil
import lib.vension.fastframe.common.R

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/29 10:19.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class TestRefreshFragment : AbsCompatMVPRefreshFragment<String, TestRefreshContract.View, TestRefreshPresenter>(),
    TestRefreshContract.View {

    companion object{
        fun newInstance() : TestRefreshFragment{
            return TestRefreshFragment()
        }
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar,"测试列表")
    }

    override fun createPresenter(): TestRefreshPresenter {
        return TestRefreshPresenter()
    }

    override fun createRecyAdapter(): BaseQuickAdapter<String, BaseViewHolder> {
        return TestRefreshAdapter(context)
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<String, BaseViewHolder>) {
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter?.getTestDatas()
    }


    override fun setTestData(testDatas: MutableList<String>) {
        val hear = layoutInflater.inflate(R.layout.layout_test_refresh_header,null)
        val mLayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(150))
        hear.layoutParams = mLayoutParams
        hear.setOnClickListener {
            startProxyActivity(TestMultiTypeFragment::class.java)
        }
        setRefreshData(testDatas, listOf(hear),null)
//        setRefreshData(testDatas)
    }

}