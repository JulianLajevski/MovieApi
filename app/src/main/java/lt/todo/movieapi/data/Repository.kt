package lt.todo.movieapi.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
        apiHelperImpl: ApiHelperImpl
) {
    val remote = apiHelperImpl
}