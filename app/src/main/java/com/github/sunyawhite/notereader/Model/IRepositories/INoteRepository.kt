interface INoteRepository {

    fun getAllNotes() : List<NoteRealm>?

    fun getNoteById(id : Long) : NoteRealm?

    fun addNewNote(note : NoteRealm) : Boolean

    fun deleteNote(id : Long) : Boolean

    fun containsNoteById(id : Long) : Boolean

}