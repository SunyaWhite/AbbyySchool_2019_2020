import java.util.*

data class Note (val Id : Long,
                 val Date : Date,
                 val Text : String,
                 val DrawableRes : Int) {

    fun toNote(note : NoteRealm) =
        Note(note.Id, note.Date, note.Text, note.DrawableRes)

}