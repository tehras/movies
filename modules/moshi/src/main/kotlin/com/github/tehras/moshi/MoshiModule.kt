/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.moshi

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

/**
 * @author tkoshkin created on 8/26/18
 */
@Module
class MoshiModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesMoshi(): Moshi = Moshi
            .Builder()
            .build()
    }
}