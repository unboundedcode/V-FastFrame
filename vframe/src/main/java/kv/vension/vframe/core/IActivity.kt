package kv.vension.vframe.core

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 14:47
 * 描  述：AbsCompatActivity - 接口
 * ========================================================
 */
interface IActivity : IAppCompatPage {


     /**
      * 是否开启侧滑返回
      *
      * @return true 默认开启
      */
      fun isSupportSwipeBack(): Boolean = true

}