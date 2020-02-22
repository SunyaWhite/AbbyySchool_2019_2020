package com.github.sunyawhite.notereader.Model

import io.realm.annotations.RealmModule
import Note
import io.realm.RealmConfiguration

@RealmModule(classes = [Note::class])
object RealmConfig {
    fun provideDefaultConfiguration() : RealmConfiguration =
        RealmConfiguration.Builder()
            .name("noteDatabase.realm")
            .schemaVersion(1)
            .modules(RealmConfig)
            .deleteRealmIfMigrationNeeded().build()

}