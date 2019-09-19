package kv.vension.fastframe.views.gridpager

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kv.vension.fastframe.R
import kv.vension.fastframe.utils.DensityUtil
import kv.vension.fastframe.views.AtMostGridView
import kv.vension.fastframe.views.AtMostViewPager

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/6 14:36.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/6 14:36
 * @desc:   采用ViewPager+GridView的方式来实现美团app的首页标签效果
 * ===================================================================
 */
class GridPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    // ViewPager
    private lateinit var viewPager: AtMostViewPager
    //指示器
    private lateinit var mGridPagerIndicator: GridPagerIndicator

    //子控件显示的宽度
    private var mChildWidth = 0
    //子控件显示的高度
    private var mChildHeight = 0
    //两个子控件之间的间距
    private var mChildMargin = 0
    //正常情况下显示的颜色
    private var mNormalColor = Color.GRAY
    //选中的时候现实的颜色
    private var mSelectColor = Color.RED
    // 是否是圆形的指示点
    private var mIsCircle = true
    //是否显示默认指示器
    private var isShowDefaultIndicator = true

    /**
     * GridPager
     */
    // 竖直方向的间距
    private var verticalSpacing = 0
    // icon 宽度
    private var imageWidth = 0
    // icon 高度
    private var imageHeight = 0
    // 文字颜色
    private var textColor = Color.BLACK
    // 文字大小
    private var textSize = 0
    // icon 文字 的间距
    private var textImgMargin = 0
    // 行数
    private var rowCount = 2
    // 列数
    private var columnCount = 4
    // 每页大小
    private var pageSize = 8
    // 数据总数
    private var dataAllCount = 0

    /**
     * 监听
     */
    private var itemBindDataListener: ItemBindDataListener? = null
    private var gridItemClickListener: GridItemClickListener? = null

    init {
        setBackgroundColor(Color.WHITE)
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        handleTypedArray(context, attrs)
        val view = View.inflate(getContext(), R.layout.layout_gridpager, null)
        viewPager = view.findViewById(R.id.viewpager)
        mGridPagerIndicator = view.findViewById(R.id.gridPagerIndicator)
        addView(view)
    }

    private fun handleTypedArray(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridPager)
        verticalSpacing = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_verticalSpacing, 0)
        imageWidth = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_img_width, 0)
        imageHeight = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_img_height, 0)
        textColor = typedArray.getColor(R.styleable.GridPager_kv_text_color, Color.BLACK)
        textSize = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_text_size, 0)
        textImgMargin = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_imgText_margin, 0)
        rowCount = typedArray.getInt(R.styleable.GridPager_kv_row_count, 2)
        columnCount = typedArray.getInt(R.styleable.GridPager_kv_column_count, 4)
        pageSize = rowCount * columnCount
        // 指示点
        isShowDefaultIndicator = typedArray.getBoolean(R.styleable.GridPager_kv_show_default_indicator, true)
        mChildWidth = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_indicator_width, DensityUtil.dp2px(8))
        mChildHeight = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_indicator_height, DensityUtil.dp2px(8))
        mChildMargin = typedArray.getDimensionPixelSize(R.styleable.GridPager_kv_indicator_margin, DensityUtil.dp2px(8))
        mNormalColor = typedArray.getColor(R.styleable.GridPager_kv_indicator_normal_color, Color.GRAY)
        mSelectColor = typedArray.getColor(R.styleable.GridPager_kv_indicator_select_color, Color.RED)
        mIsCircle = typedArray.getBoolean(R.styleable.GridPager_kv_indicator_is_circle, true)
        typedArray.recycle()
    }

    /**
     * 设置数据
     *
     * @param allCount
     * @return
     */
    fun setDataAllCount(allCount: Int): GridPager {
        if (allCount >= 0) {
            this.dataAllCount = allCount
        }
        return this
    }

    /**
     * 设置列数
     *
     * @param columnCount
     * @return
     */
    fun setColumnCount(columnCount: Int): GridPager {
        if (columnCount >= 0) {
            this.columnCount = columnCount
        }
        return this
    }

    /**
     * 设置行数
     *
     * @param rowCount
     * @return
     */
    fun setRowCount(rowCount: Int): GridPager {
        if (rowCount >= 0) {
            this.rowCount = rowCount
        }
        return this
    }

    /**
     * 设置 纵向间距
     *
     * @param verticalSpacing
     * @return
     */
    fun setVerticalSpacing(verticalSpacing: Int): GridPager {
        this.verticalSpacing = verticalSpacing
        return this
    }

    /**
     * 设置 icon 宽度
     *
     * @param imageWidth
     * @return
     */
    fun setImageWidth(imageWidth: Int): GridPager {
        this.imageWidth = imageWidth
        return this
    }

    /**
     * 设置 icon 高度
     *
     * @param imageHeight
     * @return
     */
    fun setImageHeight(imageHeight: Int): GridPager {
        this.imageHeight = imageHeight
        return this
    }

    /**
     * 设置 字体颜色
     *
     * @param textColor
     * @return
     */
    fun setTextColor(textColor: Int): GridPager {
        this.textColor = textColor
        return this
    }

    /**
     * 设置字体大小
     *
     * @param textSize
     * @return
     */
    fun setTextSize(textSize: Int): GridPager {
        this.textSize = textSize
        return this
    }

    /**
     * 设置字体与icon的间距
     *
     * @param textImgMargin
     * @return
     */
    fun setTextImgMargin(textImgMargin: Int): GridPager {
        this.textImgMargin = textImgMargin
        return this
    }

    /**
     * 设置显示默认指示器
     *
     * @param showDefaultIndicator
     * @return
     */
    fun showDefaultIndicator(showDefaultIndicator: Boolean): GridPager {
        this.isShowDefaultIndicator = showDefaultIndicator
        return this
    }

    /**
     * 设置指示器的宽度
     *
     * @param mChildWidth
     * @return
     */
    fun setIndicatorChildWidth(mChildWidth: Int): GridPager {
        this.mChildWidth = mChildWidth
        return this
    }

    /**
     * 设置指示器的高度
     *
     * @param mChildHeight
     * @return
     */
    fun setIndicatorChildHeight(mChildHeight: Int): GridPager {
        this.mChildHeight = mChildHeight
        return this
    }

    /**
     * 设置指示器的间距
     *
     * @param mChildMargin
     * @return
     */
    fun setIndicatorChildMargin(mChildMargin: Int): GridPager {
        this.mChildMargin = mChildMargin
        return this
    }

    /**
     * 设置指示器是否为圆形
     *
     * @param mIsCircle
     * @return
     */
    fun setIndicatorIsCircle(mIsCircle: Boolean): GridPager {
        this.mIsCircle = mIsCircle
        return this
    }

    /**
     * 设置指示器未选中颜色
     *
     * @param mNormalColor
     * @return
     */
    fun setIndicatorNormalColor(mNormalColor: Int): GridPager {
        this.mNormalColor = mNormalColor
        return this
    }

    /**
     * 设置指示器选中的颜色
     *
     * @param mSelectColor
     * @return
     */
    fun setIndicatorSelectColor(mSelectColor: Int): GridPager {
        this.mSelectColor = mSelectColor
        return this
    }

    fun getViewPager(): ViewPager? {
        return viewPager
    }

    /**
     * 设置 Item 点击监听
     *
     * @param gridItemClickListener
     */
    fun setGridItemClickListener(gridItemClickListener: GridItemClickListener): GridPager {
        this.gridItemClickListener = gridItemClickListener
        return this
    }

    /**
     * 绑定数据
     *
     * @param itemBindDataListener
     */
    fun setItemBindDataListener(itemBindDataListener: ItemBindDataListener): GridPager {
        this.itemBindDataListener = itemBindDataListener
        return this
    }

    /**
     * 显示
     */
    fun show() {
        if (dataAllCount == 0) {
            return
        }
        // 设置viewPager
        if (verticalSpacing > 0) {
            val viewPagerParams = viewPager.layoutParams as LinearLayout.LayoutParams
            viewPagerParams.topMargin = verticalSpacing
            viewPagerParams.bottomMargin = verticalSpacing
            viewPager.layoutParams = viewPagerParams
        }
        viewPager.adapter = GridAdapter()
        viewPager.addOnPageChangeListener(this)
        // 设置指示点
        if (isShowDefaultIndicator && dataAllCount / pageSize > 1) {
            mGridPagerIndicator.visibility = View.VISIBLE
            val page = dataAllCount / pageSize + if (dataAllCount % pageSize > 0) 1 else 0
            mGridPagerIndicator.setChildWidth(mChildWidth)
                .setChildHeight(mChildHeight)
                .setChildMargin(mChildMargin)
                .isCircle(mIsCircle)
                .setNormalColor(mNormalColor)
                .setSelectColor(mSelectColor)
                .setIndicatorCheckedChangeListener(object : GridPagerIndicator.IndicatorCheckedChangeListener {
                    override fun checkedChange(position: Int) {
                        if (position in 0 until page) {
                            viewPager.currentItem = position
                        }
                    }
                })
                .addChild(page)
        } else {
            mGridPagerIndicator.visibility = View.GONE
        }
    }

    /**
     * viewPager 页面切换监听
     *
     * @param i
     * @param v
     * @param i1
     */
    override fun onPageScrolled(i: Int, v: Float, i1: Int) {

    }

    override fun onPageSelected(i: Int) {
        if (isShowDefaultIndicator && mGridPagerIndicator.visibility == View.VISIBLE) {
            mGridPagerIndicator.setSelectPosition(i)
        }
    }

    override fun onPageScrollStateChanged(i: Int) {

    }

    /**
     * GridAdapter
     */
    private inner class GridAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return dataAllCount / pageSize + if (dataAllCount % pageSize > 0) 1 else 0
        }

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return view === o
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val atMostGridView = AtMostGridView(context)
            atMostGridView.numColumns = columnCount
            if (verticalSpacing > 0) {
                atMostGridView.verticalSpacing = verticalSpacing
            }
            atMostGridView.adapter = AtMostGridViewAdapter(context, position)
            container.addView(atMostGridView)
            return atMostGridView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    /**
     * AtMostGridViewAdapter
     */
    private inner class AtMostGridViewAdapter(private val context: Context, private val pageindex: Int) :
        BaseAdapter() {

        override fun getCount(): Int {
            return if (dataAllCount > (pageindex + 1) * pageSize) pageSize else dataAllCount - pageindex * pageSize
        }

        override fun getItem(position: Int): Any {
            return position + pageindex * pageSize
        }

        override fun getItemId(position: Int): Long {
            return (position + pageindex * pageSize).toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            var holder: ViewHolder? = null
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridpager_gv, parent, false)
                holder = ViewHolder()
                holder.iconImageView = convertView!!.findViewById(R.id.item_image)
                holder.iconNameTextView = convertView.findViewById(R.id.item_text)

                if(imageWidth > 0 && imageHeight > 0){
                    holder.iconImageView.layoutParams = LinearLayout.LayoutParams(imageWidth, imageHeight)
                }

                if(textImgMargin > 0){
                    val textParams = holder.iconNameTextView.layoutParams as LinearLayout.LayoutParams
                    textParams.topMargin = textImgMargin
                    holder.iconNameTextView.layoutParams = textParams
                }

                convertView.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }

            val pos = position + pageindex * pageSize
            if(textColor > 0){
                holder.iconNameTextView.setTextColor(textColor)
            }
            if(textSize > 0){
                holder.iconNameTextView.textSize = textSize.toFloat()
            }

            // 绑定数据
            if (itemBindDataListener != null) {
                itemBindDataListener!!.bindData(holder.iconImageView, holder.iconNameTextView, pos)
            }
            // item点击
            convertView.setOnClickListener {
                if (gridItemClickListener != null) {
                    gridItemClickListener!!.onItemClick(pos)
                }
            }
            return convertView
        }

        private inner class ViewHolder {
            internal lateinit var iconNameTextView: TextView
            internal lateinit var iconImageView: ImageView
        }
    }

    /**
     * 绑定数据
     */
    interface ItemBindDataListener {
        fun bindData(imageView: ImageView, textView: TextView, position: Int)
    }

    /**
     * item点击回调
     */
    interface GridItemClickListener {
        fun onItemClick(position: Int)
    }
}