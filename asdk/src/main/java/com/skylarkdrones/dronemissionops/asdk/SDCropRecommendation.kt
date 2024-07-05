package com.skylarkdrones.dronemissionops.asdk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.NestedScrollView
import com.skylarkdrones.dronemissionops.asdk.databinding.LayoutSdRecommendationBinding

class SDCropRecommendation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private val binding = LayoutSdRecommendationBinding.inflate(
        LayoutInflater.from(context), this, true
    )

}
