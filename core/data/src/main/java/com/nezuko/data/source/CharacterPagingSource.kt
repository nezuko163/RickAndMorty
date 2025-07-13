package com.nezuko.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel

class CharacterPagingSource(
    private val sourceQuery: suspend (page: Int) -> ResultModel<CharacterResponse>
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1
        return try {
            when (val result = sourceQuery(page)) {
                is ResultModel.Success -> {
                    val response = result.data
                    LoadResult.Page(
                        data = response.results,
                        prevKey = response.info.prevPage,
                        nextKey = response.info.nextPage
                    )
                }

                is ResultModel.Failure -> {
                    LoadResult.Error(result.e)
                }

                is ResultModel.Loading, is ResultModel.None -> {
                    LoadResult.Error(IllegalStateException("Неожиданное состояние загрузки или несуществующее"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }
}
