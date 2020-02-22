import java.util.*

// Класс. Транспортная сущность. Используется для отображения информации в приложении
data class Note (val Id : Long,
                 val Date : Date,
                 val Text : String,
                 val DrawableRes : Int) {

    // Converter
    companion object{
        internal fun toNote(note : NoteRealm?) : Note {
            require(note != null) { "Argument can't be null" }
            return Note(note.Id, note.Date, note.Text, note.DrawableRes)
        }
    }

}