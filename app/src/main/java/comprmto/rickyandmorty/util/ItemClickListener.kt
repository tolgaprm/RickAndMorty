package comprmto.rickyandmorty.util

class ItemClickListener(val clickListener: (characterId: Int) -> Unit) {
    fun onClick(characterId: Int) = clickListener(characterId)

}