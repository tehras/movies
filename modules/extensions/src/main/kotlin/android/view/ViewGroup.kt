/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package android.view

import androidx.annotation.LayoutRes

/**
 * @author tkoshkin created on 8/27/18
 */
fun ViewGroup.inflateView(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(this.context).inflate(layoutRes, this, false)
}