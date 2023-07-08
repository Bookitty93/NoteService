fun main() {

}

class ElemNotFindException(msg: String) : RuntimeException(msg)

interface Elem {
    val id: Int
    var text: String
}

data class Note(override val id: Int, override var text: String, val comments: MutableList<Comment> = mutableListOf()) :
    Elem

data class Comment(override val id: Int, override var text: String, var isDelete: Boolean) : Elem

open class CrudService<T : Elem>(val elems: MutableList<T>) {

    open fun add(elem: T): T {
        elems += elem
        return elems.last()
    }

    fun get(): MutableList<T> = elems

    fun edit(elem: T): T {
        val editElem = elems.find { it.id == elem.id } ?: throw ElemNotFindException("Not found ID")
        if (editElem is Comment && editElem.isDelete) {
            throw RuntimeException("Comment is delete")
        }
        editElem.text = elem.text
        return editElem
    }

    fun delete(elemId: Int): Boolean {
        val searchElem = elems.find { it.id == elemId } ?: return false
        return if (searchElem is Comment) {
            searchElem.isDelete = true
            true
        } else {
            elems.remove(searchElem)
        }
    }

    fun getById(elemId: Int): T {
        return elems.find { it.id == elemId } ?: throw ElemNotFindException(" Not find by this ID")
    }
}

class CommentService(list: MutableList<Comment>) : CrudService<Comment>(list) {
    fun restoreComment(commentId: Int): Boolean {
        val elemForRestore = elems.find { it.id == commentId && it.isDelete } ?: return false
        elemForRestore.isDelete = false
        return true
    }
}

object NoteService {
    private val noteService = CrudService<Note>(mutableListOf())
    fun clear() {
        noteService.elems.clear()
    }

    fun add(elem: Note) = noteService.add(elem)

    fun addComment(note: Note, comment: Comment) = CommentService(noteService.getById(note.id).comments).add(comment)

    fun delete(idNote: Int) = noteService.delete(idNote)

    fun edit(note: Note) = noteService.edit(note)

    fun deleteComment(note: Note, comment: Comment) =
        CommentService(noteService.getById(note.id).comments).delete(comment.id)

    fun editComment(note: Note, comment: Comment) = CommentService(noteService.getById(note.id).comments).edit(comment)

    fun getNotes(): MutableList<Note> {
        return noteService.get()
    }

    fun getById(idNote: Int): Note {
        return noteService.getById(idNote)
    }

    fun getComments(idNote: Int): MutableList<Comment> {
        return noteService.getById(idNote).comments
    }

    fun restoreComment(idNote: Int, idComment: Int) =
        CommentService(noteService.getById(idNote).comments).restoreComment(idComment)

}