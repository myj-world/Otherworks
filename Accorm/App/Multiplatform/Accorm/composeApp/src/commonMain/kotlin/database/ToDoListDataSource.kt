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

    fun completeItem(id: Int) {
        queries.completeItem(
            itemId = id.toLong()
        )
    }

    fun inCompleteItem(id: Int) {
        queries.inCompleteItem(
            itemId = id.toLong()
        )
    }

    fun deleteItem(id: Int) {
        queries.deleteItem(
            itemId = id.toLong()
        )
    }

    fun clearList() {
        queries.clearList()
    }
}