package lib.vension.fastframe.common.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_test.*
import lib.vension.fastframe.common.R

/**
 * ========================================================
 *
 * @author: Created by Vension on 2018/10/17 11:35.
 * @email: 2506856664@qq.com
 * @desc: character determines attitude, attitude determines destiny
 * ========================================================
 */
class TestFragment : Fragment() {

    companion object {
        fun getInstance(title: String): TestFragment {
            val fragment = TestFragment()
            fragment.mTitle = title
            return fragment
        }
    }

    private var mTitle: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mTitle = it.getString("key_content")
        }
        tv_content.text = mTitle
    }

}
