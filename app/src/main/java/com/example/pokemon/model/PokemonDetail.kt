package com.example.pokemon.model

import com.squareup.moshi.Json

data class PokemonDetail(
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>
)

data class Sprites(
    val back_default: String,
    val back_female: Any,
    val back_shiny: String,
    val back_shiny_female: Any,
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any,
    val other: OtherData
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)

data class Type(
    val slot: Int,
    val type: TypeX
)

data class StatX(
    val name: String,
    val url: String
)

data class TypeX(
    val name: String,
    val url: String
)

data class OtherData(
    @field:Json(name = "dream_world")
    val dreamWorld: DreamWorld,
    @field:Json(name = "official-artwork")
    val officialArtwork: OfficialArtwork
)

data class DreamWorld(
    val front_default: String?,
    val front_female: String?
)

data class OfficialArtwork(
    val front_default: String?
)