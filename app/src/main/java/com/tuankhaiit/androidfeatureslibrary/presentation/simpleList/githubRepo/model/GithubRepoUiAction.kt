package com.tuankhaiit.androidfeatureslibrary.presentation.simpleList.githubRepo.model

import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel

sealed class GithubRepoUiAction {
    data class Search(val query: SimpleQueryDataModel) : GithubRepoUiAction()
    data class Scroll(val currentQuery: SimpleQueryDataModel) : GithubRepoUiAction()

}