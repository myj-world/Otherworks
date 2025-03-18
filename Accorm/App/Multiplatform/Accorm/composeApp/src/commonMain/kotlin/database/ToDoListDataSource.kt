package database

import com.yousufjamil.accorm.Accorm
import com.yousufjamil.accorm.ToDoList

class ToDoListDataSource (private val db: Accorm) {
    private val queries = db.toDoListQueries

    fun getList(): List<ToDoList> {
        return queries.getList().executeAsList()
    }

    fun addToList(item: String, complete: Boolean) {
        val completeMod = if (complete) 1L else 0L
        queries.addToList(
            itemId = null,
            item = item,
            complete = completeMod
        )
    }

    fun completeItem(id: Long) {
        queries.completeItem(
            itemId = id
        )
    }

    fun inCompleteItem(id: Long) {
        queries.inCompleteItem(
            itemId = id
        )
    }

    fun deleteItem(id: Long) {
        queries.deleteItem(
            itemId = id
        )
    }

    fun clearList() {
        queries.clearList()
    }
}