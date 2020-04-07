package com.github.sunyawhite.notereader.Koin

import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.RealmNoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule : Module = module {
    factory <INoteRepository> { RealmNoteRepository(get()) }
}