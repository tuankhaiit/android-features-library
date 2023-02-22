package com.tuankhaiit.androidfeatureslibrary.presentation.githubRepo.model

import com.tuankhaiit.androidfeatureslibrary.domain.model.RepoModel
import com.tuankhaiit.androidfeatureslibrary.domain.model.SimpleQueryDataModel

sealed class GithubRepoUiAction {
    data class Search(val query: SimpleQueryDataModel) : GithubRepoUiAction()
    data class Scroll(val currentQuery: SimpleQueryDataModel) : GithubRepoUiAction()
    data class ItemClick(val model: RepoModel) : GithubRepoUiAction()
}