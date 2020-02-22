import android.content.Context
import com.github.sunyawhite.notereader.Model.RealmConfig
import io.realm.Realm
import io.realm.RealmConfiguration

// Class to
class NoteRepository (context : Context) : INoteRepository {

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
}