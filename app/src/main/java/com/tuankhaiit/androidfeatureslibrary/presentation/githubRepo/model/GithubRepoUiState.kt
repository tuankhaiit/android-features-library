package com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.model

import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel

data class GithubRepoUiState(
    val query: SimpleQueryDataModel = SimpleQueryDataModel.default(),
    val lastQueryScrolled: SimpleQueryDataModel = SimpleQueryDataModel.default(),
    val hasNotScrolledForCurrentSearch: Boolean = false
)