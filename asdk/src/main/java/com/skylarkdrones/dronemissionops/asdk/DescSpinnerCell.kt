package com.skylarkdrones.dronemissionops.asdk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat

open class DescSpinnerCell @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    val defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private var mSpinner: Spinner? = null
    private var mSummary: TextView? = null
    private var mDesc: TextView? = null
    private var mAdapter: ArrayAdapter<String>? = null

    private var mSelectedPosition = 0
    private var mSelectedListener: OnItemSelectedListener? = null


    init {
        initView()
        initListener()
        initAttrs()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.asdk_spinner_desc_layout, this, true)
        mSpinner = findViewById(R.id.spinner)
        mSummary = findViewById(R.id.summary)
        mDesc = findViewById(R.id.desc)

        mAdapter = object : ArrayAdapter<String>(context, R.layout.asdk_spinner_item_bord) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var rootView = super.getDropDownView(position, convertView, parent)
                rootView?.let {
                    val view = rootView as TextView
                    // 右边的下拉图标
                    if (position != 0 && checkRightCompoundDrawable(view)) {
                        val rightDrawable = view.compoundDrawables[2].mutate()
                        rightDrawable.alpha = 0
                        view.setCompoundDrawables(null, null, rightDrawable, null)

                    }
                    if (mSelectedPosition == position) {
                        view.setTextColor(ContextCompat.getColor(context, R.color.uxsdk_white))
                    } else {
                        view.setTextColor(ContextCompat.getColor(context, R.color.uxsdk_white_75_percent))
                    }
                }
                return rootView


            }


            private fun checkRightCompoundDrawable(view: TextView?): Boolean {
                return view?.compoundDrawables != null && view.compoundDrawables.size == 4 && view.compoundDrawables[2] != null
            }
        }

        mAdapter?.setDropDownViewResource(R.layout.asdk_spinner_item_drop)


    }


    private fun initListener() {
        // 这里需要设置不保存状态。否则在view被销毁重新加载并恢复时，由于spinnerID相同，导致value被复用
        mSpinner?.isSaveEnabled = false
        mSpinner?.adapter = mAdapter
        mSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (mSelectedPosition != position) {
                    mSpinner?.setSelection(position, true)
                    mSelectedListener?.onItemSelected(position)
                    mSelectedPosition = position
                }

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                //不需要实现
            }

        }

    }

    fun initAttrs() {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.DescSpinnerCell, defStyleAttr, 0)
        if (ta.hasValue(R.styleable.DescSpinnerCell_asdk_summary)) {
            mSummary?.show()
            mSummary?.text = ta.getString(R.styleable.DescSpinnerCell_asdk_summary)
        } else {
            mSummary?.hide()
        }

        if (ta.hasValue(R.styleable.DescSpinnerCell_asdk_desc)) {
            mDesc?.show()
            mDesc?.text = ta.getString(R.styleable.DescSpinnerCell_asdk_desc)
        } else {
            mDesc?.hide()
        }

        if (ta.hasValue(R.styleable.DescSpinnerCell_asdk_entries)) {
            var entries = ta.getTextArray(R.styleable.DescSpinnerCell_asdk_entries)
            if (entries != null && entries.isNotEmpty()) {
                val list: MutableList<String> = ArrayList(entries.size)
                for (s in entries) {
                    list.add(s.toString())
                }
                setEntries(list)
            }
        }
    }


    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    fun addOnItemSelectedListener(listener: OnItemSelectedListener?) {
        mSelectedListener = listener
    }


    open fun setSummaryText(summaryText: String) {
        if (mSummary?.text?.equals(summaryText) != true) {
            mSummary?.show()
            mSummary?.text = summaryText
        }
    }

    open fun setSummaryText(summaryTextId: Int) {
        mSummary?.show()
        mSummary?.setText(summaryTextId)

    }

    open fun setSDescText(descText: String) {
        if (mDesc?.text?.equals(descText) != true) {
            mDesc?.show()
            mDesc?.text = descText
        }

    }

    open fun getDescText():TextView?{
        return mDesc
    }

    open fun getSelectPosition() :Int{
        return mSelectedPosition;
    }

    open fun setEntries(entries: List<String?>) {
        mAdapter?.clear()
        mAdapter?.addAll(entries)

    }

    fun select(position: Int) {
        if (position >= 0 && position < mAdapter?.count ?: -1) {
            mSpinner?.setSelection(position, true)
            mSelectedPosition = position
        }
        invalidate()
    }


    override fun setEnabled(enable: Boolean) {
        super.setEnabled(enable)
        mSpinner?.isEnabled = enable
    }

}