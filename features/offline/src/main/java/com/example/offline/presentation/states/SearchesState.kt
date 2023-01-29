package com.example.offline.presentation.states

import com.example.offline.domain.model.SearchQuery

data class SearchesState(
    val searchQueries: List<SearchQuery> = listOf(),
)