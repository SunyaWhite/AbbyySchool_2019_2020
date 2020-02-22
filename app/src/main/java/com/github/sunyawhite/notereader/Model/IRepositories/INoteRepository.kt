interface INoteRepository {

    fun getAllNotes() : List<Note>?

    fun getNoteById(id : Long) : Note?

    fun addNewNote(note : Note) : Boolean

    fun deleteNote(id : Long) : Boolean

    fun containsNoteById(id : Long) : Boolean

}