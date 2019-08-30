package com.vension.mvpforkotlin.sample.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.TodoDataBean
import com.vension.fastframe.module_wan.bean.TodoResponseBody
import com.vension.fastframe.module_wan.event.RefreshTodoEvent
import com.vension.fastframe.module_wan.event.TodoEvent
import com.vension.fastframe.module_wan.mvp.contract.TodoContract
import com.vension.fastframe.module_wan.mvp.presenter.TodoPresenter
import com.vension.fastframe.module_wan.ui.adapter.TodoAdapter
import com.vension.fastframe.module_wan.widget.SwipeItemLayout
import kv.vension.fastframe.utils.DialogUtil
import kotlinx.android.synthetic.main.fragment_todo.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.adapter.recy.decoration.SpaceItemDecoration
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.utils.NetworkUtil
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/26 17:03.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class TodoFragment : AbsCompatMVPFragment<TodoContract.View, TodoContract.Presenter>(), TodoContract.View {

    companion object {
        fun getInstance(type: Int): TodoFragment {
            val fragment = TodoFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.TODO_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * is Refresh
     */
    private var isRefresh = true

    private var mType: Int = 0

    /**
     * 是否是已完成 false->待办 true->已完成
     */
    private var bDone: Boolean = false

    private val datas = mutableListOf<TodoDataBean>()

    private val mAdapter: TodoAdapter by lazy {
        TodoAdapter(R.layout.item_recy_todo, R.layout.item_sticky_header, datas)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun createPresenter(): TodoContract.Presenter {
        return TodoPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_todo
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        mType = arguments?.getInt(Constant.TODO_TYPE) ?: 0

        swipeRefreshLayout_todo.run {
            isRefreshing = true
            setColorSchemeColors(VFrame.getColor(R.color.colorWanMain))
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView_todo.run {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
            adapter = mAdapter
            addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(activity))
        }

        mAdapter.run {
            bindToRecyclerView(recyclerView_todo)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView_todo)
            onItemClickListener = this@TodoFragment.onItemClickListener
            onItemChildClickListener = this@TodoFragment.onItemChildClickListener
            setEmptyView(R.layout.layout_mul_default_empty)
        }
    }

    override fun lazyLoadData() {
        swipeRefreshLayout_todo.postDelayed({
            isRefresh = true
            if (bDone) {
                mPresenter?.getDoneList(1, mType)
            } else {
                mPresenter?.getNoTodoList(1, mType)
            }
        },300)
    }


    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        mAdapter.setEnableLoadMore(false)
        lazyLoadData()
    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout_todo.isRefreshing = false
        val page = mAdapter.data.size / 20 + 1
        if (bDone) {
            mPresenter?.getDoneList(page, mType)
        } else {
            mPresenter?.getNoTodoList(page, mType)
        }
    }


    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            val data = datas[position]
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (datas.size != 0) {
                val data = datas[position].t
                when (view.id) {
                    R.id.btn_delete -> {
                        if (!NetworkUtil.isNetworkAvailable()) {
                            showToast(resources.getString(R.string.no_network_view_hint))
                            return@OnItemChildClickListener
                        }
                        activity?.let {
                            DialogUtil.getConfirmDialog(it, resources.getString(R.string.confirm_delete),
                                DialogInterface.OnClickListener { _, _ ->
                                    mPresenter?.deleteTodoById(data.id)
                                    mAdapter.remove(position)
                                }).show()
                        }
                    }
                    R.id.btn_done -> {
                        if (!NetworkUtil.isNetworkAvailable()) {
                            showToast(resources.getString(R.string.no_network_view_hint))
                            return@OnItemChildClickListener
                        }
                        if (bDone) {
                            mPresenter?.updateTodoById(data.id, 0)
                        } else {
                            mPresenter?.updateTodoById(data.id, 1)
                        }
                        mAdapter.remove(position)
                    }
                    R.id.item_todo_content -> {
                        getBundleExtras().putSerializable(Constant.TODO_BEAN,data)
                        getBundleExtras().putInt(Constant.TODO_TYPE,mType)
                        if (bDone) {
                            getBundleExtras().putString(Constant.TYPE_KEY, Constant.Type.SEE_TODO_TYPE_KEY)
                            getBundleExtras().putString(Constant.TODO_TITLE,getString(R.string.see))
                        } else {
                            getBundleExtras().putString(Constant.TYPE_KEY, Constant.Type.EDIT_TODO_TYPE_KEY)
                            getBundleExtras().putString(Constant.TODO_TITLE,getString(R.string.edit))
                        }
                        startProxyActivity(TodoAddFragment::class.java,getBundleExtras())
                    }
                }
            }
        }


    override fun showLoading() {
        swipeRefreshLayout_todo?.isRefreshing = false
        if (isRefresh) {
            mAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }


    override fun showNoNetwork() {
        super.showNoNetwork()
        stopRefresh()
    }
    override fun showError() {
        super.showError()
        stopRefresh()
    }

    override fun showEmpty() {
        super.showEmpty()
        stopRefresh()
    }
    override fun showContent() {
        super.showContent()
        stopRefresh()
    }

    private fun stopRefresh(){
        swipeRefreshLayout_todo?.isRefreshing = false
        if (isRefresh) {
            mAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showMessage(message: String) {
        super.showMessage(message)
        mAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
    }

    override fun showNoTodoList(todoResponseBody: TodoResponseBody) {
        stopRefresh()
        // TODO 待优化
        val list = mutableListOf<TodoDataBean>()
        var bHeader = true
        todoResponseBody.datas.forEach { todoBean ->
            bHeader = true
            for (i in list.indices) {
                if (todoBean.dateStr == list[i].header) {
                    bHeader = false
                    break
                }
            }
            if (bHeader)
                list.add(TodoDataBean(true, todoBean.dateStr))
            list.add(TodoDataBean(todoBean))
        }

        list.let {
            mAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < todoResponseBody.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
    }

    override fun showDeleteSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.delete_success))
        }
    }

    override fun showUpdateSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.completed))
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doTodoEvent(event: TodoEvent) {
        if (mType == event.curIndex) {
            when (event.type) {
                Constant.TODO_ADD -> {
                    getBundleExtras().putString(Constant.TYPE_KEY, Constant.Type.ADD_TODO_TYPE_KEY)
                    getBundleExtras().putInt(Constant.TODO_TYPE,mType)
                    getBundleExtras().putString(Constant.TODO_TITLE,getString(R.string.add))
                    startProxyActivity(TodoAddFragment::class.java,getBundleExtras())
                }
                Constant.TODO_NO -> {
                    bDone = false
                    lazyLoadData()
                }
                Constant.TODO_DONE -> {
                    bDone = true
                    lazyLoadData()
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doRefresh(event: RefreshTodoEvent) {
        if (event.isRefresh) {
            if (mType == event.type) {
                lazyLoadData()
            }
        }
    }

}