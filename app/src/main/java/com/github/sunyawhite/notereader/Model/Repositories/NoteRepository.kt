import android.content.Context
import com.github.sunyawhite.notereader.Model.RealmConfig
import com.github.sunyawhite.notereader.R
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
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

    override fun getSingleNote(predicate: (note: Note) -> Boolean): Note {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNotes(predicate: (note: Note) -> Boolean): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllNotes(): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNoteById(id: Long): Note {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewNote(note: Note): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteNote(id: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun containsNoteById(id: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateSampleData() {

        if(realm.where<Note>().count() != 0L)
            return

        context.assets.open("notes.txt")
            .bufferedReader()
            .readText().split("\n\n")
            .fold(0L) { acc, s ->
                if(s == "")
                    acc
                this.addNewNote(Note(acc, generateRandomDate(), s, selectDrawable( acc % 4 + 1)))
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