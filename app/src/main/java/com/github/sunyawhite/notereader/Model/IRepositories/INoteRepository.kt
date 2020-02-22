interface INoteRepository {

    fun getSingleNote (predicate: (note: Note) -> Boolean) : Note

    fun getNotes(predicate : (note : Note) -> Boolean) : List<Note>

    fun getAllNotes() : List<Note>

    fun getNoteById(id : Long) : Note

    fun addNewNote(note : Note) : Boolean

    fun deleteNote(id : Long) : Boolean

    fun containsNoteById(id : Long) : Boolean

}