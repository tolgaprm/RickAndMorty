package comprmto.rickyandmorty.domain

import comprmto.rickyandmorty.data.remote.dto.Info

data class CharactersDomain(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
    val info: Info)