package com.vension.mvpforkotlin.sample.ui.fragment

import android.os.Bundle
import android.view.View
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.TodoBean
import com.vension.fastframe.module_wan.event.RefreshTodoEvent
import com.vension.fastframe.module_wan.mvp.contract.TodoAddContract
import com.vension.fastframe.module_wan.mvp.presenter.TodoAddPresenter
import kv.vension.fastframe.utils.DialogUtil
import kotlinx.android.synthetic.main.fragment_todo_add.*
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment
import kv.vension.fastframe.ext.formatCurrentDate
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.utils.KeyBoardUtil
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/26 17:28.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class TodoAddFragment : AbsCompatMVPFragment<TodoAddContract.View, TodoAddContract.Presenter>(), TodoAddContract.View {

    companion object {
        fun getInstance(bundle: Bundle): TodoAddFragment {
            val fragment = TodoAddFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * Date
     */
    private var mCurrentDate = formatCurrentDate()

    /**
     * 类型
     */
    private var mType: Int = 0
    private var mTodoBean: TodoBean? = null
    /**
     * 新增，编辑，查看 三种状态
     */
    private var mTypeKey = ""
    /**
     * id
     */
    private var mId: Int? = 0
    /**
     * 优先级  重要（1），一般（0）
     */
    private var mPriority = 0

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(activity!!, getString(R.string.save_ing))
    }


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_todo_add
    }

    override fun createPresenter(): TodoAddContract.Presenter {
        return TodoAddPresenter()
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        tv_date.text = mCurrentDate

        mType = arguments?.getInt(Constant.TODO_TYPE) ?: 0
        mTypeKey = arguments?.getString(Constant.TYPE_KEY) ?: Constant.Type.ADD_TODO_TYPE_KEY

        when (mTypeKey) {
            Constant.Type.ADD_TODO_TYPE_KEY -> {

            }
            Constant.Type.EDIT_TODO_TYPE_KEY -> {
                mTodoBean = arguments?.getSerializable(Constant.TODO_BEAN) as TodoBean ?: null
                et_title.setText(mTodoBean?.title)
                et_content.setText(mTodoBean?.content)
                tv_date.text = mTodoBean?.dateStr
                mPriority = mTodoBean?.priority ?: 0
            }
            Constant.Type.SEE_TODO_TYPE_KEY -> {
                mTodoBean = arguments?.getSerializable(Constant.TODO_BEAN) as TodoBean ?: null
                et_title.setText(mTodoBean?.title)
                et_content.setText(mTodoBean?.content)
                tv_date.text = mTodoBean?.dateStr
                et_title.isEnabled = false
                et_content.isEnabled = false
                ll_date.isEnabled = false
                btn_save.visibility = View.GONE
            }
        }

        rg_priority.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb0) {
                mPriority = 0
                rb0.isChecked = true
                rb1.isChecked = false
            } else if (checkedId == R.id.rb1) {
                mPriority = 1
                rb0.isChecked = false
                rb1.isChecked = true
            }
        }

        ll_date.setOnClickListener {
            KeyBoardUtil.closeKeyBoard(et_content, activity!!)
            val now = Calendar.getInstance()
            val dpd = android.app.DatePickerDialog(
                activity!!,
                android.app.DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val currentMonth = month + 1
                    mCurrentDate = "$year-$currentMonth-$dayOfMonth"
                    tv_date.text = mCurrentDate
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }

        btn_save.setOnClickListener {
            when (mTypeKey) {
                Constant.Type.ADD_TODO_TYPE_KEY -> {
                    mPresenter?.addTodo()
                }
                Constant.Type.EDIT_TODO_TYPE_KEY -> {
                    mPresenter?.updateTodo(getItemId())
                }
            }
        }
    }

    override fun lazyLoadData() {
    }


    override fun getType(): Int = mType
    override fun getCurrentDate(): String = mCurrentDate
    override fun getTitle(): String = et_title.text.toString()
    override fun getContent(): String = et_content.text.toString()
    override fun getStatus(): Int = mTodoBean?.status ?: 0
    override fun getItemId(): Int = mTodoBean?.id ?: 0
    override fun getPriority(): String = mPriority.toString()

    override fun showAddTodo(success: Boolean) {
        if (success) {
            showToast(getString(R.string.save_success))
            EventBus.getDefault().post(RefreshTodoEvent(true, mType))
            activity?.finish()
        }
    }

    override fun showUpdateTodo(success: Boolean) {
        if (success) {
            showToast(getString(R.string.save_success))
            EventBus.getDefault().post(RefreshTodoEvent(true, mType))
            activity?.finish()
        }
    }



    override fun showLoading() {
        mDialog.show()
    }

    override fun showContent() {
        super.showContent()
        mDialog.dismiss()
    }


}