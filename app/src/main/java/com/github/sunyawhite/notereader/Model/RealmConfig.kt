package com.github.sunyawhite.notereader.Model

import io.realm.annotations.RealmModule
import io.realm.RealmConfiguration

@RealmModule(classes = [NoteRealm::class])
object RealmConfig {
    fun provideDefaultConfiguration() : RealmConfiguration =
        RealmConfiguration.Builder()
            .name("noteDatabase.realm")
            .schemaVersion(2)
            .modules(RealmConfig)
            .deleteRealmIfMigrationNeeded().build()

}