package com.github.sunyawhite.notereader.Koin

import com.github.sunyawhite.notereader.Model.INoteRepository
import com.github.sunyawhite.notereader.Model.RealmNoteRepository
import com.github.sunyawhite.notereader.Model.RoomNoteRepository
import com.github.sunyawhite.notereader.Services.FirebaseTextRecognition
import com.github.sunyawhite.notereader.Services.ITextRecognition
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule : Module = module {
    // setting classes for DI
    single <INoteRepository> { RoomNoteRepository(get()) }
    single <ITextRecognition> { FirebaseTextRecognition(get()) }
}