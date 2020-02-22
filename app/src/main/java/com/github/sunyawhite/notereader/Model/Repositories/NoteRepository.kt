import android.content.Context
import android.util.Log
import com.github.sunyawhite.notereader.Model.RealmConfig
import com.github.sunyawhite.notereader.R
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmException
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import java.lang.Exception
import java.util.*
import kotlin.random.Random

// Class to
class NoteRepository (val context : Context) : INoteRepository {

    // Database context
    private val realm : Realm
    // Database configuration provider class
    private val config : RealmConfiguration

    init {
        Realm.init(context)
        config = RealmConfig.provideDefaultConfiguration()
        realm = Realm.getInstance(config)
        //generateSampleData()
    }

    override fun getAllNotes(): List<NoteRealm>? =
        realm.where<NoteRealm>().findAll()

    override fun getNoteById(id: Long): NoteRealm? =
        realm.where<NoteRealm>().equalTo("Id", id).findFirst()

    override fun addNewNote(note: NoteRealm): Boolean {
        return try {
            realm.executeTransaction { realm ->
                realm.copyToRealm(note)
                    ?: throw RealmException("Unable to add note to the database. Note $note")
            }
            true
        }
        catch(ex : Exception) {
            Log.e("REALM EXCEPTION : ", ex.message)
            false
        }
    }

    override fun deleteNote(id: Long): Boolean {
        return try{
            realm.executeTransaction { realm ->
                var note = realm.where<NoteRealm>().equalTo("Id", id).findFirst()
                    ?: throw RealmException("Unable to find note with a such id")
                note.deleteFromRealm()
            }
            true
        }
        catch (ex : Exception){
            Log.e("REALM EXCEPTION : ", ex.message)
            false
        }
    }

    override fun containsNoteById(id: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateSampleData() {

        if(realm.where<NoteRealm>().count() != 0L)
            return

        context.assets.open("notes.txt")
            .bufferedReader()
            .readText().split("\n\n")
            .fold(0L) { acc, s ->
                if(s == "")
                    acc
                this.addNewNote(NoteRealm(acc, generateRandomDate(), s, selectDrawable( acc % 4 + 1)))
                acc + 1
            }
    }

    private fun generateRandomDate() : Date =
        Date(Random.nextInt(2000, 2020), Random.nextInt(1, 13), Random.nextInt(1, 30))

    private fun selectDrawable(id : Long) =
        when(id)
        {
            1L -> R.drawable.cat1
            2L -> R.drawable.cat2
            3L -> R.drawable.cat3
            4L -> R.drawable.cat4
            else -> R.drawable.ic_launcher_foreground
        }
}