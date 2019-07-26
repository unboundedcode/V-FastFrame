package com.vension.fastframe.module_wan.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * Created by chenxz on 2018/8/11.
 */
class TodoDataBean : SectionEntity<TodoBean> {

    constructor(isHeader: Boolean, headerName: String) : super(isHeader, headerName)

    constructor(todoBean: TodoBean) : super(todoBean)

}