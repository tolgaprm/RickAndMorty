package comprmto.rickyandmorty.util

class ItemClickListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(id: Int) = clickListener(id)

}