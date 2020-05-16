package com.github.sunyawhite.notereader.Model

import io.realm.annotations.RealmModule
import io.realm.RealmConfiguration
// Конфиг файл для базы данных Realm
@RealmModule(classes = [NoteRealm::class])
object RealmConfig {
    fun provideDefaultConfiguration() : RealmConfiguration =
        RealmConfiguration.Builder()
            .name("noteDatabase.realm")
            .schemaVersion(2)
            .modules(RealmConfig)
            .deleteRealmIfMigrationNeeded().build()

}