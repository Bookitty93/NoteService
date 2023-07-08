import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class NoteServiceTest {
    private val noteService = CrudService<Note>(mutableListOf())

    @Before
    fun clear() {
        NoteService.clear()
    }

    @Test
    fun addPositive() {
        val service = NoteService
        val note = Note(1, "noni")
        val result = service.add(note)
        assertEquals(result, note)
    }


    @Test
    fun addComment() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(1, "lala", false)
        service.add(note)

        val result = service.addComment(note, comment)
        assertEquals(result.text, comment.text)

    }


    @Test
    fun delete() {
        val service = NoteService
        val note = Note(1, "noni")
        val idNote = 1
        service.add(note)
        val result = service.delete(idNote)

        assertTrue(result)
    }

    @Test
    fun delete2() {
        val service = NoteService
        val note = Note(1, "noni")
        val idNote = 1
        val result = service.delete(idNote)

        assertFalse(result)
    }

    @Test
    fun edit() {
        val service = NoteService
        val note = Note(1, "noni")
        val note2 = Note(1, "ni")
        service.add(note)
        val result = service.edit(note2)
        assertEquals(result, note2)
    }

    @Test
    fun edit2() {
        val service = NoteService
        val note = Note(1, "noni")

        service.add(note)
        val result = service.edit(note)
        assertEquals(result, note)
    }

    @Test
    fun deleteCommentTrue() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(1, "lala", false)
        service.add(note)
        service.addComment(note, comment)

        val result = service.deleteComment(note, comment)

        assertTrue(result)
    }

    @Test
    fun deleteCommentFalse() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(3, "lala", false)
        service.add(note)
        val result = service.deleteComment(note, comment)
        assertFalse(result)
    }

    @Test
    fun editComment() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(3, "lala", false)
        service.add(note)
        service.addComment(note, comment)
        val result = service.editComment(note, comment)
        assertEquals(result, comment)
    }

    @Test
    fun getNotes() {
        val service = NoteService
        val note = Note(1, "noni")
        val note2 = Note(2, "ni")
        service.add(note)
        service.add(note2)
        val result = service.getNotes()
        assertEquals(result, mutableListOf(note, note2))

    }

    @Test
    fun getById() {
        val service = NoteService
        val note = Note(1, "noni")
        service.add(note)
        val idNote = 1
        val result = service.getById(idNote)
        assertEquals(result, note)
    }

    @Test
    fun getComments() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(1, "nni", false)
        val comment2 = Comment(22, "nerni", false)
        service.add(note)
        service.addComment(note, comment)
        service.addComment(note, comment2)
        val result = service.getComments(note.id)
        assertEquals(result, mutableListOf(comment, comment2))
    }

    @Test
    fun restoreComments() {
        val service = NoteService
        val note = Note(1, "noni")
        val comment = Comment(1, "nni", true)
        val comment2 = Comment(22, "nerni", false)
        service.add(note)
        service.addComment(note, comment)
        service.addComment(note, comment2)
        val result = service.restoreComment(note.id, comment.id)
        val result2 = service.restoreComment(note.id, comment2.id)
        assertTrue(result)
        assertFalse(result2)
    }
}